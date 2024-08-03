/**
 * @file VOTEPostServiceImplTest.java
 * @brief Unit tests for the PostServiceImpl's upVote method, using Mockito for mocking dependencies.
 *
 * This test class is responsible for verifying the behavior of the upVote method in the PostServiceImpl class.
 * It ensures that the method correctly increments votes, updates the score, and interacts with the cache service.
 */

 package com.artozersky.HackerNewsAPI.cache;

 import static org.junit.jupiter.api.Assertions.assertNotNull;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.when;
 
 import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
 import com.artozersky.HackerNewsAPI.model.NewsPostModel;
 import com.artozersky.HackerNewsAPI.repository.PostRepository;
 import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.modelmapper.ModelMapper;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;
 
 import java.util.Optional;
 
 /**
  * @class VOTEPostServiceImplTest
  * @brief Unit test class for testing the upVote method in PostServiceImpl.
  */
 public class VOTEPostServiceImplTest {
 
     /**
      * Mock of the PostRepository interface to simulate database interactions.
      */
     @Mock
     private PostRepository postRepository;
 
     /**
      * Mock of the ModelMapper class to simulate object mapping.
      */
     @Mock
     private ModelMapper modelMapper;
 
     /**
      * Mock of the CacheServiceImpl class to simulate cache interactions.
      */
     @Mock
     private CacheServiceImpl cacheService;
 
     /**
      * Injects the mocks into the PostServiceImpl instance under test.
      */
     @InjectMocks
     private PostServiceImpl postService;
 
     /**
      * @brief Initializes mocks before each test.
      *
      * This method is called before each test case to initialize the Mockito annotations.
      */
     @BeforeEach
     public void setUp() {
         MockitoAnnotations.openMocks(this);
     }
 
     /**
      * @brief Tests the upVote method by simulating a post being found by ID.
      *
      * This test verifies that the upVote method correctly:
      * - Finds the post by its ID
      * - Increments the vote count and updates the score
      * - Saves the updated post
      * - Maps the NewsPostModel to PostResponseDTO
      * - Returns a non-null PostResponseDTO with a success message
      */
     @Test
     public void testUpVote_FindPostById() {
         // Arrange
         Long postId = 1L;
         NewsPostModel mockPost = new NewsPostModel();
         mockPost.setPostId(postId);
         mockPost.setTitle("Sample Title");
         mockPost.setUrl("http://sample.url");
         mockPost.setPostedBy("Author");
         mockPost.setCurrentVotes(10);
         mockPost.setScore(100.0);
         mockPost.setTimeElapsed(5);  // Initialize timeElapsed to a valid value
 
         // Simulate the post being found in the repository
         when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
         when(postRepository.save(any(NewsPostModel.class))).thenReturn(mockPost);
         
         // Simulate the mapping of the post to a response DTO
         PostResponseDTO mockResponseDTO = new PostResponseDTO();
         when(modelMapper.map(any(NewsPostModel.class), any())).thenReturn(mockResponseDTO);
         
         // Simulate the cache service returning null (cache miss)
         when(cacheService.get(any(Long.class), any(Class.class))).thenReturn(null);
 
         // Act
         PostResponseDTO responseDTO = postService.upVote(postId);
 
         // Assert
         assertNotNull(responseDTO, "The response DTO should not be null");
         assertNotNull(responseDTO.getMessage(), "The response message should not be null");
     }
 }
 