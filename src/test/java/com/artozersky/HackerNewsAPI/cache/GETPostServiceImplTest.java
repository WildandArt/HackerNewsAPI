
// package com.artozersky.HackerNewsAPI.cache;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.*;

// import java.util.List;
// import java.util.concurrent.Callable;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.modelmapper.ModelMapper;
// import org.springframework.core.ParameterizedTypeReference;

// import com.artozersky.HackerNewsAPI.cache.CacheServiceImpl;
// import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
// import com.artozersky.HackerNewsAPI.model.NewsPostModel;
// import com.artozersky.HackerNewsAPI.repository.PostRepository;
// import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;

// public class GETPostServiceImplTest {

//     @Mock
//     private PostRepository postRepository;

//     @Mock
//     private ModelMapper modelMapper;

//     @Mock
//     private CacheServiceImpl<String, List<PostResponseDTO>> allPostsCacheService;

//     @InjectMocks
//     private PostServiceImpl postService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         System.out.println("Setting up mocks and injectMocks...");
//     }

//     @Test
//     @SuppressWarnings("unchecked")
//     void testGetAllPosts_FromCache() {
//         System.out.println("Running test: testGetAllPosts_FromCache");

//         String cacheKey = "all_posts";
//         List<PostResponseDTO> cachedPosts = List.of(new PostResponseDTO());
        
//         System.out.println("Simulating cache hit...");
//         when(allPostsCacheService.getFromCacheOrDb(
//             eq(cacheKey), 
//             any(ParameterizedTypeReference.class), 
//             any()))
//                 .thenReturn(cachedPosts);

//         System.out.println("Calling postService.getAllPosts()...");
//         List<PostResponseDTO> result = postService.getAllPosts();

//         System.out.println("Asserting results...");
//         assertNotNull(result);
//         assertEquals(cachedPosts.size(), result.size());
//         verify(postRepository, never()).findAll(); // Ensure DB is not hit

//         System.out.println("testGetAllPosts_FromCache completed successfully.\n");
//     }

//     @Test
//     @SuppressWarnings("unchecked")
//     void testGetAllPosts_FromDB() throws Exception {
//         System.out.println("Running test: testGetAllPosts_FromDB");

//         String cacheKey = "all_posts";
//         List<NewsPostModel> posts = List.of(new NewsPostModel());
//         List<PostResponseDTO> postResponseDTOs = posts.stream()
//                 .map(post -> new PostResponseDTO())
//                 .collect(Collectors.toList());

//         System.out.println("Simulating fetching from DB because cache is empty...");
//         when(postRepository.findAll()).thenReturn(posts);
//         when(modelMapper.map(posts.get(0), PostResponseDTO.class)).thenReturn(postResponseDTOs.get(0));
//         when(allPostsCacheService.getFromCacheOrDb(eq(cacheKey), any(ParameterizedTypeReference.class), any()))
//                 .thenAnswer(invocation -> {
//                     Callable<List<PostResponseDTO>> dbFetch = invocation.getArgument(2, Callable.class);
//                     System.out.println("Cache is empty, calling dbFetch...");
//                     return dbFetch.call();
//                 });

//         System.out.println("Calling postService.getAllPosts()...");
//         List<PostResponseDTO> result = postService.getAllPosts();

//         System.out.println("Asserting results...");
//         assertNotNull(result);
//         assertEquals(postResponseDTOs.size(), result.size());
//         verify(allPostsCacheService).put(cacheKey, postResponseDTOs); // Verify cache is updated

//         System.out.println("testGetAllPosts_FromDB completed successfully.\n");
//     }
// }
package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;

import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;

