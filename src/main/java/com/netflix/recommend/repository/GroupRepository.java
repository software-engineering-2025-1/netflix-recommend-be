package com.netflix.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netflix.recommend.entity.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
