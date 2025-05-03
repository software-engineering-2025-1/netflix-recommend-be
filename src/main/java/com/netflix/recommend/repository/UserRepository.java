package com.netflix.recommend.repository;

import com.netflix.recommend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(Long kakaoId);

    @Query("select u from User u left join fetch u.genres left join fetch u.histories h left join fetch h.video where u.id = :id")
    Optional<User> findUserDetailByIdFetch(Long id);
}
