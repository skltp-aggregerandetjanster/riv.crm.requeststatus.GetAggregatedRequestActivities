package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import riv.crm.requeststatus.v1.RequestActivityType;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetRequestActivitiesType request = (GetRequestActivitiesType) messageContentsList.get(1);
		return request.getSubjectOfCareId();
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetRequestActivitiesResponseType response = new GetRequestActivitiesResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getRequestActivity().add((RequestActivityType)responseItems[i]);
		}
		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

		RequestActivityType response = new RequestActivityType();
		response.setCareUnit(logicalAddress);
		response.setSubjectOfCareId(registeredResidentId);
		response.setSenderRequestId(businessObjectId);
		response.setReceiverRequestId("ReceiverRequestId");
		response.setTypeOfRequest("TypeOfRequest");
		response.setRequestMedium("RequestMedium");
		response.setRequestIssuedByPersonName("RequestIssuedByPersonName");
		response.setRequestIssuedByOrganizationalUnitId("RequestIssuedByOrganizationalUnitId");
		response.setRequestIssuedByOrganizationalUnitDescription("RequestIssuedByOrganizationalUnitDescription");
		response.setReceivingPersonName("ReceivingPersonName");
		response.setReceivingOrganizationalUnitId("ReceivingOrganizationalUnitId");
		response.setReceivingOrganizationalUnitDescription("ReceivingOrganizationalUnitDescription");

		return response;
	}

	public Object createRequest(String patientId, String sourceSystemHSAId){
		GetRequestActivitiesType request = new GetRequestActivitiesType();
		request.setFromDate("ÅÅÅÅMMDDttmmss");
		request.setSubjectOfCareId(patientId);
		request.setToDate("ÅÅÅÅMMDDttmmss");
		request.getCareUnitId().add("CareUnit1");
		request.getCareUnitId().add("CareUnit2");
		request.getTypeOfRequest().add("TypeOfRequest1");
		request.getTypeOfRequest().add("TypeOfRequest2");
		return request;
	}
}
