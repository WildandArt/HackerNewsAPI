package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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

public class VOTEPostServiceImplTest {

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
    void testUpVote_FindPostById() {
        System.out.println("Running test: testUpVote_FindPostById");

        Long postId = 1L;

        // Step 1: Mocking the NewsPostModel object
        NewsPostModel post = mock(NewsPostModel.class);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Step 2: Call the upVote method
        postService.upVote(postId);

        // Step 3: Verify that the post was retrieved from the repository
        verify(postRepository).findById(postId);
        
        System.out.println("Post retrieved successfully.");
    }

    // @Test
    // void testUpVote_CacheExists() {
    //     System.out.println("Running test: testUpVote_CacheExists");

    //     Long postId = 1L;
    //     NewsPostModel post = mock(NewsPostModel.class);
    //     when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    //     when(post.getCurrentVotes()).thenReturn(5); // Mock the vote count

    //     PostResponseDTO responseDTO = new PostResponseDTO();
    //     responseDTO.setPostId(postId);  // Mock setting post ID

    //     when(modelMapper.map(any(NewsPostModel.class), eq(PostResponseDTO.class))).thenReturn(responseDTO);
    //     when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(responseDTO);

    //     System.out.println("Calling postService.upVote()...");
    //     PostResponseDTO result = postService.upVote(postId);

    //     System.out.println("Asserting results...");
    //     assertNotNull(result);
    //     assertEquals("Vote updated successfully", result.getMessage());
    //     verify(postRepository).save(post);
    //     verify(cacheService).put(postId, responseDTO);

    //     System.out.println("testUpVote_CacheExists completed successfully.\n");
    // }


// @Test
// void testUpVote_CacheDoesNotExist() {
//     System.out.println("Running test: testUpVote_CacheDoesNotExist");

//     Long postId = 1L;
//     NewsPostModel post = mock(NewsPostModel.class);
//     when(post.getPostId()).thenReturn(postId);
//     when(post.getCurrentVotes()).thenReturn(10);

//     PostResponseDTO responseDTO = new PostResponseDTO();
//     responseDTO.setPostId(postId);

//     when(postRepository.findById(postId)).thenReturn(Optional.of(post));
//     when(modelMapper.map(any(NewsPostModel.class), eq(PostResponseDTO.class))).thenReturn(responseDTO);
//     when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(null);

//     System.out.println("Calling postService.upVote()...");
//     PostResponseDTO result = postService.upVote(postId);

//     System.out.println("Asserting results...");
//     assertNotNull(result);
//     assertEquals("Vote updated successfully", result.getMessage());
//     verify(postRepository).save(post);
//     verify(cacheService, never()).put(anyLong(), any(PostResponseDTO.class));

//     System.out.println("testUpVote_CacheDoesNotExist completed successfully.\n");
// }


    // @Test
    // void testDownVote_CacheExists() {
    //     System.out.println("Running test: testDownVote_CacheExists");

    //     Long postId = 1L;
    //     NewsPostModel post = mock(NewsPostModel.class);
    //     when(post.getPostId()).thenReturn(postId);
    //     when(post.getCurrentVotes()).thenReturn(10);
    //     when(post.getTimeElapsed()).thenReturn(5); // Mocked method call

    //     PostResponseDTO responseDTO = new PostResponseDTO();
    //     responseDTO.setPostId(postId);

    //     when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    //     when(modelMapper.map(any(NewsPostModel.class), eq(PostResponseDTO.class))).thenReturn(responseDTO);
    //     when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(responseDTO);

    //     System.out.println("Calling postService.downVote()...");
    //     PostResponseDTO result = postService.downVote(postId);

    //     System.out.println("Asserting results...");
    //     assertNotNull(result);
    //     assertEquals("Vote updated successfully", result.getMessage());
    //     verify(postRepository).save(post);
    //     verify(cacheService).put(postId, responseDTO);

    //     System.out.println("testDownVote_CacheExists completed successfully.\n");
    // }

    // @Test
    // void testDownVote_CacheDoesNotExist() {
    //     System.out.println("Running test: testDownVote_CacheDoesNotExist");

    //     Long postId = 1L;
    //     NewsPostModel post = mock(NewsPostModel.class);
    //     when(post.getPostId()).thenReturn(postId);
    //     when(post.getCurrentVotes()).thenReturn(10);
    //     when(post.getTimeElapsed()).thenReturn(5); // Mocked method call

    //     PostResponseDTO responseDTO = new PostResponseDTO();
    //     responseDTO.setPostId(postId);

    //     when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    //     when(modelMapper.map(any(NewsPostModel.class), eq(PostResponseDTO.class))).thenReturn(responseDTO);
    //     when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(null);

    //     System.out.println("Calling postService.downVote()...");
    //     PostResponseDTO result = postService.downVote(postId);

    //     System.out.println("Asserting results...");
    //     assertNotNull(result);
    //     assertEquals("Vote updated successfully", result.getMessage());
    //     verify(postRepository).save(post);
    //     verify(cacheService, never()).put(anyLong(), any(PostResponseDTO.class));

    //     System.out.println("testDownVote_CacheDoesNotExist completed successfully.\n");
    // }

    
}
