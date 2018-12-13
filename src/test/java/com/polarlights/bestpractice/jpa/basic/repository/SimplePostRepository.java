package com.polarlights.bestpractice.jpa.basic.repository;

import com.polarlights.bestpractice.jpa.basic.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimplePostRepository extends JpaRepository<Post, Long> {

}
