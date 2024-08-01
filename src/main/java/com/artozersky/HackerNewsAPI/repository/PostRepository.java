package com.artozersky.HackerNewsAPI.repository;

import com.artozersky.HackerNewsAPI.model.*;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<NewsPostModel, Long> {
    List<NewsPostModel> findByUserId(Long userId);
    List<NewsPostModel> findAllByOrderByScoreDesc();
}
