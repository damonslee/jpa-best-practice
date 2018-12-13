package com.polarlights.bestpractice.jpa.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.polarlights.bestpractice.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "basic_post")
public class FullPost extends BaseEntity {

    private static final long serialVersionUID = -1L;

    private String content;
}
