package com.shopsphere.common.controller;

import com.shopsphere.common.model.BaseEntity;
import com.shopsphere.common.response.ApiResponse;
import com.shopsphere.common.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseController<T extends BaseEntity, S extends BaseService<T>> {
    protected final S service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<T>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(service.findAll()));
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<T>>> findAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(service.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<T>> save(@RequestBody T entity) {
        return ResponseEntity.ok(ApiResponse.success(service.save(entity)));
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<T>>> saveAll(@RequestBody List<T> entities) {
        return ResponseEntity.ok(ApiResponse.success(service.saveAll(entities)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody T entity) {
        service.delete(entity);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 