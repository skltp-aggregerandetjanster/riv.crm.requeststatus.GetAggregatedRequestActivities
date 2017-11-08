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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.crm.requeststatus.getrequestactivitiesresponder.v1.GetRequestActivitiesType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

	private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);

	/**
	 * Filtrera svarsposter från i EI (ei-engagement) baserat parametrar i GetRequestActivities requestet (req).
	 * Följande villkor måste vara sanna för att en svarspost från EI skall tas med i svaret:
	 *
	 * 1. req.fromDate <= ei-engagement.mostRecentContent <= req.toDate
	 * 2. reg.getTypeOfRequest == 0 or req.getTypeOfRequest.contains(ei-engagement.categorization)
	 *
	 * Svarsposter från EI som passerat filtreringen grupperas på fältet sourceSystem samt postens fält logicalAddress (= PDL-enhet) samlas i listan careUnitId per varje sourceSystem
	 *
	 * Ett anrop görs per funnet sourceSystem med följande värden i anropet:
	 *
	 * 1. logicalAddress = sourceSystem (systemadressering)
	 * 2. subjectOfCareId = orginal-request.subjectOfCareId
	 * 3. careUnitId = orginal-request.careUnitId
	 * 4. fromDate = orginal-request.fromDate
	 * 5. toDate = orginal-request.toDate
	 */
	@Override
	public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType src) {

		GetRequestActivitiesType originalRequest = (GetRequestActivitiesType)qo.getExtraArg();
		List<String> reqCategories = originalRequest.getTypeOfRequest();

		FindContentResponseType eiResp = (FindContentResponseType)src;
		List<EngagementType> inEngagements = eiResp.getEngagement();

		log.info("Got {} hits in the engagement index", inEngagements.size());

		Map<String, List<String>> sourceSystem_pdlUnitList_map = new HashMap<String, List<String>>();

		for (EngagementType inEng : inEngagements) {

			// Filter
			if (isCorrectCategory(reqCategories, inEng.getCategorization())) {

				// Add pdlUnit to source system
				log.debug("Add SS: {} for PDL unit: {}", inEng.getSourceSystem(), inEng.getLogicalAddress());
				addPdlUnitToSourceSystem(sourceSystem_pdlUnitList_map, inEng.getSourceSystem(), inEng.getLogicalAddress());
			}
		}

		// Prepare the result of the transformation as a list of request-payloads,
		// one payload for each unique logical-address (e.g. source system since we are using systemaddressing),
		// each payload built up as an object-array according to the JAX-WS signature for the method in the service interface
		List<Object[]> reqList = new ArrayList<Object[]>();

		for (Entry<String, List<String>> entry : sourceSystem_pdlUnitList_map.entrySet()) {

			String sourceSystem = entry.getKey();

			if (log.isInfoEnabled()) log.info("Calling source system using logical address {} for subject of care id {}", sourceSystem, originalRequest.getSubjectOfCareId());

			GetRequestActivitiesType request = new GetRequestActivitiesType();
			request.setSubjectOfCareId(originalRequest.getSubjectOfCareId());
			request.getCareUnitId().addAll(originalRequest.getCareUnitId());
			request.setFromDate(originalRequest.getFromDate());
			request.setToDate(originalRequest.getToDate());

			//For each careUnit found in EI, add all requestTypes from original request
			request.getTypeOfRequest().addAll(originalRequest.getTypeOfRequest());

			Object[] reqArr = new Object[] {sourceSystem, request};

			reqList.add(reqArr);
		}

		log.debug("Transformed payload: {}", reqList);

		return reqList;
	}

	boolean isCorrectCategory(List<String> reqTypeOfRequestList,
			String categorization) {

		log.debug("Check if list of requested categories {} contains EI categorization {} ", reqTypeOfRequestList, categorization);

		if(reqTypeOfRequestList == null || reqTypeOfRequestList.isEmpty()) return true;

		return reqTypeOfRequestList.contains(categorization);
	}

	void addPdlUnitToSourceSystem(Map<String, List<String>> sourceSystem_pdlUnitList_map, String sourceSystem, String pdlUnitId) {
		List<String> careUnitList = sourceSystem_pdlUnitList_map.get(sourceSystem);
		if (careUnitList == null) {
			careUnitList = new ArrayList<String>();
			sourceSystem_pdlUnitList_map.put(sourceSystem, careUnitList);
		}
		careUnitList.add(pdlUnitId);
	}
}
