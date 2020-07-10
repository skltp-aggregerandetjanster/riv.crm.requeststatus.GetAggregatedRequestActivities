package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

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
}

