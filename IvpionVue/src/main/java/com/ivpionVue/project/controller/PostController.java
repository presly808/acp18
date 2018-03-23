package com.ivpionVue.project.controller;

import com.ivpionVue.project.model.Post;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ivpionVue.project.service.PostService;


@CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
@RestController
public class PostController {

    private PostService service;

    @Autowired
    public PostController(@Qualifier("postServiceImpl") PostService service) {
        this.service = service;
    }


    @CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
    @RequestMapping(path = "/post/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPosts(){
        return new ResponseEntity<>(service.getAllPosts(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
    @RequestMapping(path = "/post/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPosts(@PathVariable String id){
        int ID = Integer.parseInt(id);
        return new ResponseEntity<>(service.getPostById(ID), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @RequestMapping(path = "/post/add", method = RequestMethod.POST)
    public ResponseEntity<Post> addPost(@RequestBody Post post){
        return new ResponseEntity<>(service.addPost(post), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.DELETE})
    @RequestMapping(path = "/post/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable String id){
        try {
            return new ResponseEntity<>(service.deletePost(Integer.parseInt(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:8089", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @RequestMapping(path = "/post/update", method = RequestMethod.POST)
    public ResponseEntity<Post> updatePost(@RequestBody Post post){
        return new ResponseEntity<>(service.updatePost(post), HttpStatus.OK);
    }
}
