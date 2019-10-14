package com.cg.ibs.im.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.ApplicantBean.Gender;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class CustomerDaoImpl implements CustomerDao {

	private static Map<String, CustomerBean> customerDao = new HashMap<String, CustomerBean>();
	public static final String UPLOADS_LOC="./uploads";
	CustomerBean newCustomer = new CustomerBean();
	
	static {
		CustomerBean customer1 = new CustomerBean();
		customer1.setUci("5555111151511001");
		customer1.setUserId("udita");
		customer1.setPassword("abc123");
		AccountBean account1 = new AccountBean();
		account1.setAccountNumber("05100001111");
		account1.setCurrentBalance(new BigDecimal("20048.32"));
		Set<AccountBean> accounts1 = new HashSet<AccountBean>();
		accounts1.add(account1);
		customer1.setAccounts(accounts1);
		customer1.setLogin(1);
			ApplicantBean applicant1 = new ApplicantBean();
			applicant1.setFirstName("Udita");
			applicant1.setLastName("Alawadhi");
			LocalDate date = LocalDate.of(1997, 07, 28);
			applicant1.setDob(date);
			applicant1.setFatherName("Kamal Alawadhi");
			applicant1.setMotherName("Poonam Alawadhi");
			applicant1.setGender(Gender.FEMALE);
			applicant1.setMobileNumber("9553528684");
			applicant1.setAlternateMobileNumber("9944995656");
			applicant1.setEmailId("udita@gmail.com");
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
		customer1.setApplicant(applicant1);
		customerDao.put(customer1.getUci(), customer1);
		
		CustomerBean customer2 = new CustomerBean();
		customer2.setUci("5555111151512201");
		customer2.setUserId("chetan551");
		customer2.setPassword("xyz123");
		AccountBean account2 = new AccountBean();
		account2.setAccountNumber("05100222111");
		account2.setCurrentBalance(new BigDecimal("208.92"));
		Set<AccountBean> accounts = new HashSet<AccountBean>();
		accounts.add(account2);
		customer2.setAccounts(accounts);
		customer2.setLogin(1);
			ApplicantBean applicant2 = new ApplicantBean();
			applicant2.setFirstName("Safe");
			applicant2.setLastName("Ali");
			LocalDate date2 = LocalDate.of(1993, 01, 03);
			applicant2.setDob(date2);
			applicant2.setFatherName("KAL");
			applicant2.setMotherName("XYZ");
			applicant2.setGender(Gender.FEMALE);
			applicant2.setMobileNumber("9223528684");
			applicant2.setAlternateMobileNumber("9941195656");
			applicant2.setEmailId("safe@outlook.com");
			applicant2.setAadharNumber("489512564586");
			applicant2.setPanNumber("ASEPA9911H");
			applicant2.setApplicantId(12345);
				AddressBean address2 = new AddressBean();
				address2.setHouseNumber("House No- 999");// Address
				address2.setStreetName("LIG Colony Road");
				address2.setArea("Talawade");
				address2.setCity("Pune");
				address2.setLandmark("Ganga Society");
				address2.setPincode("404332");
				address2.setState("Maharastra");
				address2.setCountry("India");
				applicant2.setCurrentAddress(address2);
				applicant2.setPermanentAddress(address2);
			applicant2.setApplicantStatus(ApplicantStatus.APPROVED);
			applicant2.setApplicationDate(LocalDate.now());
		customer2.setApplicant(applicant2);
		customerDao.put(customer2.getUci(), customer2);
	}
	
	@Override
	public boolean saveCustomer(CustomerBean customer) throws IBSCustomException {
		boolean result = false;
		if (customer != null) {
			customerDao.put(customer.getUci(), customer);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return result;
	}

	@Override
	public CustomerBean getCustomerDetails(String uci) throws IBSCustomException {
		newCustomer = new CustomerBean();
		for (Entry<String, CustomerBean> entry : customerDao.entrySet()) {
			if (entry.getKey().equals(uci)) {
				newCustomer = entry.getValue();
				break;
			}
		}
		if (newCustomer==null) {
			throw new IBSCustomException(IBSException.customerNotPresent);
		}
		return newCustomer;
	}

	@Override
	public Set<String> getAllCustomers() {
		return new TreeSet<String>(customerDao.keySet());
	}

	public CustomerBean getCustomerByApplicantId(long applicantId) {
		CustomerBean newCustomer = new CustomerBean();
		for (Entry<String, CustomerBean> entry : customerDao.entrySet()) {
			long appId = entry.getValue().getApplicant().getApplicantId();
			if (appId == applicantId) {
				newCustomer = entry.getValue();
				break;
			}
		}
		return newCustomer;
	}
	
	@Override
	public boolean copy(String srcPath, String destPath) {
        boolean isDone=false;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        if (srcFile.exists()) {
                try (FileInputStream fin = new FileInputStream(srcFile);
                                FileOutputStream fout = new FileOutputStream(destFile)) {
                        byte[] data = new byte[1024];
                        while(fin.read(data)>-1) {
                                fout.write(data);
                        }
                        isDone=true;
                } catch (IOException exception) {
                        //raise a user defiend exception
                }
        } else {
                // throw your exception
        }
        return isDone;
	}
}