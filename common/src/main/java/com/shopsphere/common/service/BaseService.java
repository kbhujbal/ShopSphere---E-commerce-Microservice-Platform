package com.shopsphere.common.service;

import com.shopsphere.common.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {
    T save(T entity);
    List<T> saveAll(List<T> entities);
    Optional<T> findById(String id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    void deleteById(String id);
    void delete(T entity);
    boolean existsById(String id);
    long count();
} 