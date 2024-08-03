package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;

public class CREATEPostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePost_Success() {
        // Arrange
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setUrl("https://example.com");

        NewsPostModel post = new NewsPostModel();
        post.setTitle("Test Title");
        post.setUrl("https://example.com");

        PostResponseDTO expectedResponseDTO = new PostResponseDTO();
        expectedResponseDTO.setPostId(1L);
        expectedResponseDTO.setMessage("Post created successfully");

        when(modelMapper.map(postCreateDTO, NewsPostModel.class)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(modelMapper.map(post, PostResponseDTO.class)).thenReturn(expectedResponseDTO);

        // Act
        PostResponseDTO result = postService.savePost(postCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponseDTO.getPostId(), result.getPostId());
        assertEquals(expectedResponseDTO.getMessage(), result.getMessage());
        verify(postRepository).save(post);
    }

    @Test
    void testSavePost_ValidationException() {
        // Arrange
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("");
        postCreateDTO.setUrl("https://example.com");

        // Act & Assert
        assertThrows(ValidationException.class, () -> postService.savePost(postCreateDTO));
        verify(postRepository, never()).save(any(NewsPostModel.class));
    }
}