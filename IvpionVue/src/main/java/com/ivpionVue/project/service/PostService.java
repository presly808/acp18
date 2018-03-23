package com.ivpionVue.project.service;

import com.ivpionVue.project.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<Post> getAllPosts();

    Post getPostById(int id);

    Post addPost(Post post);

    Post deletePost(int id) throws Exception;

    Post updatePost(Post updatePost);

}
