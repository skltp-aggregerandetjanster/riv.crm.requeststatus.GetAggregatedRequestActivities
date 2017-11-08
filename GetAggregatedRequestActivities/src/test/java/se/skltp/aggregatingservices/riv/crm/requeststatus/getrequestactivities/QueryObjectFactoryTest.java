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
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import riv.crm.requeststatus.getrequestactivitiesresponder.v1.ObjectFactory;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.test.producer.TestProducerDb;



public class QueryObjectFactoryTest {

	private static final ObjectFactory OF = new ObjectFactory();
	private QueryObjectFactoryImpl testObject = new QueryObjectFactoryImpl();

	@Before
	public void beforeTest(){
		testObject.setEiServiceDomain("riv:crm:requeststatus");
	}

	@Test
	public void createQueryObjectAllRequestParametersSet() throws Exception{

		//Create request
		GetRequestActivitiesType request = new GetRequestActivitiesType();
		request.setFromDate("ÅÅÅÅMMDDttmmss");
		request.setSubjectOfCareId(TestProducerDb.TEST_RR_ID_ONE_HIT);
		request.setToDate("ÅÅÅÅMMDDttmmss");
		request.getCareUnitId().add("CareUnit1");
		request.getCareUnitId().add("CareUnit2");
		request.getTypeOfRequest().add("TypeOfRequest1");
		request.getTypeOfRequest().add("TypeOfRequest2");

		//Create FindContent request
		QueryObject queryObject = testObject.createQueryObject(createNode(request));
		FindContentType findContent = queryObject.getFindContent();

		//Expected to be set with values
		assertEquals(TestProducerDb.TEST_RR_ID_ONE_HIT, findContent.getRegisteredResidentIdentification());
		assertEquals("riv:crm:requeststatus", findContent.getServiceDomain());

		//Expected to be null
		assertNull(findContent.getCategorization());
		assertNull(findContent.getSourceSystem());
		assertNull(findContent.getLogicalAddress());
		assertNull(findContent.getBusinessObjectInstanceIdentifier());
		assertNull(findContent.getClinicalProcessInterestId());
		assertNull(findContent.getDataController());
		assertNull(findContent.getMostRecentContent());
		assertNull(findContent.getOwner());
	}

	private Node createNode(GetRequestActivitiesType request) throws Exception{

		// Since the class GetDiagnosisType don't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
		JAXBElement<GetRequestActivitiesType> requestJaxb = OF.createGetRequestActivities(request);

		//Node to carry marshalled content
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document node = db.newDocument();

        //Marshall
		JAXBContext jc = JAXBContext.newInstance(GetRequestActivitiesType.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(requestJaxb, node);
		return node;
	}
}
