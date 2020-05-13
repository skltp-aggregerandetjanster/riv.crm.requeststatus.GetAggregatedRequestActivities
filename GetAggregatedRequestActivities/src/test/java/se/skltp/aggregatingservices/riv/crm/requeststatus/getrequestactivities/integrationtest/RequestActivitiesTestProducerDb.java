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

import riv.crm.requeststatus.getrequestactivitiesresponder.v2.GetRequestActivitiesResponseType;
import riv.crm.requeststatus._2.AccessControlHeaderType;
import riv.crm.requeststatus._2.AuthorType;
import riv.crm.requeststatus._2.HeaderType;
import riv.crm.requeststatus._2.RequestActivityBodyType;
import riv.crm.requeststatus._2.RequestActivityType;
import se.skltp.agp.test.producer.TestProducerDb;
import static se.skltp.aggregatingservices.riv.crm.requeststatus.RequestUtil.*;

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

		RequestActivityBodyType body = new RequestActivityBodyType();
		response.setBody(body);
		
		HeaderType header = new HeaderType();
		response.setHeader(header);
		
		AccessControlHeaderType ach = new AccessControlHeaderType();		
		header.setAccessControlHeader(ach);
		AuthorType author = new AuthorType();
		header.setAuthor(author );
		header.setSourceSystemId(createII("", logicalAddress));
		
		author.setName("The Author");
		author.setTimestamp("20200101151300");
		
		ach.setOriginalPatientId(createII("", registeredResidentId));
		ach.setAccountableCareGiver(createII("root","AccountableCareGiver"));
		ach.setAccountableCareUnit(createII("root","AccountableCareUnit"));
		ach.setApprovedForPatient(true);
		ach.setCareProcessId(businessObjectId);
		
		body.setStatusCode(createCV("1.2.752.129.2.2.2.43", "7"));
		body.setEventTime("20200115153000");

		return response;
	}
}
