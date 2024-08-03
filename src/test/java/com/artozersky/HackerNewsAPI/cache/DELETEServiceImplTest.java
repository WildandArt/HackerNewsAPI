package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;

public class DELETEServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CacheServiceImpl<Long, PostResponseDTO> cacheService;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Setting up mocks and injectMocks...");
    }

    @Test
    void testDeletePost_CacheExists() {
        System.out.println("Running test: testDeletePost_CacheExists");

        Long postId = 1L;
        NewsPostModel post = new NewsPostModel();
        post.setPostId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        System.out.println("Calling postService.deletePost()...");
        PostResponseDTO result = postService.deletePost(postId);

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals("Post deleted successfully", result.getMessage());
        verify(postRepository).delete(post);
        verify(cacheService).evict(postId);

        System.out.println("testDeletePost_CacheExists completed successfully.\n");
    }

    @Test
    void testDeletePost_CacheDoesNotExist() {
        System.out.println("Running test: testDeletePost_CacheDoesNotExist");

        Long postId = 1L;
        NewsPostModel post = new NewsPostModel();
        post.setPostId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        System.out.println("Calling postService.deletePost()...");
        PostResponseDTO result = postService.deletePost(postId);

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals("Post deleted successfully", result.getMessage());
        verify(postRepository).delete(post);
        verify(cacheService).evict(postId);

        System.out.println("testDeletePost_CacheDoesNotExist completed successfully.\n");
    }
}
