package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;


@RunWith(SpringJUnit4ClassRunner.class)
public class GARACreateFindContentTest extends CreateFindContentTest {

  private static GARAAgpServiceConfiguration configuration = new GARAAgpServiceConfiguration();
  private static AgpServiceFactory<GetRequestActivitiesResponseType> agpServiceFactory = new GARAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GARACreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }
}
