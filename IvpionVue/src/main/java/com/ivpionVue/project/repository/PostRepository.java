package com.ivpionVue.project.repository;


import com.ivpionVue.project.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query
    Post findPostByTitle(String title);

}
