package com.artozersky.HackerNewsAPI.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

/**
 * Repository interface for managing {@link NewsPostModelImpl} entities.
 */
@Repository
public interface PostRepository extends JpaRepository<NewsPostModelImpl, Long> {
    /**
     * Finds all posts and orders them by score in descending order.
     *
     * @return A list of {@link NewsPostModelImpl} objects ordered by their score in descending order.
     */
    List<NewsPostModelImpl> findAllByOrderByScoreDesc();
}
