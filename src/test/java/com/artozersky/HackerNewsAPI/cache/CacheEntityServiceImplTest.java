// package com.artozersky.HackerNewsAPI.cache;

// import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityImpl;
// import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityServiceImpl;
// import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
// import com.artozersky.HackerNewsAPI.repository.PostRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.mockito.Mockito;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;


// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityServiceImpl;
// import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
// import com.artozersky.HackerNewsAPI.repository.PostRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class)
// class CacheEntityServiceImplTest {

//     @Mock
//     private PostRepository postRepository;

//     private CacheEntityServiceImpl cacheEntityService;

//     @BeforeEach
//     public void setUp() {
//         int maxSize = 100;  // Provide the required cache size
//         cacheEntityService = new CacheEntityServiceImpl(maxSize);
//     }

//     @Test
//     public void testPutPostInCache() {
//         NewsPostModelImpl modelImpl = NewsPostModelImpl.builder()
//             .postId(1L)
//             .title("Test Post")
//             .url("http://example.com")
//             .postedBy("Author")
//             .score(10.0)
//             .currentVotes(100)
//             .createdAt(LocalDateTime.now())
//             .build();

//         cacheEntityService.putPost(modelImpl);

//         NewsPostModelImpl cachedPost = cacheEntityService.getPostById(1L);
//         assertNotNull(cachedPost);
//         assertEquals("Test Post", cachedPost.getTitle());
//     }
//     @Test
//     public void testGetAllPostsFromCache() {
//         LocalDateTime now = LocalDateTime.now();
//         NewsPostModelImpl post1 = NewsPostModelImpl.builder()
//             .postId(1L)
//             .title("Post 1")
//             .url("http://example.com/1")
//             .postedBy("Author 1")
//             .score(5.0)
//             .currentVotes(50)
//             .createdAt(now.minusHours(1))
//             .timeElapsed(1)
//             .build();

//         NewsPostModelImpl post2 = NewsPostModelImpl.builder()
//             .postId(2L)
//             .title("Post 2")
//             .url("http://example.com/2")
//             .postedBy("Author 2")
//             .score(7.0)
//             .currentVotes(70)
//             .createdAt(now.minusHours(2))
//             .timeElapsed(2)
//             .build();

//         cacheEntityService.putPost(post1);
//         cacheEntityService.putPost(post2);

//         List<NewsPostModelImpl> cachedPosts = cacheEntityService.getAllPosts();
//         assertEquals(2, cachedPosts.size());
//     }

//     // @Test
//     // public void testGetAllPostsFromCacheWithStaleData() {
//     //     LocalDateTime now = LocalDateTime.now();
//     //     NewsPostModelImpl post = NewsPostModelImpl.builder()
//     //         .postId(1L)
//     //         .title("Stale Post")
//     //         .url("http://example.com/stale")
//     //         .postedBy("Author")
//     //         .score(10.0)
//     //         .currentVotes(100)
//     //         .createdAt(now.minusHours(3))
//     //         .timeElapsed(3) // Set a time that indicates the data is stale
//     //         .build();

//     //     cacheEntityService.putPostInCache(post);

//     //     List<NewsPostModelImpl> cachedPosts = cacheEntityService.getAllPostsFromCache();
//     //     assertTrue(cachedPosts.isEmpty(), "Cache should be cleared due to stale data");
//     // }

//     @Test
//     public void testEvictPost() {
//         NewsPostModelImpl modelImpl = NewsPostModelImpl.builder()
//             .postId(1L)
//             .title("Test Post")
//             .url("http://example.com")
//             .postedBy("Author")
//             .score(10.0)
//             .currentVotes(100)
//             .createdAt(LocalDateTime.now())
//             .build();

//         cacheEntityService.putPost(modelImpl);

//         cacheEntityService.evictPost(1L);
//         NewsPostModelImpl cachedPost = cacheEntityService.getPostById(1L);
//         assertNull(cachedPost, "Post should be evicted from cache");
//     }

//     @Test
//     public void testClearCache() {
//         NewsPostModelImpl modelImpl = NewsPostModelImpl.builder()
//             .postId(1L)
//             .title("Test Post")
//             .url("http://example.com")
//             .postedBy("Author")
//             .score(10.0)
//             .currentVotes(100)
//             .createdAt(LocalDateTime.now())
//             .build();

//         cacheEntityService.putPost(modelImpl);

//         cacheEntityService.clearCache();
//         NewsPostModelImpl cachedPost = cacheEntityService.getPostById(1L);
//         assertNull(cachedPost, "Cache should be cleared");
//     }
// }
