package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Group;
import com.netflix.recommend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g left join fetch g.participants gp left join fetch gp.user where g.id = :groupId")
    Optional<Group> findGroupDetailByIdFetch(Long groupId);

    List<Group> findAllByNameContaining(String keyword);

    @Query("select g from Group g left join g.participants gp where gp.user.id = :userId")
    List<Group> findAllByUserId(Long userId);

    @Query("select distinct v from Group g " +
            "left join g.participants gp " +
            "left join gp.user gpu " +
            "left join gpu.histories gpuh " +
            "left join gpuh.video v " +
            "where g.id = :groupId")
    List<Video> findGroupHistory(Long groupId);
}
