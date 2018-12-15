package com.polarlights.bestpractice.jpa.basic.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.polarlights.bestpractice.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 懒加载某个属性，可以使用Lob + Basic(fetch=FetchType.LAZY)；或者使用子类。
 * 前者需要使用代码增强；
 * 后者对于使用 JPA Repository 相关的操作会额外增加一些类和代码；除非使用比较底层的entityManager 来操作；
 * 但是它对关联关系的支持并不好
 */
@Getter
@Setter
@Entity
@Table(name = "basic_post")
public class BasicPost extends BaseEntity {

    private static final long serialVersionUID = -1L;
}
