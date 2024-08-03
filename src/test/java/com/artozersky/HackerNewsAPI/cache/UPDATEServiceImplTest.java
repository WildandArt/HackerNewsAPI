package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;

public class UPDATEServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CacheServiceImpl<Long, PostResponseDTO> cacheService;

    @InjectMocks
    private PostServiceImpl postService;

    private NewsPostModel mockPost;
    private PostUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set up mock post data
        mockPost = new NewsPostModel();
        mockPost.setPostId(1L);
        mockPost.setTitle("Old Title");
        mockPost.setUrl("http://old-url.com");
        mockPost.setCurrentVotes(10);  // Initialize currentVotes to avoid NullPointerException
        
        // Set up update DTO
        updateDTO = new PostUpdateDTO();
        updateDTO.setTitle("New Title");
        updateDTO.setUrl("http://new-url.com");
    }

    @Test
    void testUpdatePost_CacheExists() {
        System.out.println("Running test: testUpdatePost_CacheExists");

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(mockPost));
        when(cacheService.get(1L, PostResponseDTO.class)).thenReturn(new PostResponseDTO());
        when(postRepository.save(any(NewsPostModel.class))).thenReturn(mockPost);
        when(modelMapper.map(mockPost, PostResponseDTO.class)).thenReturn(new PostResponseDTO());

        postService.updatePost(updateDTO, 1L);

        verify(cacheService).put(eq(1L), any(PostResponseDTO.class));
        verify(postRepository).save(mockPost);

        System.out.println("testUpdatePost_CacheExists completed successfully.");
    }

    @Test
    void testUpdatePost_CacheDoesNotExist() {
        System.out.println("Running test: testUpdatePost_CacheDoesNotExist");

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(mockPost));
        when(cacheService.get(1L, PostResponseDTO.class)).thenReturn(null);
        when(postRepository.save(any(NewsPostModel.class))).thenReturn(mockPost);
        when(modelMapper.map(mockPost, PostResponseDTO.class)).thenReturn(new PostResponseDTO());

        postService.updatePost(updateDTO, 1L);

        verify(cacheService, never()).put(eq(1L), any(PostResponseDTO.class));
        verify(postRepository).save(mockPost);

        System.out.println("testUpdatePost_CacheDoesNotExist completed successfully.");
    }
}
