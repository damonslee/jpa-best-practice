package com.polarlights.bestpractice.jpa.jpa.basic;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.polarlights.bestpractice.jpa.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Transactional
@EnableJpaRepositories(considerNestedRepositories = true)
public class LazyLoadAttributeTest {

    private static final String LONG_CONTENT = "content";

    @Autowired
    FullPostRepository fullPostRepository;
    @Autowired
    SimplePostRepository simplePostRepository;
    @Autowired
    EnhancedPostRepository enhancedPostRepository;

    @BeforeEach
    void setup() {
        FullPost post = new FullPost();
        post.setContent(LONG_CONTENT);
        fullPostRepository.save(post);
    }

    /**
     * lazy loading property by bytecode enhancement.
     *
     * <p>
     * 1. only select eager load properties first.
     * <pre>{@code
     * SELECT
     *     id, created_at, updated_at
     * FROM
     *     basic_post
     * WHERE
     *     id = 1;
     * }</pre>
     * <p>
     * 2. generate a SQL to fetch all the lazy loading properties.
     * <pre>{@code
     * SELECT
     *     content
     * FROM
     *     basic_post
     * WHERE
     *     id = 1;
     * }</pre>
     */
    @Test
    void testWithEnhancedLazyLoadAttribute() {
        EnhancedPost post = enhancedPostRepository.findById(1L).get();
        assertEquals(LONG_CONTENT, post.getContent());
    }

    public interface EnhancedPostRepository extends JpaRepository<EnhancedPost, Long> {

    }

    public interface FullPostRepository extends JpaRepository<FullPost, Long> {

    }

    public interface SimplePostRepository extends JpaRepository<BasicPost, Long> {

    }

    @Getter
    @Setter
    @MappedSuperclass
    static class BasePost extends BaseEntity {

        private static final long serialVersionUID = -1L;
    }

    @Getter
    @Setter
    @Entity
    @Table(name = "basic_post")
    static class BasicPost extends BasePost {

        private static final long serialVersionUID = -1L;
    }

    @Getter
    @Setter
    @Entity
    @Table(name = "basic_post")
    static class EnhancedPost extends BasePost {

        private static final long serialVersionUID = -1L;

        private String content;

        @Lob
        @Basic(fetch = FetchType.LAZY)
        public String getContent() {
            return content;
        }
    }

    @Getter
    @Setter
    @Entity
    @Table(name = "basic_post")
    static class FullPost extends BasePost {

        private static final long serialVersionUID = -1L;

        private String content;
    }

    @Nested
    class LazyLoadBySubClass {

        /**
         * only load basic attributes.
         * <pre>{@code
         * SELECT
         *     id, created_at, updated_at
         * FROM
         *     basic_post
         * WHERE
         *     id = 1;
         * }</pre>
         */
        @Test
        void testWithoutLazyLoadAttribute() {
            BasicPost basicPost = simplePostRepository.findAll().get(0);
            assertNotNull(basicPost);
        }

        /**
         * loading all properties by subclass.
         *
         * <pre>{@code
         * SELECT
         *     id, content, created_at, updated_at
         * FROM
         *     basic_post
         * WHERE
         *     id = 1;
         * }</pre>
         */
        @Test
        void testWithLazyLoadAttribute() {
            FullPost post = fullPostRepository.findAll().get(0);
            assertEquals(LONG_CONTENT, post.getContent());
        }
    }
}
