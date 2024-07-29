package com.artozersky.HackerNewsAPI.repository;

import com.artozersky.HackerNewsAPI.model.Post;
import com.artozersky.HackerNewsAPI.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAndRetrievePost() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setKarma(100);
        user.setAbout("Test user about section");
        user = userRepository.save(user);

        // Create a new post
        Post post = new Post();
        post.setUser(user);
        post.setAuthor("John Doe");
        post.setTitle("Interesting Article");
        post.setUrl("http://example.com/interesting-article");
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setCurrentVotes(0);
        post.setScore(0.0);
        post.setCreatedHoursAgo(0);

        // Save the post
        Post savedPost = postRepository.save(post);

        // Retrieve the post
        Post retrievedPost = postRepository.findById(savedPost.getPostId()).orElse(null);

        // Verify the post
        assertThat(retrievedPost).isNotNull();
        assertThat(retrievedPost.getAuthor()).isEqualTo(post.getAuthor());
        assertThat(retrievedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(retrievedPost.getUrl()).isEqualTo(post.getUrl());
        assertThat(retrievedPost.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(retrievedPost.getCreatedAt()).isNotNull();
        assertThat(retrievedPost.getCurrentVotes()).isEqualTo(post.getCurrentVotes());
        assertThat(retrievedPost.getScore()).isEqualTo(post.getScore());
        assertThat(retrievedPost.getCreatedHoursAgo()).isEqualTo(post.getCreatedHoursAgo());
    }
}
