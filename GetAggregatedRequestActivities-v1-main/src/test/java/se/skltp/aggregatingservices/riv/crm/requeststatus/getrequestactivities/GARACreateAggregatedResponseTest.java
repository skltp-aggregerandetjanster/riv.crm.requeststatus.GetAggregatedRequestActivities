package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;


import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARACreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GARAAgpServiceConfiguration configuration = new GARAAgpServiceConfiguration();
  private static AgpServiceFactory<GetRequestActivitiesResponseType> agpServiceFactory = new GARAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GARACreateAggregatedResponseTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
    GetRequestActivitiesResponseType responseType = (GetRequestActivitiesResponseType) response;
    return responseType.getRequestActivity().size();
  }
}