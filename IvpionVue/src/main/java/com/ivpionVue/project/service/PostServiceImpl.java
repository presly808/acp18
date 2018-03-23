package com.ivpionVue.project.service;


import com.ivpionVue.project.model.Post;
import com.ivpionVue.project.repository.PostRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger LOG = Logger.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> getAllPosts() {
        LOG.info("Search posts...");
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;

    }

    @Override
    public Post getPostById(int id) {
        LOG.info("Getting post with ID:" + id);
        return postRepository.findById(id).get();
    }

    @Override
    public Post addPost(Post post) {
        LOG.info("Start saving post");
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(int id) throws Exception {
        LOG.info("Start deleting post with ID:" + id);
        Post post = postRepository.findById(id).get();
        postRepository.deleteById(id);
        return post;
    }

    @Override
    public Post updatePost(Post updatePost) {
        Post post = postRepository.findById(updatePost.getId()).get();
        post.setTitle(updatePost.getTitle());
        post.setBody(updatePost.getBody());
        return postRepository.save(post);

    }
}
