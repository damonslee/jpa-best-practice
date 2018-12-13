package com.polarlights.bestpractice.configration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
@ComponentScan("com.polarlights.jpa.bestpractice")
@EntityScan("com.polarlights.jpa.bestpractice")
public class ApplicationConfigration {

}
