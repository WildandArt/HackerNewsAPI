package com.artozersky.HackerNewsAPI.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

@Repository
public interface PostRepository extends JpaRepository<NewsPostModelImpl, Long> {

    /**
     * @brief Retrieves all posts with pagination support.
     *
     * @param pageable The pagination information (e.g., page number, size).
     * @return A paginated list of {@link NewsPostModelImpl} objects.
     */
    Page<NewsPostModelImpl> findAll(Pageable pageable);

    /**
     * @brief Finds all posts ordered by score in descending order.
     *
     * @return A list of {@link NewsPostModelImpl} objects ordered by their score in descending order.
     */
    List<NewsPostModelImpl> findAllByOrderByScoreDesc();

    /**
     * @brief Finds the top posts by score, excluding specific post IDs.
     *
     * This method retrieves the top posts by score while excluding the posts 
     * with the specified IDs. The results are ordered by score in descending 
     * order and support pagination.
     *
     * @param excludedIds A list of post IDs to exclude from the results.
     * @param pageable    The pagination information (e.g., page size).
     * @return A list of {@link NewsPostModelImpl} objects excluding the specified IDs,
     *         ordered by their score in descending order.
     */
    @Query("SELECT p FROM NewsPostModelImpl p WHERE p.postId NOT IN :excludedIds ORDER BY p.score DESC")
    List<NewsPostModelImpl> findTopPostsByScoreExcludingIds(
            @Param("excludedIds") List<Long> excludedIds, 
            Pageable pageable);

    /**
     * @brief Finds the top post by score, excluding specific post IDs.
     *
     * This method retrieves the top post by score while excluding the posts 
     * with the specified IDs. The results are ordered by score in descending 
     * order and by post ID in descending order.
     *
     * @param excludedIds A list of post IDs to exclude from the results.
     * @return A list of {@link NewsPostModelImpl} objects excluding the specified IDs,
     *         ordered by their score and post ID in descending order.
     */
    @Query("SELECT p FROM NewsPostModelImpl p WHERE p.postId NOT IN :excludedIds ORDER BY p.score DESC, p.postId DESC")
    List<NewsPostModelImpl> findTopPostByScoreExcludingIds(@Param("excludedIds") List<Long> excludedIds);

    /**
     * @brief Finds the top posts by score with pagination support.
     *
     * This method retrieves the top posts by score and orders them in 
     * descending order of their score, with support for pagination.
     *
     * @param pageable The pagination information (e.g., page size).
     * @return A list of {@link NewsPostModelImpl} objects ordered by their score in descending order.
     */
    @Query("SELECT p FROM NewsPostModelImpl p ORDER BY p.score DESC")
    List<NewsPostModelImpl> findTopPostsByScore(Pageable pageable);
}
