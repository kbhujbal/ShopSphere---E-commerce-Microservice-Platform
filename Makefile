# ShopSphere E-commerce Microservice Platform - Makefile
# Usage: make start-all   (starts everything for demo)
#        make stop-all     (stops all running services)

SERVICES = api-gateway user-service product-service order-service cart-service payment-service shipping-service notification-service review-service

# Shell function to resolve service port (hyphens in names prevent make variable lookup)
define get_port
$(shell case $(1) in \
	api-gateway) echo 8080;; \
	product-service) echo 8081;; \
	order-service) echo 8082;; \
	user-service) echo 8083;; \
	cart-service) echo 8084;; \
	payment-service) echo 8085;; \
	shipping-service) echo 8086;; \
	notification-service) echo 8087;; \
	review-service) echo 8088;; \
esac)
endef

# Inline port lookup for use inside shell loops
PORT_CMD = case $$service in api-gateway) port=8080;; product-service) port=8081;; order-service) port=8082;; user-service) port=8083;; cart-service) port=8084;; payment-service) port=8085;; shipping-service) port=8086;; notification-service) port=8087;; review-service) port=8088;; esac

LOG_DIR = .logs

# Detect docker compose command (v2 plugin vs standalone)
DOCKER_COMPOSE := $(shell command -v docker-compose 2>/dev/null || echo "docker compose")

# ========================
# Full project targets
# ========================

## Build all backend services (compiles common + all modules)
build:
	@echo "Building all services..."
	mvn clean package -DskipTests -q
	@echo "Build complete."

## Check if infrastructure is running, start via Docker if not
start-infra:
	@echo "Checking infrastructure..."
	@MONGO_UP=false; REDIS_UP=false; \
	if curl -s http://localhost:27017 2>&1 | grep -q "MongoDB" 2>/dev/null || mongosh --eval "db.runCommand({ping:1})" > /dev/null 2>&1; then \
		MONGO_UP=true; echo "  MongoDB:       already running"; \
	fi; \
	if redis-cli ping 2>/dev/null | grep -q "PONG"; then \
		REDIS_UP=true; echo "  Redis:         already running"; \
	fi; \
	if $$MONGO_UP && $$REDIS_UP; then \
		echo "Infrastructure is ready (running natively)."; \
	else \
		echo "Starting missing infrastructure via Docker..."; \
		$(DOCKER_COMPOSE) up -d mongodb redis elasticsearch || \
		(echo ""; echo "ERROR: Docker is not running or not installed."; \
		 echo "Please start MongoDB and Redis manually:"; \
		 echo "  brew services start mongodb-community"; \
		 echo "  brew services start redis"; \
		 exit 1); \
	fi

## Stop infrastructure (Docker only — skips if running natively)
stop-infra:
	@if command -v docker > /dev/null 2>&1 && docker info > /dev/null 2>&1; then \
		$(DOCKER_COMPOSE) down 2>/dev/null || true; \
	else \
		echo "Docker not running — infrastructure may be running natively."; \
	fi

