package com.artozersky.HackerNewsAPI.service.impl;

import com.artozersky.HackerNewsAPI.dto.PostCreateDTO;
import com.artozersky.HackerNewsAPI.dto.PostUpdateDTO;
import com.artozersky.HackerNewsAPI.model.Post;

import com.artozersky.HackerNewsAPI.model.User;
import com.artozersky.HackerNewsAPI.repository.PostRepository;
import com.artozersky.HackerNewsAPI.repository.UserRepository;
import com.artozersky.HackerNewsAPI.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

// please space one line between objects and functions.
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /* List of posts sorted by score field. */
    @Override
    public List<Post> getSortedPostsByScore() {
        return postRepository.findAllByOrderByScoreDesc();
    }

    @Override
    public Post savePost(PostCreateDTO postCreateDTO) { // make this function exception safe.
        User user = userRepository.findById(postCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // read about ModelMapper and implement mapper to transition between dto to full
        // schema
        Post post = new Post();
        post.setTitle(postCreateDTO.getTitle());
        post.setUrl(postCreateDTO.getUrl());
        post.setUserId(postCreateDTO.getUserId());

        post.setAuthor(user.getUsername());

        post.setCurrentVotes(0);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        double timeInHours = 0; // remove this
        post.setScore(ScoreCalculator.calculateScore(0, 0, ScoreCalculator.GRAVITY));
        post.setCreatedHoursAgo((int) timeInHours); // insert 0
        return postRepository.save(post);
    }

    // remove those help functions to the place they belong to, please think wher.
    // dont create a new impl and interface for this.
    public static class ScoreCalculator {

        private static final double GRAVITY = 1.8;

        public static double calculateScore(double points, double timeInHours, double gravity) {
            return (points - 1) / Math.pow((timeInHours + 2), gravity);
        }
    }

    public class TimeUtils {
        public static double calculateHoursAgo(Timestamp createdAt) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            long msSinceCreation = now.getTime() - createdAt.getTime();
            return msSinceCreation / (1000.0 * 60 * 60);
        }
    }

    // make this function exception safe.
    // think of the user updating a empty json what do do how to prevent updating.
    @Override
    public Post updatePost(Long postId, PostUpdateDTO postUpdateDTO) { // change the order of parameters to be DTO, id
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        // remove this
        /* if both null, return the post */
        // if( postUpdateDTO.getTitle() != null && postUpdateDTO.getUrl() != null){
        // return post;
        // }
        if (postUpdateDTO.getTitle() != null) {
            post.setTitle(postUpdateDTO.getTitle());
        }
        if (postUpdateDTO.getUrl() != null) {
            post.setUrl(postUpdateDTO.getUrl());
        }

        /* fields that need to be recalculated at each update */ // create a function in model update()
        Integer currentVotes = post.getCurrentVotes();
        post.setCreatedHoursAgo(0);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setScore(ScoreCalculator.calculateScore(currentVotes, 0, ScoreCalculator.GRAVITY));

        return postRepository.save(post);
    }

    // exception safe, read how not to crash your server and send the client the
    // error.
    // think of how to restrict this function to get only 1 or -1.
    @Override
    public Post updateVote(Long id, Integer byNum) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        Integer updatedVotesNumber = post.getCurrentVotes() + byNum; // create an upvote and downvote function in the
                                                                     // schema.
        post.setCurrentVotes(updatedVotesNumber);
        /* update score field */
        Double hoursAgoDouble = TimeUtils.calculateHoursAgo(post.getCreatedAt());
        post.setScore(ScoreCalculator.calculateScore(updatedVotesNumber, hoursAgoDouble, ScoreCalculator.GRAVITY));
        /* updating the field hoursAgo */
        Integer hoursAgoInt = (int) Math.floor(hoursAgoDouble);
        post.setCreatedHoursAgo(hoursAgoInt);

        return postRepository.save(post);
    }
}
