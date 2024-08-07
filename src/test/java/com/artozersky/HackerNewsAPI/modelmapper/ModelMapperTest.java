// package com.artozersky.HackerNewsAPI.modelmapper;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.time.LocalDateTime;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.modelmapper.ModelMapper;

// import com.artozersky.HackerNewsAPI.dto.impl.NewsPostsResponseDTOImpl;
// import com.artozersky.HackerNewsAPI.model.impl.NewsPostModelImpl;

// public class ModelMapperTest {

//     private ModelMapper modelMapper;

//     @BeforeEach
//     public void setUp() {
//         modelMapper = new ModelMapper();
//     }

//     @Test
//     public void testMapNewsPostModelToPostResponseDTO() {
//         // Arrange: Create a sample NewsPostModel object
//         NewsPostModelImpl newsPost = new NewsPostModelImpl();
//         newsPost.setPostId(1L);
//         newsPost.setTitle("Sample Title");
//         newsPost.setUrl("http://sample.url");
//         newsPost.setPostedBy("Author");
//         newsPost.setCurrentVotes(10);
//         newsPost.setScore(100.0);
//         newsPost.setCreatedAt(LocalDateTime.now());

//         // Act: Map NewsPostModel to PostResponseDTO
//         NewsPostsResponseDTOImpl responseDTO = modelMapper.map(newsPost, NewsPostsResponseDTOImpl.class);

//         // Assert: Verify the mapping
//         assertNotNull(responseDTO);
//         assertEquals(newsPost.getPostId(), responseDTO.getPostId());
//         assertEquals(newsPost.getTitle(), responseDTO.getTitle());
//         assertEquals(newsPost.getUrl(), responseDTO.getUrl());
//         assertEquals(newsPost.getPostedBy(), responseDTO.getPostedBy());
//         assertEquals(newsPost.getCurrentVotes(), responseDTO.getCurrentVotes());
//         assertEquals(newsPost.getScore(), responseDTO.getScore());
//         assertEquals(newsPost.getCreatedAt(), responseDTO.getCreatedAt());
//     }
// }
