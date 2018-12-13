package com.polarlights.bestpractice.configration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
@ComponentScan("com.polarlights.bestpractice.jpa")
@EntityScan("com.polarlights.bestpractice.jpa")
public class ApplicationConfigration {

}
