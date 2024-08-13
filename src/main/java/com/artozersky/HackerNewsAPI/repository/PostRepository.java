package com.artozersky.HackerNewsAPI.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

/**
 * Repository interface for managing {@link NewsPostModelImpl} entities.
 * Provides methods for retrieving posts based on their score and other criteria.
 */
@Repository
public interface PostRepository extends JpaRepository<NewsPostModelImpl, Long> {

Page<NewsPostModelImpl> findAll(Pageable pageable);

/**
 * Finds all posts and orders them by score in descending order.
 *
 * @return A list of {@link NewsPostModelImpl} objects ordered by their score in descending order.
 */
List<NewsPostModelImpl> findAllByOrderByScoreDesc();

/**
 * Finds the top posts by score excluding specific IDs and orders them by score in descending order.
 *
 * @param excludedIds A list of post IDs to exclude from the results.
 * @param pageable    The pagination information (e.g., page size).
 * @return A list of {@link NewsPostModelImpl} objects excluding the specified IDs,
 *         ordered by their score in descending order.
 */
@Query("SELECT p FROM NewsPostModelImpl p WHERE p.postId NOT IN :excludedIds ORDER BY p.score DESC")
List<NewsPostModelImpl> findTopPostsByScoreExcludingIds(
        @Param("excludedIds") List<Long> excludedIds, 
        Pageable pageable
);

// @Query("SELECT p FROM NewsPostModelImpl p WHERE p.postId NOT IN :excludedIds ORDER BY p.score DESC")
// NewsPostModelImpl findTopPostByScoreExcludingIds(@Param("excludedIds") List<Long> excludedIds);

@Query("SELECT p FROM NewsPostModelImpl p WHERE p.postId NOT IN :excludedIds ORDER BY p.score DESC, p.postId DESC")
List<NewsPostModelImpl> findTopPostByScoreExcludingIds(@Param("excludedIds") List<Long> excludedIds);

/**
 * Finds the top posts by score and orders them by score in descending order.
 *
 * @param pageable The pagination information (e.g., page size).
 * @return A list of {@link NewsPostModelImpl} objects ordered by their score in descending order.
 */
@Query("SELECT p FROM NewsPostModelImpl p ORDER BY p.score DESC")
List<NewsPostModelImpl> findTopPostsByScore(Pageable pageable);

}
