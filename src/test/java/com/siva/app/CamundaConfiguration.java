package com.siva.app;

import com.siva.app.delegates.LoggerDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.ProcessCoverageInMemProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@ComponentScan(basePackageClasses={LoggerDelegate.class})
//@TestConfiguration
    @Configuration
public class CamundaConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CamundaConfiguration.class);
   @Bean
    public ProcessEngineConfiguration processEngineConfiguration(){
        System.out.println("ProcessEngineConfiguration **********");
        log.info("ProcessEngineConfiguration created");
        return new ProcessCoverageInMemProcessEngineConfiguration();
    }
    @Bean
    public ProcessEngine processEngine(){
        return processEngineConfiguration().buildProcessEngine();
    }
}
