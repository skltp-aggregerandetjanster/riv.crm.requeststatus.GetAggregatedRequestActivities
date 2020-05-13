package se.skltp.aggregatingservices.riv.crm.requeststatus;

import riv.crm.requeststatus._2.CVType;
import riv.crm.requeststatus._2.DatePeriodType;
import riv.crm.requeststatus._2.IIType;

public class RequestUtil {

	public static DatePeriodType createDatePeriod(String from, String to) {
		DatePeriodType dp = new DatePeriodType();
		dp.setStart(from);
		dp.setEnd(to);
		return dp;
	}
	
	public static CVType createCV(String code) {
		CVType cv = new CVType();
		cv.setCode(code);		
		return cv;
	}	
	
	public static CVType createCV(String oid, String code) {
		CVType cv = new CVType();
		cv.setCode(code);	
		cv.setCodeSystem(oid);
		return cv;
	}	
	
	public static IIType createII(String root, String extension) {
		IIType cv = new IIType();
		cv.setRoot(root);	
		cv.setExtension(extension);
		return cv;
	}
}
