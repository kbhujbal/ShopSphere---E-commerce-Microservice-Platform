FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/app/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-cp","app:app/lib/*","com.shopsphere.product.ProductServiceApplication"] 