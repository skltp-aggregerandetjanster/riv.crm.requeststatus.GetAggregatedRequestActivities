package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARACreateFindContentTest extends CreateFindContentTest {

  private static GARAAgpServiceConfiguration configuration = new GARAAgpServiceConfiguration();
  private static AgpServiceFactory<GetRequestActivitiesResponseType> agpServiceFactory = new GARAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GARACreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }
}
