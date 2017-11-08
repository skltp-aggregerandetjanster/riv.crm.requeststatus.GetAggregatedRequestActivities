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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetRequestActivitiesType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	@SuppressWarnings("unused")
	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetRequestActivities request till EI FindContent request enligt:
	 *
	 * 1. subjectOfCareId --> registeredResidentIdentification
	 * 2. "riv:crm:requeststatus" --> serviceDomain
	 */
	@Override
	public QueryObject createQueryObject(Node node) {

		GetRequestActivitiesType req = (GetRequestActivitiesType)ju.unmarshal(node);

		if (log.isDebugEnabled()) log.debug("Transformed payload for pid: {}", req.getSubjectOfCareId());

		FindContentType fc = new FindContentType();
		fc.setRegisteredResidentIdentification(req.getSubjectOfCareId());
		fc.setServiceDomain(eiServiceDomain);

		//A GetRequestActivities request can contain 0..* typeOfRequests, in previous
		//version (1.0 RC3) it could only contain one. Therefore we need to request all
		//typeOfRequests from EI and filter the FindContent response in RequestListFactoryImpl.
		//fc.setCategorization(req.getTypeOfRequest());

		QueryObject qo = new QueryObject(fc, req);

		return qo;
	}
}
