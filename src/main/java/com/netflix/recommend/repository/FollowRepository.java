package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Follow;
import com.netflix.recommend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsBySenderAndReceiver(User sender, User receiver);
}
