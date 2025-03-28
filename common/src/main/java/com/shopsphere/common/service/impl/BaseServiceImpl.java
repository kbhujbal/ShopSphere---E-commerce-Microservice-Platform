package com.shopsphere.common.service.impl;

import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.common.model.BaseEntity;
import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.common.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity, R extends BaseRepository<T>> implements BaseService<T> {
    protected final R repository;

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<T> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
} 