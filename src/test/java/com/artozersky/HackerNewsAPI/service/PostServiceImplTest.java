import com.artozersky.HackerNewsAPI.cache.CacheServiceImpl;
import com.artozersky.HackerNewsAPI.dto.PostResponseDTO;
import com.artozersky.HackerNewsAPI.model.NewsPostModel;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

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
    }

    @Test
    void testGetPostById_CacheHit() {
        // Arrange
        Long postId = 1L;
        PostResponseDTO cachedPost = new PostResponseDTO();
        cachedPost.setPostId(postId);

        when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(cachedPost);

        // Act
        PostResponseDTO result = postService.getPostById(postId);

        // Assert
        assertEquals(postId, result.getPostId());
        verify(cacheService, times(1)).get(postId, PostResponseDTO.class);
        verify(postRepository, never()).findById(any());
    }

    @Test
    void testGetPostById_CacheMiss() {
        // Arrange
        Long postId = 1L;
        NewsPostModel postModel = new NewsPostModel();
        postModel.setPostId(postId);

        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setPostId(postId);

        when(cacheService.get(postId, PostResponseDTO.class)).thenReturn(null);
        when(postRepository.findById(postId)).thenReturn(Optional.of(postModel));
        when(modelMapper.map(postModel, PostResponseDTO.class)).thenReturn(postResponseDTO);

        // Act
        PostResponseDTO result = postService.getPostById(postId);

        // Assert
        assertEquals(postId, result.getPostId());
        verify(cacheService, times(1)).get(postId, PostResponseDTO.class);
        verify(postRepository, times(1)).findById(postId);
        verify(cacheService, times(1)).put(postId, postResponseDTO);
    }
}
