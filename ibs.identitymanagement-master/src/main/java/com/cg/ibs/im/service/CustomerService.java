package com.cg.ibs.im.service;


import java.time.LocalDate;

import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface CustomerService {
	
	boolean verifyApplicantId(long applicantId) throws IBSCustomException;
	
	ApplicantStatus checkStatus(long applicantId) throws IBSCustomException;
	
	boolean login(String username, String password) throws IBSCustomException;
	
	boolean verifyName(String name);

	boolean verifyDob(LocalDate ld);

	boolean verifyMobileNumber(String mobileNumber);

	boolean verifyAadharNumber(String aadharNumber);

	boolean verifyPincode(String pinCode);
	
	boolean verifyCurrentAddress(AddressBean currentAddress);

	boolean verifyPanNumber(String panNumber);

	boolean verifyEmailId(String emailId);

	boolean verifyMobileNumbers(String mobile1, String mobile2);

	boolean checkCustomerDetails(String confirm1, String confirm2);

	boolean updateUserId(CustomerBean customer, String userId) throws IBSCustomException;

	boolean updatePassword(CustomerBean customer, String password) throws IBSCustomException;

	boolean saveApplicantDetails(ApplicantBean applicant) throws IBSCustomException;

	boolean storeCustomerDetails(CustomerBean customerBean) throws IBSCustomException;

	CustomerBean getCustomerDetails(String uci) throws IBSCustomException;

	boolean firstLogin(String userUci) throws IBSCustomException;

	ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException;

	boolean storeApplicantDetails(ApplicantBean applicant);

	CustomerBean getCustomerByApplicantId(long applicantId);

	boolean isCustomerValid(String uci) throws IBSCustomException;

	boolean upload(String srcPath);


}
