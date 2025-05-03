package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Follow;
import com.netflix.recommend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsBySenderAndReceiver(User sender, User receiver);

    @Query("select f from Follow f left join fetch f.sender where f.receiver.id = :receiverId")
    List<Follow> findFollowersByIdFetch(Long receiverId);

    @Query("select f from Follow f left join fetch f.receiver where f.sender.id = :senderId")
    List<Follow> findFollowingsByIdFetch(Long senderId);
}
