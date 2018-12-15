package com.polarlights.bestpractice.jpa.jpa.time.repository;

import com.polarlights.bestpractice.jpa.jpa.time.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
