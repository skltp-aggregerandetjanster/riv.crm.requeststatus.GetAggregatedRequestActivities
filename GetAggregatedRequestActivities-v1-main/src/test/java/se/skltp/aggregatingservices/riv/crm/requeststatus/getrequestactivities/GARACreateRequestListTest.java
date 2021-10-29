package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.apache.cxf.message.MessageContentsList;
import org.junit.jupiter.api.Test;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.tests.CreateRequestListTest;
import se.skltp.aggregatingservices.tests.TestDataUtil;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GARACreateRequestListTest extends CreateRequestListTest {

  private static final String RONTGEN = "1";
  private static final String LABB = "2";
  private static final String ALLMAN = "4";
  private static final String FYSIOLOG = "10";

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
    assertEquals(3L, (long)requestList.size());
  }

  @Test
  public void testCreateRequestListAllFilteredBySourceSystem() {
    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", this.testDataGenerator.createRequest("121212121212", "HSA-ID-2"));
    FindContentResponseType eiResponse = this.eiResponseDataHelper.getResponseForPatient("121212121212");
    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(3L, (long)requestList.size());
  }

  @Test
  public void testCreateRequestListFilteredByCategory_2CategoryMatch() {
    GetRequestActivitiesType request = (GetRequestActivitiesType) this.testDataGenerator
        .createRequest("198611062384", "");
    request.getTypeOfRequest().add(RONTGEN);
    request.getTypeOfRequest().add(LABB);

    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", request);

    FindContentResponseType eiResponse = this.eiResponseDataHelper
        .getResponseForPatient("198611062384");
    eiResponse.getEngagement().get(0).setCategorization(RONTGEN);
    eiResponse.getEngagement().get(1).setCategorization(LABB);
    eiResponse.getEngagement().get(2).setCategorization(ALLMAN);

    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(2L, (long) requestList.size());
  }

  @Test
  public void testCreateRequestListFilteredByCategory_CategoryDoesNotMatch() {
    GetRequestActivitiesType request = (GetRequestActivitiesType) this.testDataGenerator
        .createRequest("198611062384", "");
    request.getTypeOfRequest().add(FYSIOLOG);

    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", request);

    FindContentResponseType eiResponse = this.eiResponseDataHelper
        .getResponseForPatient("198611062384");
    eiResponse.getEngagement().get(0).setCategorization(RONTGEN);
    eiResponse.getEngagement().get(1).setCategorization(LABB);
    eiResponse.getEngagement().get(2).setCategorization(ALLMAN);

    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(0L, (long) requestList.size());
  }

  @Test
  public void testCreateRequestListFilteredByCategory_EmptyCategoryInRequest() {
    GetRequestActivitiesType request = (GetRequestActivitiesType) this.testDataGenerator
        .createRequest("198611062384", "");

    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", request);

    FindContentResponseType eiResponse = this.eiResponseDataHelper
        .getResponseForPatient("198611062384");
    eiResponse.getEngagement().get(0).setCategorization(RONTGEN);
    eiResponse.getEngagement().get(1).setCategorization(LABB);
    eiResponse.getEngagement().get(2).setCategorization(ALLMAN);

    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(3L, (long) requestList.size());
  }

  @Test
  public void testCreateRequestListFilteredByCategory_EmptyCategoryInFindContentResponse() {
    GetRequestActivitiesType request = (GetRequestActivitiesType) this.testDataGenerator
        .createRequest("198611062384", "");
    request.getTypeOfRequest().add(FYSIOLOG);

    MessageContentsList messageContentsList = TestDataUtil.createRequest("logiskAdress", request);

    FindContentResponseType eiResponse = this.eiResponseDataHelper
        .getResponseForPatient("198611062384");
    eiResponse.getEngagement().clear();


    List<MessageContentsList> requestList = this.agpServiceFactory.createRequestList(messageContentsList, eiResponse);
    assertEquals(0L, (long) requestList.size());
  }

}