package se.skltp.aggregatingservices.riv.crm.requestactivities.getaggregatedrequestactivities

trait CommonParameters {
  val serviceName:String     = "RequestActivities"
  val urn:String             = "urn:riv:crm:requeststatus:GetRequestActivitiesResponder:1"
  val responseElement:String = "GetRequestActivitiesResponse"
  val responseItem:String    = "requestActivity"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedRequestActivities/service/v1"
                               }
}
