package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g join fetch g.participants gp join fetch gp.user where g.id = :groupId")
    Optional<Group> findGroupDetailByIdFetch(Long groupId);
}
