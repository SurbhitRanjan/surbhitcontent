package com.cg.ibs.im.dao;

import java.util.Set;

import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.im.exception.IBSCustomException;

public interface ApplicantDao {
	
	boolean saveApplicant(ApplicantBean applicant);
	
	Set<Long> getAllApplicants();
	
	ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException;
	
	Set<Long> getApplicantsByStatus(ApplicantStatus applicantStatus);

	boolean isApplicantPresent(long applicantId);

	
	
	
	
}
