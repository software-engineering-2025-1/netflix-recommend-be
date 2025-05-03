package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Group;
import com.netflix.recommend.entity.Participant;
import com.netflix.recommend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Boolean existsByUserAndGroup(User user, Group group);
}
