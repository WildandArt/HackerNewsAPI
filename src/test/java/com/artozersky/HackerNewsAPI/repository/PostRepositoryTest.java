package com.artozersky.HackerNewsAPI.repository;

import com.artozersky.HackerNewsAPI.model.NewsPostModel; 
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    //@Test
    public void testCreateAndRetrievePost() {
       

    }
}
