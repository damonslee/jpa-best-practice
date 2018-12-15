package com.polarlights.bestpractice.jpa.basic.repository;

import com.polarlights.bestpractice.jpa.basic.domain.BasicPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimplePostRepository extends JpaRepository<BasicPost, Long> {

}
