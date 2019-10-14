package com.cg.ibs.im.service;

import java.util.List;
import java.util.Set;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;

public interface BankerService {
	boolean verifyLogin(String user, String password);
	
	Set<Long> viewPendingApplications();
	
	Set<Long> viewApprovedApplications();
	
	Set<Long> viewDeniedApplications();
	
	boolean updateStatus(long applicantId, ApplicantStatus applicantStatus) throws IBSCustomException;
	
	String generatePassword(long applicantId);
	
	CustomerBean createNewCustomer(ApplicantBean applicant) throws IBSCustomException;

	boolean isApplicantPresentInPendingList(long applicantId);

	boolean isApplicantPresent(long applicantId);

	ApplicantBean displayDetails(long applicantId) throws IBSCustomException;

	String generateUsername(long applicantId) throws IBSCustomException;

	AccountBean createNewAccount();

	boolean download(String destPath, String fileName);

	List<String> getFilesAvialable();
}
