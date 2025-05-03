package com.netflix.recommend.repository;

import com.netflix.recommend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r left join fetch r.user where r.group.id = :groupId")
    Page<Review> findAllReviewDetailByGroupId(Long groupId, Pageable pageable);
}
