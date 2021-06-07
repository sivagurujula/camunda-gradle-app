package com.siva.app.test;

import com.siva.app.CamundaConfiguration;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE//,
        //classes = CamundaConfiguration.class,
        /*properties = { //
        "camunda.bpm.job-execution.enabled=false", //
        "camunda.bpm.auto-deployment-enabled=false", //
        "restProxyHost=api.example.org", //
        "restProxyPort=80" }*/
        )

@ContextConfiguration(classes = CamundaConfiguration.class)
@Deployment(resources = "process.bpmn")
public class ProcessUnitTest {

  private static final String PROCESS_DEFINITION_KEY = "camunda-test-spring";
  public static final String PROCESS_KEY = "camunda-test-spring";
  public static final String TASK_DELIVER_ORDER = "Task_DeliverOrder";
  public static final String VAR_ORDER_DELIVERED = "orderDelivered";
  public static final String END_EVENT_DELIVERY_COMPLETED = "EndEvent";
  public static final String END_EVENT_DELIVERY_CANCELLED = "EndEvent_DeliveryCancelled";
  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }
  /*
  @Autowired
  //private ProcessEngineConfiguration  processEngineConfiguration ;
  private ProcessEngineConfiguration  processEngineConfiguration = new  ProcessCoverageInMemProcessEngineConfiguration();
  @Rule
  public final ProcessEngineRule rule = new ProcessEngineRule(processEngineConfiguration.buildProcessEngine());

  @Rule
  @ClassRule
  public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
          .assertClassCoverageAtLeast(0.9)
          .build();
*/
  /*@Rule
  //public final ProcessEngineRule processEngine = new ProcessCoverageInMemProcessEngineConfiguration().rule();
  public final ProcessEngineRule processEngine = new StandaloneInMemoryTestConfiguration().rule();*/
  @Mock
  private ProcessScenario testDeliveryProcess;

  @Autowired
  private ProcessEngine processEngine;

  @Rule
  @ClassRule
  public static ProcessEngineRule rule;

  @PostConstruct
  void initRule() {
    //rule = new ProcessEngineRule(processEngine);
    rule = TestCoverageProcessEngineRuleBuilder.create(processEngine)
            .assertClassCoverageAtLeast(0.3)
            .build();
  }
  @Before
  public void defaultScenario() {
    //MockitoAnnotations.initMocks(this);

    //Happy-Path
    when(testDeliveryProcess.waitsAtUserTask(TASK_DELIVER_ORDER))
            .thenReturn(task -> {
              task.complete(withVariables(VAR_ORDER_DELIVERED, true));
            });
  }
  @Test
  public void shouldExecuteHappyPath() {
    Scenario.run(testDeliveryProcess)
            .startByKey(PROCESS_KEY)
            .execute();

    verify(testDeliveryProcess)
            .hasFinished(END_EVENT_DELIVERY_COMPLETED);
  }
  @Test
  public void testHappyPath() {
    // Either: Drive the process by API and assert correct behavior by camunda-bpm-assert, e.g.:
    //ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
    
    // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
  }

}
