package com.artozersky.HackerNewsAPI.repository;

import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link NewsPostModel} entities.
 */
@Repository
public interface PostRepository extends JpaRepository<NewsPostModel, Long> {

    /**
     * Finds all posts by a specific user ID.
     *
     * @param userId The ID of the user whose posts are to be retrieved.
     * @return A list of {@link NewsPostModel} objects associated with the specified user ID.
     */
    List<NewsPostModel> findByUserId(Long userId);

    /**
     * Finds all posts and orders them by score in descending order.
     *
     * @return A list of {@link NewsPostModel} objects ordered by their score in descending order.
     */
    List<NewsPostModel> findAllByOrderByScoreDesc();
}