## Start all backend services in background
start-services: | $(LOG_DIR)
	@echo "Starting all backend services..."
	@for service in $(SERVICES); do \
		$(PORT_CMD); \
		echo "  Starting $$service on port $$port..."; \
		cd $(CURDIR)/$$service && \
		nohup java -jar target/*.jar > $(CURDIR)/$(LOG_DIR)/$$service.log 2>&1 & \
		echo $$! > $(CURDIR)/$(LOG_DIR)/$$service.pid; \
		cd $(CURDIR); \
	done
	@echo ""
	@echo "All services started. Logs in $(LOG_DIR)/"
	@echo "Waiting for services to initialize..."
	@sleep 15
	@$(MAKE) --no-print-directory status

## Start frontend dev server
start-frontend: | $(LOG_DIR)
	@echo "Starting frontend dev server..."
	@cd $(CURDIR)/frontend && nohup npm run dev > $(CURDIR)/$(LOG_DIR)/frontend.log 2>&1 & echo $$! > $(CURDIR)/$(LOG_DIR)/frontend.pid
	@echo "Frontend started on http://localhost:5173"

## Start everything (build + infra + services + frontend)
start-all: build start-infra start-services start-frontend
	@echo ""
	@echo "============================================"
	@echo "  ShopSphere is ready for demo!"
	@echo "  Frontend:    http://localhost:5173"
	@echo "  API Gateway: http://localhost:8080"
	@echo "============================================"

## Stop all backend services
stop-services:
	@echo "Stopping all backend services..."
	@for service in $(SERVICES); do \
		if [ -f $(LOG_DIR)/$$service.pid ]; then \
			pid=$$(cat $(LOG_DIR)/$$service.pid); \
			if kill -0 $$pid 2>/dev/null; then \
				kill $$pid; \
				echo "  Stopped $$service (PID $$pid)"; \
			else \
				echo "  $$service already stopped"; \
			fi; \
			rm -f $(LOG_DIR)/$$service.pid; \
		fi; \
	done
	@echo "All services stopped."

## Stop frontend
stop-frontend:
	@if [ -f $(LOG_DIR)/frontend.pid ]; then \
		pid=$$(cat $(LOG_DIR)/frontend.pid); \
		if kill -0 $$pid 2>/dev/null; then \
			kill $$pid; \
			echo "Frontend stopped (PID $$pid)"; \
		else \
			echo "Frontend already stopped"; \
		fi; \
		rm -f $(LOG_DIR)/frontend.pid; \
	fi

## Stop everything (services + infra + frontend)
stop-all: stop-services stop-frontend stop-infra
	@echo "Everything stopped."

## Show status of all services
status:
	@echo ""
	@echo "Service Status:"
	@echo "-------------------------------------------"
	@for service in $(SERVICES); do \
		$(PORT_CMD); \
		if curl -s http://localhost:$$port/actuator/health > /dev/null 2>&1; then \
			echo "  $$service (port $$port) : UP"; \
		else \
			echo "  $$service (port $$port) : DOWN"; \
		fi; \
	done
	@echo "-------------------------------------------"

## Tail logs for a specific service (usage: make logs SVC=order-service)
logs:
	@if [ -z "$(SVC)" ]; then \
		echo "Usage: make logs SVC=<service-name>"; \
		echo "Available: $(SERVICES) frontend"; \
	else \
		tail -f $(LOG_DIR)/$(SVC).log; \
	fi

## Tail all logs
logs-all:
	tail -f $(LOG_DIR)/*.log

## Restart a specific service (usage: make restart SVC=order-service)
restart:
	@if [ -z "$(SVC)" ]; then \
		echo "Usage: make restart SVC=<service-name>"; \
	else \
		echo "Restarting $(SVC)..."; \
		if [ -f $(LOG_DIR)/$(SVC).pid ]; then \
			pid=$$(cat $(LOG_DIR)/$(SVC).pid); \
			kill $$pid 2>/dev/null; \
		fi; \
		service=$(SVC); $(PORT_CMD); \
		cd $(CURDIR)/$(SVC) && \
		nohup java -jar target/*.jar > $(CURDIR)/$(LOG_DIR)/$(SVC).log 2>&1 & \
		echo $$! > $(CURDIR)/$(LOG_DIR)/$(SVC).pid; \
		echo "$(SVC) restarted on port $$port"; \
	fi

## Clean build artifacts and logs
clean:
	mvn clean -q
	rm -rf $(LOG_DIR)
	@echo "Cleaned build artifacts and logs."

$(LOG_DIR):
	@mkdir -p $(LOG_DIR)

.PHONY: build start-infra stop-infra start-services stop-services start-frontend stop-frontend start-all stop-all status logs logs-all restart clean help

## Show help
help:
	@echo "ShopSphere Makefile Commands:"
	@echo ""
	@echo "  make start-all        - Build, start infra + all services + frontend"
	@echo "  make stop-all         - Stop everything"
	@echo "  make build            - Build all backend services"
	@echo "  make start-infra      - Start MongoDB, Redis, Elasticsearch"
	@echo "  make start-services   - Start all backend microservices"
	@echo "  make start-frontend   - Start frontend dev server"
	@echo "  make stop-services    - Stop all backend microservices"
	@echo "  make stop-frontend    - Stop frontend dev server"
	@echo "  make stop-infra       - Stop Docker infrastructure"
	@echo "  make status           - Health check all services"
	@echo "  make logs SVC=name    - Tail logs for a specific service"
	@echo "  make logs-all         - Tail all service logs"
	@echo "  make restart SVC=name - Restart a specific service"
	@echo "  make clean            - Remove build artifacts and logs"
	@echo "  make help             - Show this help"
