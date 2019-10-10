package com.cg.ibs.bean;

import java.time.LocalDate;


public class ApplicantBean {
	private String firstName;
	private String lastName;
	private String fatherName;
	private String motherName;
	private LocalDate dob;
	private Gender gender;
	private AddressBean permanentAddress;
	private AddressBean currentAddress;
	private String mobileNumber;
	private String alternateMobileNumber;
	private String emailId;
	private String aadharNumber;
	private String panNumber;
	private AccountType accountType;
	private static long applicationId=10000;						//5 digit applicant ID
	private long applicantId;
	private ApplicantStatus applicantStatus;
	private LocalDate applicationDate;
	Document[] document;
	
	public ApplicantBean(){
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public AddressBean getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(AddressBean permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public AddressBean getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(AddressBean currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
	public void setApplicantId(long appID) {
		this.applicantId = appID;
	}

	public long getApplicantId() {
		return applicantId;
	}

	public ApplicantStatus getApplicantStatus() {
		return applicantStatus;
	}

	public void setApplicantStatus(ApplicantStatus applicantStatus) {
		this.applicantStatus = applicantStatus;
	}

	public Document[] getDocument() {
		return document;
	}

	public void setDocument(Document[] document) {
		this.document = document;
	}

	public LocalDate getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}

	public enum AccountType {
		INDIVIDUAL, JOINT
	}

	public enum Gender {
		MALE, FEMALE, OTHERS
	}
	
	public enum ApplicantStatus{
		PENDING, APPROVED, DENIED
	}

	public static long generateApplicantId(){
		return ++applicationId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Applicant Id: "+applicantId);
		builder.append("\nFirst Name=");
		builder.append(firstName);
		builder.append("\nLast Name=");
		builder.append(lastName);
		builder.append("\nFather's Name=");
		builder.append(fatherName);
		builder.append("\nMother's Name=");
		builder.append(motherName);
		builder.append("\nDate of Birth:");
		builder.append(dob);
		builder.append("\nGender:");
		builder.append(gender);
		builder.append("\nPermanent Address=");
		builder.append(permanentAddress);
		builder.append("\nCurrent Address=");
		builder.append(currentAddress);
		builder.append("\nMobile Number=");
		builder.append(mobileNumber);
		builder.append("\nAlternate Mobile Number=");
		builder.append(alternateMobileNumber);
		builder.append("\nEmail Id=");
		builder.append(emailId);
		builder.append("\nAadhar Number=");
		builder.append(aadharNumber);
		builder.append("\nPAN Number=");
		builder.append(panNumber);
//		builder.append("\nAccount Type=");
//		builder.append(accountType);
		builder.append("\nApplicant Id=");
		builder.append(applicantId);
		builder.append("\nApplicant Status=");
		builder.append(applicantStatus);
		builder.append("\nApplication Date=");
		builder.append(applicationDate);
		return builder.toString();
	}
	
	
}
