package com.shopsphere.common.repository;

import com.shopsphere.common.model.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends MongoRepository<T, String> {
    // Add common repository methods here if needed
} 