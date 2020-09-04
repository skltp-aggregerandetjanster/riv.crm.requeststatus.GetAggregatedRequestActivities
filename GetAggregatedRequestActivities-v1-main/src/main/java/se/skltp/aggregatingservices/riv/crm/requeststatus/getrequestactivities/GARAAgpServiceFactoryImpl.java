package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.aggregatingservices.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.aggregatingservices.utility.RequestListUtil;

@Log4j2
public class GARAAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetRequestActivitiesType, GetRequestActivitiesResponseType> {

  @Override
  public String getPatientId(GetRequestActivitiesType queryObject) {
    return queryObject.getSubjectOfCareId();
  }

  @Override
  public String getSourceSystemHsaId(GetRequestActivitiesType queryObject) {
    return null;
  }

  @Override
  public GetRequestActivitiesResponseType aggregateResponse(
      List<GetRequestActivitiesResponseType> aggregatedResponseList) {
    GetRequestActivitiesResponseType aggregatedResponse = new GetRequestActivitiesResponseType();
    for (GetRequestActivitiesResponseType response : aggregatedResponseList) {
      aggregatedResponse.getRequestActivity().addAll(response.getRequestActivity());
    }

    return aggregatedResponse;
  }

  public List<MessageContentsList> createRequestList(MessageContentsList messageContentsList, FindContentResponseType eiResp) {
    int index = this.agpServiceConfiguration.getMessageContentListQueryIndex();
    GetRequestActivitiesType queryObject = (GetRequestActivitiesType)messageContentsList.get(index);
    List<String> reqCategories = queryObject.getTypeOfRequest();

    filterOnCategory(eiResp, reqCategories);

    String filterOnCareUnit = this.getSourceSystemHsaId(queryObject);
    log.info("Got {} hits in the engagement index, filtering on {}...", eiResp.getEngagement().size(), filterOnCareUnit);
    List<MessageContentsList> reqList = RequestListUtil
        .createRequestMessageContentsLists(eiResp, messageContentsList, filterOnCareUnit);
    log.info("Calling {} source systems", reqList.size());
    return reqList;
  }

  private void filterOnCategory(FindContentResponseType eiResp, List<String> reqCategories) {
    List <EngagementType> noCorrectEng = new ArrayList<EngagementType>();
    for (EngagementType resp : eiResp.getEngagement()) {

      if (!isCorrectCategory(reqCategories, resp.getCategorization())) {
        log.debug("Delete eiResp with category {}", resp.getCategorization());
        noCorrectEng.add(resp);
      }
    }
    eiResp.getEngagement().removeAll(noCorrectEng);
  }

  boolean isCorrectCategory(List<String> reqCategories, String categorization){
    log.debug("Check if list of requested categories {} contains EI categorization {} ", reqCategories, categorization);
    if(reqCategories == null || reqCategories.isEmpty()) return true;
    return reqCategories.contains(categorization);
  }
}

