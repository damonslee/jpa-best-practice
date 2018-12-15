package com.polarlights.bestpractice.jpa.jpa.time;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;

import com.polarlights.bestpractice.jpa.jpa.time.domain.Post;
import com.polarlights.bestpractice.jpa.jpa.time.repository.PostRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@ActiveProfiles("utc")
class UTCTimestampTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void testTimeSavedInLocalFormat() {
        Post post = new Post();
        Instant now = Instant.now();
        post.setCreatedAt(now);
        postRepository.save(post);

        String datetimePattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dtfUTC8 = DateTimeFormatter.ofPattern(datetimePattern)
            .withZone(ZoneId.systemDefault());

        DateTimeFormatter dtfUTC = DateTimeFormatter.ofPattern(datetimePattern)
            .withZone(ZoneOffset.UTC);

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                    "SELECT FORMATDATETIME(created_at,'" + datetimePattern + "') " +
                        "FROM time_post"
                )) {
                    while (resultSet.next()) {
                        assertEquals(dtfUTC.format(now), resultSet.getString(1));
                        assertNotEquals(dtfUTC8.format(now), resultSet.getString(1));
                    }
                }
            }
        });
    }

    @Test
    void testLocalDateTimeSavedInLocalFormat() {
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();
        post.setLocalDateTime(now);
        postRepository.save(post);

        String datetimePattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dtfUTC8 = DateTimeFormatter.ofPattern(datetimePattern)
            .withZone(ZoneId.systemDefault());

        DateTimeFormatter dtfUTC = DateTimeFormatter.ofPattern(datetimePattern)
            .withZone(ZoneOffset.UTC);

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(
                    "SELECT FORMATDATETIME(local_date_time,'" + datetimePattern + "') " +
                        "FROM time_post"
                )) {
                    while (resultSet.next()) {
                        Instant instant = ZonedDateTime.of(now, ZoneId.of("Asia/Shanghai"))
                            .toInstant();
                        assertNotEquals(dtfUTC8.format(instant), resultSet.getString(1));
                        assertEquals(dtfUTC.format(instant), resultSet.getString(1));
                    }
                }
            }
        });

        post = postRepository.findAll().get(0);
        assertEquals(post.getLocalDateTime(), post.getLocalDateTime());
    }
}