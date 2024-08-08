// package com.artozersky.HackerNewsAPI.cache;

// import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityImpl;
// import com.artozersky.HackerNewsAPI.cache.impl.CacheEntityServiceImpl;
// import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.Mockito;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class CacheEntityServiceImplTest {

//     @Mock
//     private CacheEntityImpl cacheEntity;

//     @InjectMocks
//     private CacheEntityServiceImpl cacheEntityService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testGetPostFromCacheById() {
//         NewsPostModelImpl post = new NewsPostModelImpl();
//         post.setPostId(1L);

//         when(cacheEntity.get(1L)).thenReturn(post);

//         NewsPostModelImpl result = cacheEntityService.getPostFromCacheById(1L);

//         assertNotNull(result);
//         assertEquals(1L, result.getPostId());
//         verify(cacheEntity, times(1)).get(1L);
//     }

//     @Test
//     void testGetAllPostsFromCacheWithStaleData() {
//         List<NewsPostModelImpl> cachedPosts = new ArrayList<>();
//         NewsPostModelImpl post1 = new NewsPostModelImpl();
//         post1.setPostId(1L);
//         post1.setCreatedAt(LocalDateTime.now().minusHours(2));
//         post1.setTimeElapsed(1); // One hour elapsed since creation

//         cachedPosts.add(post1);

//         when(cacheEntity.getAll()).thenReturn(cachedPosts);

//         List<NewsPostModelImpl> result = cacheEntityService.getAllPostsFromCache();

//         assertTrue(result.isEmpty()); // Because cache should be invalidated due to stale data
//         verify(cacheEntity, times(1)).clear();
//     }

//     @Test
//     void testPutPostInCache() {
//         NewsPostModelImpl post = new NewsPostModelImpl();
//         post.setPostId(1L);

//         cacheEntityService.putPostInCache(post);

//         verify(cacheEntity, times(1)).put(1L, post);
//     }

//     @Test
//     void testPutAllPostsInCache() {
//         List<NewsPostModelImpl> posts = new ArrayList<>();
//         NewsPostModelImpl post1 = new NewsPostModelImpl();
//         post1.setPostId(1L);

//         NewsPostModelImpl post2 = new NewsPostModelImpl();
//         post2.setPostId(2L);

//         posts.add(post1);
//         posts.add(post2);

//         cacheEntityService.putAllPostsInCache(posts);

//         verify(cacheEntity, times(1)).putAll(posts);
//     }

//     @Test
//     void testEvictPost() {
//         Long postId = 1L;

//         cacheEntityService.evictPost(postId);

//         verify(cacheEntity, times(1)).evict(postId);
//     }

//     @Test
//     void testClearCache() {
//         cacheEntityService.clearCache();

//         verify(cacheEntity, times(1)).clear();
//     }
// }
