package com.bigbyman.rtask.repository;

import com.bigbyman.rtask.model.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, Long> {
    Optional<T> findById(Long id);
}
