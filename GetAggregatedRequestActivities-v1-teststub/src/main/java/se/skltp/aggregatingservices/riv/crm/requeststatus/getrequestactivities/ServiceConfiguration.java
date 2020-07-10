package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.crm.requeststatus.getrequestactivities.v1.rivtabp21.GetRequestActivitiesResponderInterface;
import riv.crm.requeststatus.getrequestactivities.v1.rivtabp21.GetRequestActivitiesResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedrequestactivities.v1.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  public static final String SCHEMA_PATH = "/schemas/TD_REQUESTSTATUS_1_0_1_R/interactions/GetRequestActivitiesInteraction/GetRequestActivitiesInteraction_1.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetRequestActivitiesResponderInterface.class.getName());
    setServiceNamespace("urn:riv:crm:requeststatus:GetRequestActivitiesResponder:1");
    setPortName(GetRequestActivitiesResponderService.GetRequestActivitiesResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
	setServiceTimeout(27000);
  }

}
