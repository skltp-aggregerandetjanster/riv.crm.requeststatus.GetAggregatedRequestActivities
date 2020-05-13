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
package se.skltp.aggregatingservices.riv.crm.requeststatus.getrequestactivities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static se.skltp.agp.test.producer.TestProducerDb.TEST_LOGICAL_ADDRESS_1;
import static se.skltp.agp.test.producer.TestProducerDb.TEST_LOGICAL_ADDRESS_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.crm.requeststatus.getrequestactivitiesresponder.v2.GetRequestActivitiesType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;
import static se.skltp.aggregatingservices.riv.crm.requeststatus.RequestUtil.*;

public class RequestListFactoryTest {

	private static final String RONTGEN = "1";
	private static final String LABB = "2";
	private static final String ALLMAN = "4";
	
	private static final String CATEGORIZATION = "req-act";

	private static final ThreadSafeSimpleDateFormat df = new ThreadSafeSimpleDateFormat("YYYYMMDDhhmmss");

	private RequestListFactory testObject = new RequestListFactoryImpl();

	@Test
	@Ignore
	public void testQueryObjectFactory() {

		QueryObject qo = null;
		FindContentResponseType src = null;
		testObject.createRequestList(qo, src);
		assertEquals("expected", "actual");
	}

	@Test
	public void createRequestList(){
		FindContentResponseType findContentResponse = new FindContentResponseType();
		findContentResponse.getEngagement().add(createEngagement(TEST_LOGICAL_ADDRESS_1, "121212121212"));
		findContentResponse.getEngagement().add(createEngagement(TEST_LOGICAL_ADDRESS_2, "121212121212"));

		GetRequestActivitiesType originalRequest = new GetRequestActivitiesType();
		originalRequest.setPatientId(createII("","121212121212"));
		originalRequest.getCareUnitHSAId().add(createII("","CAREUNITID_1"));
		originalRequest.getTypeOfRequest().add(createCV("1.2.752.129.2.2.2.24", LABB));
		originalRequest.setDatePeriod(createDatePeriod("20200101", "20200102"));
		QueryObject qo = new QueryObject(null, originalRequest);

		List<Object[]> requestList = new RequestListFactoryImpl().createRequestList(qo, findContentResponse);

		String sourceSystem1 = (String)requestList.get(0)[0];
		String sourceSystem2 = (String)requestList.get(1)[0];
		assertEquals(TEST_LOGICAL_ADDRESS_2, sourceSystem1);
		assertEquals(TEST_LOGICAL_ADDRESS_1, sourceSystem2);

		GetRequestActivitiesType request1 = (GetRequestActivitiesType)requestList.get(0)[1];
		GetRequestActivitiesType request2 = (GetRequestActivitiesType)requestList.get(1)[1];

		assertOriginalRequestParamsArePropagated(originalRequest, request1);
		assertOriginalRequestParamsArePropagated(originalRequest, request2);
	}

	private void assertOriginalRequestParamsArePropagated(GetRequestActivitiesType originalRequest, GetRequestActivitiesType request) {
		assertEquals(originalRequest.getCareUnitHSAId().get(0).getExtension(), request.getCareUnitHSAId().get(0).getExtension());
		assertEquals(originalRequest.getDatePeriod().getStart(), request.getDatePeriod().getStart());
		assertEquals(originalRequest.getPatientId().getExtension(), request.getPatientId().getExtension());
		assertEquals(originalRequest.getDatePeriod().getEnd(), request.getDatePeriod().getEnd());
		assertEquals(originalRequest.getTypeOfRequest().get(0), request.getTypeOfRequest().get(0));
	}

	private EngagementType createEngagement(String sourceSystem, String rrid) {
		EngagementType engagement = new EngagementType();
		engagement.setLogicalAddress(sourceSystem);
		engagement.setSourceSystem(sourceSystem);
		engagement.setCategorization(CATEGORIZATION);
		engagement.setDataController("DATACONTROLLER");

		engagement.setMostRecentContent(df.format(new Date()));
		engagement.setUpdateTime(null);
		engagement.setCreationTime(null);

		engagement.setOwner("OWNER");
		engagement.setRegisteredResidentIdentification(rrid);
		engagement.setServiceDomain("riv:crm:requeststatus");


		engagement.setBusinessObjectInstanceIdentifier(null);
		engagement.setClinicalProcessInterestId(null);
		return engagement;
	}
}
