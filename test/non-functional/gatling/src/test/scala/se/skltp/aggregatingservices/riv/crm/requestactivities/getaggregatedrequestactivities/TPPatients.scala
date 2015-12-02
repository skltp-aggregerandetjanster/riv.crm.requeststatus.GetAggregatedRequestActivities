package se.skltp.aggregatingservices.riv.crm.requestactivities.getaggregatedrequestactivities

import se.skltp.agp.testnonfunctional.TPPatientsAbstract

/**
 * Test GetAggregatedRequestActivities using test cases defined in patients.csv (or patients-override.csv)
 */
class TPPatients extends TPPatientsAbstract with CommonParameters {
  setUp(setUpAbstract(serviceName, urn, responseElement, responseItem, baseUrl))
}
