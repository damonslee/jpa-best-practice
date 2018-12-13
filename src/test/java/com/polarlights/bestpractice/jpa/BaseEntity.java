package com.polarlights.bestpractice.jpa;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @CreatedDate
    public Instant getCreatedAt() {
        return createdAt;
    }

    @LastModifiedDate
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
