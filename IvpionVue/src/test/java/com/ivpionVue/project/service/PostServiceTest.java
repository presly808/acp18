package com.ivpionVue.project.service;

import com.ivpionVue.project.model.Post;
import com.ivpionVue.project.repository.PostRepository;
import org.hamcrest.Matchers;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceTest{

    @Qualifier("postServiceImpl")
    @Autowired
    private PostService service;
    @Autowired
    private PostRepository repository;

    @Before
    public void setUp(){
        repository.deleteAll();


        Post post1 = new Post();
        post1.setId(1);
        post1.setTitle("Post1");
        post1.setBody("Body1");
        Post post2 = new Post();
        post1.setId(2);
        post2.setTitle("Post2");
        post2.setBody("Body2");
        Post post3 = new Post();
        post1.setId(3);
        post3.setTitle("Post3");
        post3.setBody("Body3");
        service.addPost(post1);
        service.addPost(post2);
        service.addPost(post3);
    }

    @After
    public void tearDown(){
        repository.deleteAll();
    }



    @Test
    public void updateTest(){
        Post updatePost = repository.findPostByTitle("Post1");
        updatePost.setTitle("osejrfksef");
        service.updatePost(updatePost);
        assertThat(repository.findPostByTitle("osejrfksef").getBody(), Matchers.is("Body1"));
    }

    @Test
    public void getAllPostsTest(){
        List<Post> allPosts = service.getAllPosts();
        assertNotNull(allPosts);
        assertThat(allPosts.size(), Matchers.is(3));
    }

    @Test
    public void getPostByIdTest(){
        Post post = service.getPostById(1);
        assertThat(post.getBody(), Matchers.is("Body1"));
    }

    @Test
    public void addPostTest(){
        Post post = new Post();
        post.setTitle("Post2");
        post.setBody("Body2");
        service.addPost(post);
        List<Post> list = service.getAllPosts();
        assertThat(list.size(), Matchers.is(4));
        assertTrue(list.contains(post));
    }

    @Test
    public void deletePostTest() throws Exception{
        Post newPost= new Post();
        newPost.setTitle("post4");
        newPost.setBody("body4");
        service.addPost(newPost);
        Post postByTitle = repository.findPostByTitle("post4");
                service.deletePost(postByTitle.getId());
        assertFalse(service.getAllPosts().contains(postByTitle));
        assertThat(service.getAllPosts().size(), Matchers.is(3));
    }

}
