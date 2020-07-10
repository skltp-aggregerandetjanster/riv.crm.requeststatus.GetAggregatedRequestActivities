package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.crm.requeststatus.getrequestactivities.v1.rivtabp21.GetRequestActivitiesResponderInterface;
import riv.crm.requeststatus.getrequestactivities.v1.rivtabp21.GetRequestActivitiesResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedrequestactivities.v1")
public class GARAAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "/schemas/TD_REQUESTSTATUS_1_0_1_R/interactions/GetRequestActivitiesInteraction/GetRequestActivitiesInteraction_1.0_RIVTABP21.wsdl";

  public GARAAgpServiceConfiguration() {

    setServiceName("GetAggregatedRequestActivities-v1");
    setTargetNamespace("urn:riv:crm:requeststatus:GetRequestActivities:1:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9006/GetAggregatedRequestActivities/service/v1");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetRequestActivitiesResponderInterface.class.getName());
    setInboundPortName(GetRequestActivitiesResponderService.GetRequestActivitiesResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetRequestActivitiesResponderInterface.class.getName());
    setOutboundPortName(GetRequestActivitiesResponderService.GetRequestActivitiesResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:crm:requeststatus");
    setEiCategorization("1");

    // TAK
    setTakContract("urn:riv:crm:requeststatus:GetRequestActivitiesResponder:1");

    // Set service factory
    setServiceFactoryClass(GARAAgpServiceFactoryImpl.class.getName());
    }


}
