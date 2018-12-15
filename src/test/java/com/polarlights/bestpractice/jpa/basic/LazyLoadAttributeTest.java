package com.polarlights.bestpractice.jpa.basic;

import com.polarlights.bestpractice.jpa.basic.domain.BasicPost;
import com.polarlights.bestpractice.jpa.basic.domain.FullPost;
import com.polarlights.bestpractice.jpa.basic.repository.FullPostRepository;
import com.polarlights.bestpractice.jpa.basic.repository.SimplePostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LazyLoadAttributeTest {

    private static final String LONG_CONTENT = "content";

    @Autowired
    FullPostRepository fullPostRepository;
    @Autowired
    SimplePostRepository simplePostRepository;

    @BeforeEach
    void inti() {
        FullPost post = new FullPost();
        post.setContent(LONG_CONTENT);
        fullPostRepository.save(post);
    }

    @Test
    void testWithoutLazyLoadAttribute() {
        BasicPost basicPost = simplePostRepository.findById(1L).get();
        assertEquals(Long.valueOf(1), basicPost.getId());
    }

    @Test
    void testWithLazyLoadAttribute() {
        FullPost post = fullPostRepository.findAll().get(0);
        assertEquals(LONG_CONTENT, post.getContent());
    }
}
