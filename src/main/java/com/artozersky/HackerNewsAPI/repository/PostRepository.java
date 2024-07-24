package com.artozersky.HackerNewsAPI.repository;

import com.artozersky.HackerNewsAPI.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