public class GETPostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CacheServiceImpl<String, List<PostResponseDTO>> allPostsCacheService;

    @Mock
    private CacheServiceImpl<Long, PostResponseDTO> cacheService;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Setting up mocks and injectMocks...");
    }

    // Existing tests for getAllPosts()
    @Test
    @SuppressWarnings("unchecked")
    void testGetAllPosts_FromCache() {
        System.out.println("Running test: testGetAllPosts_FromCache");

        String cacheKey = "all_posts";
        List<PostResponseDTO> cachedPosts = List.of(new PostResponseDTO());
        
        System.out.println("Simulating cache hit...");
        when(allPostsCacheService.getFromCacheOrDb(
            eq(cacheKey), 
            any(ParameterizedTypeReference.class), 
            any()))
                .thenReturn(cachedPosts);

        System.out.println("Calling postService.getAllPosts()...");
        List<PostResponseDTO> result = postService.getAllPosts();

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(cachedPosts.size(), result.size());
        verify(postRepository, never()).findAll(); // Ensure DB is not hit

        System.out.println("testGetAllPosts_FromCache completed successfully.\n");
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetAllPosts_FromDB() throws Exception {
        System.out.println("Running test: testGetAllPosts_FromDB");

        String cacheKey = "all_posts";
        List<NewsPostModel> posts = List.of(new NewsPostModel());
        List<PostResponseDTO> postResponseDTOs = posts.stream()
                .map(post -> new PostResponseDTO())
                .collect(Collectors.toList());

        System.out.println("Simulating fetching from DB because cache is empty...");
        when(postRepository.findAll()).thenReturn(posts);
        when(modelMapper.map(posts.get(0), PostResponseDTO.class)).thenReturn(postResponseDTOs.get(0));
        when(allPostsCacheService.getFromCacheOrDb(eq(cacheKey), any(ParameterizedTypeReference.class), any()))
                .thenAnswer(invocation -> {
                    Callable<List<PostResponseDTO>> dbFetch = invocation.getArgument(2, Callable.class);
                    System.out.println("Cache is empty, calling dbFetch...");
                    return dbFetch.call();
                });

        System.out.println("Calling postService.getAllPosts()...");
        List<PostResponseDTO> result = postService.getAllPosts();

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(postResponseDTOs.size(), result.size());
        verify(allPostsCacheService).put(cacheKey, postResponseDTOs); // Verify cache is updated

        System.out.println("testGetAllPosts_FromDB completed successfully.\n");
    }

    // New tests for getPostById()
    @Test
    void testGetPostById_FromCache() {
        System.out.println("Running test: testGetPostById_FromCache");

        Long postId = 1L;
        PostResponseDTO cachedPost = new PostResponseDTO();
        cachedPost.setPostId(postId);
        cachedPost.setCreatedAt(LocalDateTime.now().minusMinutes(30)); // Cache is still fresh

        System.out.println("Simulating cache hit...");
        when(cacheService.getFromCacheOrDb(eq(postId), eq(PostResponseDTO.class), any(Callable.class)))
            .thenReturn(cachedPost);

        System.out.println("Calling postService.getPostById()...");
        PostResponseDTO result = postService.getPostById(postId);

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(postId, result.getPostId());
        verify(postRepository, never()).findById(any()); // Ensure DB is not hit
        verify(cacheService, never()).put(anyLong(), any(PostResponseDTO.class)); // Ensure cache is not refreshed

        System.out.println("testGetPostById_FromCache completed successfully.\n");
    }

    @Test
    void testGetPostById_FromDB_AndRefreshCache() {
        System.out.println("Running test: testGetPostById_FromDB_AndRefreshCache");

        Long postId = 1L;
        PostResponseDTO stalePost = new PostResponseDTO();
        stalePost.setPostId(postId);
        stalePost.setCreatedAt(LocalDateTime.now().minusHours(2)); // Cache is stale

        NewsPostModel postFromDB = new NewsPostModel();
        postFromDB.setPostId(postId);

        PostResponseDTO freshPost = new PostResponseDTO();
        freshPost.setPostId(postId);

        System.out.println("Simulating cache miss and fetching from DB...");
        when(cacheService.getFromCacheOrDb(eq(postId), eq(PostResponseDTO.class), any(Callable.class)))
            .thenReturn(stalePost);
        when(postRepository.findById(postId)).thenReturn(Optional.of(postFromDB));
        when(modelMapper.map(postFromDB, PostResponseDTO.class)).thenReturn(freshPost);

        System.out.println("Calling postService.getPostById()...");
        PostResponseDTO result = postService.getPostById(postId);

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(postId, result.getPostId());
        verify(postRepository).findById(postId); // Ensure DB is hit
        verify(cacheService).put(eq(postId), eq(freshPost)); // Ensure cache is refreshed

        System.out.println("testGetPostById_FromDB_AndRefreshCache completed successfully.\n");
    }

    // New tests for getSortedPostsByScore()
    @Test
    @SuppressWarnings("unchecked")
    void testGetSortedPostsByScore_FromCache() {
        System.out.println("Running test: testGetSortedPostsByScore_FromCache");

        String cacheKey = "sorted_posts";
        List<PostResponseDTO> cachedPosts = List.of(new PostResponseDTO());

        System.out.println("Simulating cache hit...");
        when(allPostsCacheService.getFromCacheOrDb(eq(cacheKey), any(ParameterizedTypeReference.class), any()))
            .thenReturn(cachedPosts);

        System.out.println("Calling postService.getSortedPostsByScore()...");
        List<PostResponseDTO> result = postService.getSortedPostsByScore();

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(cachedPosts.size(), result.size());
        verify(postRepository, never()).findAllByOrderByScoreDesc(); // Ensure DB is not hit

        System.out.println("testGetSortedPostsByScore_FromCache completed successfully.\n");
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetSortedPostsByScore_FromDB_AndRefreshCache() throws Exception {
        System.out.println("Running test: testGetSortedPostsByScore_FromDB_AndRefreshCache");

        String cacheKey = "sorted_posts";
        List<NewsPostModel> postsFromDB = List.of(new NewsPostModel());
        List<PostResponseDTO> postResponseDTOs = List.of(new PostResponseDTO());

        System.out.println("Simulating fetching from DB because cache is empty...");
        when(postRepository.findAllByOrderByScoreDesc()).thenReturn(postsFromDB);
        when(modelMapper.map(postsFromDB.get(0), PostResponseDTO.class)).thenReturn(postResponseDTOs.get(0));
        when(allPostsCacheService.getFromCacheOrDb(eq(cacheKey), any(ParameterizedTypeReference.class), any()))
            .thenAnswer(invocation -> {
                Callable<List<PostResponseDTO>> dbFetch = invocation.getArgument(2, Callable.class);
                System.out.println("Cache is empty, calling dbFetch...");
                return dbFetch.call();
            });

        System.out.println("Calling postService.getSortedPostsByScore()...");
        List<PostResponseDTO> result = postService.getSortedPostsByScore();

        System.out.println("Asserting results...");
        assertNotNull(result);
        assertEquals(postResponseDTOs.size(), result.size());
        verify(allPostsCacheService).put(cacheKey, postResponseDTOs); // Ensure cache is refreshed

        System.out.println("testGetSortedPostsByScore_FromDB_AndRefreshCache completed successfully.\n");
    }
}
