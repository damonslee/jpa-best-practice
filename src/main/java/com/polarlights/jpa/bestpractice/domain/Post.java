package com.polarlights.jpa.bestpractice.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    private static final long serialVersionUID = -1L;
}
