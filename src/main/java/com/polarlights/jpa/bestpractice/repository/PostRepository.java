package com.polarlights.jpa.bestpractice.repository;

import com.polarlights.jpa.bestpractice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
