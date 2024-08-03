
package com.artozersky.HackerNewsAPI.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;

import com.artozersky.HackerNewsAPI.cache.CacheServiceImpl;
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

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Setting up mocks and injectMocks...");
    }

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
}
