/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesResponseType;
import riv.crm.requeststatus.v1.RequestActivityType;
import se.skltp.agp.test.producer.TestProducerDb;

public class RequestActivitiesTestProducerDb extends TestProducerDb {

	private static final Logger log = LoggerFactory.getLogger(RequestActivitiesTestProducerDb.class);

	@Override
	public Object createResponse(Object... responseItems) {
		log.debug("Creates a response with {} items", responseItems);
		GetRequestActivitiesResponseType response = new GetRequestActivitiesResponseType();
		for (int i = 0; i < responseItems.length; i++) {
			response.getRequestActivity().add((RequestActivityType)responseItems[i]);
		}
		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

		if (log.isDebugEnabled()) {
			log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[] {logicalAddress, registeredResidentId, businessObjectId});
		}

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
}
