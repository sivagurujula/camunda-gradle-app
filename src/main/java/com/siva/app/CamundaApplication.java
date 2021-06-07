package com.siva.app;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
@EnableProcessApplication("camunda-test-spring")
public class CamundaApplication {
	private static final Logger LOGGER = Logger.getLogger(CamundaApplication.class.getName());
  public static void main(String... args) {
	  System.out.println("CamundaApplication main");
	  LOGGER.info("CamundaApplication main");
    SpringApplication.run(CamundaApplication.class, args);
  }
}
