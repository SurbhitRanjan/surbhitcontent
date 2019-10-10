package com.cg.ibs.im.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.ApplicantBean.Gender;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class ApplicantDaoImpl implements ApplicantDao {

	private static Map<Long, ApplicantBean> applicantDao = new HashMap<Long, ApplicantBean>();

	static {
		ApplicantBean applicant1 = new ApplicantBean();
		applicant1.setFirstName("Sanjay");
		applicant1.setLastName("Ramaswamy");
		applicant1.setDob(LocalDate.now());
		applicant1.setFatherName("Ramu");
		applicant1.setMotherName("Sita");
		applicant1.setGender(Gender.MALE);
		applicant1.setMobileNumber("9553528684");
		applicant1.setAlternateMobileNumber("9944995656");
		applicant1.setEmailId("ramu999@gmail.com");
		applicant1.setAadharNumber("1234 1256 4586");
		applicant1.setPanNumber("ASEPY8911H");
		applicant1.setApplicantId(12345);
		AddressBean address1 = new AddressBean();
		address1.setHouseNumber("3-177");// Address
		address1.setStreetName("LIG Colony Road");
		address1.setArea("Talawade");
		address1.setCity("Pune");
		address1.setLandmark("Devi Indrayani Appartments");
		address1.setPincode("404112");
		address1.setState("Maharastra");
		address1.setCountry("India");
		applicant1.setCurrentAddress(address1);
		applicant1.setPermanentAddress(address1);
		applicant1.setApplicantStatus(ApplicantStatus.APPROVED);
		applicant1.setApplicationDate(LocalDate.now());
		applicantDao.put(applicant1.getApplicantId(), applicant1);

		ApplicantBean applicant2 = new ApplicantBean();
		applicant2.setFirstName("Sagar");
		applicant2.setLastName("Swamy");
		applicant2.setDob(LocalDate.now());
		applicant2.setFatherName("Somu");
		applicant2.setMotherName("Rita");
		applicant2.setGender(Gender.MALE);
		applicant2.setMobileNumber("9453558684");
		applicant2.setAlternateMobileNumber("9526499565");
		applicant2.setEmailId("Sagar99@gmail.com");
		applicant2.setAadharNumber("1534 1356 4559");
		applicant2.setPanNumber("ASEXG8711L");
		applicant2.setApplicantId(13680);
		AddressBean address2 = new AddressBean();
		address2.setHouseNumber("3-177");// Address
		address2.setStreetName("MIDC Road");
		address2.setArea("Talawade");
		address2.setCity("Pune");
		address2.setLandmark("Devi Indrayani Appartments");
		address2.setPincode("404112");
		address2.setState("Maharastra");
		address2.setCountry("India");
		applicant2.setPermanentAddress(address2);
		applicant2.setCurrentAddress(address1);
		applicant2.setApplicantStatus(ApplicantStatus.PENDING);
		applicant2.setApplicationDate(LocalDate.now());
		applicantDao.put(applicant2.getApplicantId(), applicant2);

		ApplicantBean applicant3 = new ApplicantBean();
		applicant3.setFirstName("Sagarika");
		applicant3.setLastName("Reddy");
		applicant3.setDob(LocalDate.now());
		applicant3.setFatherName("Lakshman");
		applicant3.setMotherName("Kumari");
		applicant3.setGender(Gender.FEMALE);
		applicant3.setMobileNumber("9885448684");
		applicant3.setAlternateMobileNumber("9956512345");
		applicant3.setEmailId("Sagarika1801@gmail.com");
		applicant3.setAadharNumber("3415 5613 5954");
		applicant3.setPanNumber("BSERH8788L");
		applicant3.setApplicantId(12950);
		applicant3.setCurrentAddress(address2);
		applicant3.setPermanentAddress(address1);
		applicant3.setApplicantStatus(ApplicantStatus.PENDING);
		applicant3.setApplicationDate(LocalDate.now());
		applicantDao.put(applicant3.getApplicantId(), applicant3);

		ApplicantBean applicant4 = new ApplicantBean();
		applicant4.setFirstName("Surbith");
		applicant4.setLastName("Ranajan");
		applicant4.setDob(LocalDate.now());
		applicant4.setFatherName("Ramesh");
		applicant4.setMotherName("Anu");
		applicant4.setGender(Gender.MALE);
		applicant4.setMobileNumber("9868466455");
		applicant4.setAlternateMobileNumber("9956500123");
		applicant4.setEmailId("Surbith129@gmail.com");
		applicant4.setAadharNumber("1996 1856 2019");
		applicant4.setPanNumber("ASSRG1811R");
		applicant4.setApplicantId(18019);
		AddressBean address4 = new AddressBean();
		address4.setHouseNumber("G-15");
		address4.setStreetName("Bhadson Road");
		address4.setArea("Nigdi Chowk ");
		address4.setCity("Jammu");
		address4.setLandmark("Near ABC School");
		address4.setPincode("101010");
		address4.setState("Jammu And Kashmir");
		address4.setCountry("India");
		applicant4.setPermanentAddress(address4);
		applicant4.setCurrentAddress(address4);
		applicant4.setPermanentAddress(address1);
		applicant4.setApplicantStatus(ApplicantStatus.DENIED);
		applicant4.setApplicationDate(LocalDate.now());
		applicantDao.put(applicant4.getApplicantId(), applicant4);
	}

	@Override
	public boolean saveApplicant(ApplicantBean applicant) {
		boolean result = false;
		if (applicant != null) { // check if applicant already exists. (out of
									// scope)
			applicantDao.put(applicant.getApplicantId(), applicant);
			result = true;
		}
		return result;
	}

	@Override
	public Set<Long> getAllApplicants() {
		return new TreeSet<Long>(applicantDao.keySet());
	}

	public boolean isApplicantPresent(long applicantId) {
		boolean result = false;
		for (Map.Entry<Long, ApplicantBean> entry : applicantDao.entrySet()) {
			if (entry.getKey().equals(applicantId)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public ApplicantBean getApplicantDetails(long applicantId) throws IBSCustomException {
		ApplicantBean newApplicant = new ApplicantBean();
		for (Map.Entry<Long, ApplicantBean> entry : applicantDao.entrySet()) {
			if (entry.getKey().equals(applicantId)) {
				newApplicant = entry.getValue();
				break;
			}
		}
		if (newApplicant == null) {
			throw new IBSCustomException(IBSException.applicantNotFound);
		}
		return newApplicant;
	}

	@Override
	public Set<Long> getApplicantsByStatus(ApplicantStatus applicantStatus) {
		ApplicantBean applicant;
		Set<Long> applicants = new TreeSet<Long>();
		for (Map.Entry<Long, ApplicantBean> entry : applicantDao.entrySet()) {
			applicant = entry.getValue();
			if (applicant.getApplicantStatus() == applicantStatus) {
				applicants.add(applicant.getApplicantId());
			}
		}
		return applicants;
	}

}
