package com.netflix.recommend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netflix.recommend.entity.GroupEntity;
import com.netflix.recommend.entity.GroupSettings;

@Repository
public interface GroupSettingsRepository extends JpaRepository<GroupSettings, Long> {
    Optional<GroupSettings> findByGroup(GroupEntity group);

    Optional<GroupSettings> findByGroup_Id(Long groupId);
}
