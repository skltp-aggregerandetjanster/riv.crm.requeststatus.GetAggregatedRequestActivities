package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import java.util.List;
import org.apache.cxf.message.MessageContentsList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.tests.CreateRequestListTest;
import se.skltp.aggregatingservices.tests.TestDataUtil;


@RunWith(SpringJUnit4ClassRunner.class)
public class GARACreateRequestListTest extends CreateRequestListTest {
  private static GARAAgpServiceConfiguration configuration = new GARAAgpServiceConfiguration();
  private static AgpServiceFactory<GetRequestActivitiesResponseType> agpServiceFactory = new GARAAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GARACreateRequestListTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Test
  public void testCreateRequestListSomeFilteredBySourceSystem() {
    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", this.testDataGenerator.createRequest("198611062384", "HSA-ID-2"));
    FindContentResponseType eiResponse = this.eiResponseDataHelper.getResponseForPatient("198611062384");
    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    Assert.assertEquals(3L, (long)requestList.size());
  }

  @Test
  public void testCreateRequestListAllFilteredBySourceSystem() {
    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", this.testDataGenerator.createRequest("121212121212", "HSA-ID-2"));
    FindContentResponseType eiResponse = this.eiResponseDataHelper.getResponseForPatient("121212121212");
    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    Assert.assertEquals(3L, (long)requestList.size());
  }

}