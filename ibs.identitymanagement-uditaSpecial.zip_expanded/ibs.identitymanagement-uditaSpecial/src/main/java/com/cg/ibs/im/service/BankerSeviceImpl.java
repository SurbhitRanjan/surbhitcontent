package com.cg.ibs.im.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.dao.ApplicantDao;
import com.cg.ibs.im.dao.ApplicantDaoImpl;
import com.cg.ibs.im.dao.CustomerDao;
import com.cg.ibs.im.dao.CustomerDaoImpl;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.exception.IBSException;

public class BankerSeviceImpl implements BankerService {
	CustomerDao customerDao = new CustomerDaoImpl();
	ApplicantDao applicantDao = new ApplicantDaoImpl();
	CustomerBean customer = new CustomerBean();
	AccountBean account = new AccountBean();

	private static BigInteger uciConstant = new BigInteger("1111222210000000");
	private static BigInteger accountVariable = new BigInteger("05010010000");

	public static String generateUci() {
		uciConstant = uciConstant.add(new BigInteger("1"));
		String str = uciConstant.toString();
		return str;
	}

	public static String generateAccountNumber() {
		accountVariable = accountVariable.add(new BigInteger("1"));
		String str = accountVariable.toString();
		return str;
	}

	@Override
	public boolean verifyLogin(String user, String password) {
		boolean result = false;
		if ((user.equals("admin1") && password.equals("pass")) || user.equals("admin") && password.equals("key1")) {
			result = true;
		}
		return result;
	}

	@Override
	public Set<Long> viewPendingApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.PENDING);
	}

	@Override
	public Set<Long> viewApprovedApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.APPROVED);
	}

	@Override
	public Set<Long> viewDeniedApplications() {
		return applicantDao.getApplicantsByStatus(ApplicantStatus.DENIED);
	}

	@Override
	public boolean updateStatus(long applicantId, ApplicantStatus applicantStatus) throws IBSCustomException {
		boolean result = false;
		if (applicantDao.getApplicantDetails(applicantId) != null) {
			applicantDao.getApplicantDetails(applicantId).setApplicantStatus(applicantStatus);
			result = true;
		} else {
			throw new IBSCustomException(IBSException.invalidApplicantId);
		}
		return result;
	}

	@Override
	public String generatePassword(long applicantId) {
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder stringBuffer = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			int index = (int) (alphaNumeric.length() * Math.random());
			stringBuffer.append(alphaNumeric.charAt(index));
		}
		return stringBuffer.toString();
	}

	@Override
	public String generateUsername(long applicantId) throws IBSCustomException {
		ApplicantBean applicant = applicantDao.getApplicantDetails(applicantId);
		String username = applicant.getLastName().substring(0, 4) + applicant.getMobileNumber().substring(6, 9);
		return username;
	}

	@Override
	public CustomerBean createNewCustomer(ApplicantBean applicant) throws IBSCustomException {

		long applicantId = applicant.getApplicantId();

		String customerUci = generateUci();
		customer.setUci(customerUci);
		customer.setUserId(generateUsername(applicantId));
		customer.setPassword(generatePassword(applicantId));
		account = createNewAccount();
		Set<AccountBean> accounts = new HashSet<AccountBean>();
		accounts.add(account);
		customer.setAccounts(accounts);
		customer.setApplicant(applicant);

		boolean result = customerDao.saveCustomer(customer);
		if (result == true)
			return customer;
		else
			throw new IBSCustomException(IBSException.customerNotPresent);
	}

	@Override
	public AccountBean createNewAccount() {
		account.setAccountNumber(generateAccountNumber());
		account.setCurrentBalance(new BigDecimal("0.00"));
		// set other details
		return account;
	}

	@Override
	public ApplicantBean displayDetails(long applicantId) throws IBSCustomException {
		ApplicantBean applicant = applicantDao.getApplicantDetails(applicantId);
		return applicant;

	}
	
	@Override
	public List<String> getFilesAvialable() {
		List<String> files = new ArrayList<>();
		File upLoc = new File(CustomerDaoImpl.UPLOADS_LOC);
		for (File f : upLoc.listFiles()) {
			files.add(f.getName());
		}
		return files;
	}

	@Override
	public boolean download(String destPath, String fileName) {
		String srcPath = CustomerDaoImpl.UPLOADS_LOC + "/" + fileName;
		destPath += "/" + fileName;
		return customerDao.copy(srcPath, destPath);
	}

	@Override
	public boolean isApplicantPresent(long applicantId) {
		boolean result = false;
		if (applicantId != 0) {
			result = applicantDao.isApplicantPresent(applicantId);
		}

		return result;
	}

	@Override
	public boolean isApplicantPresentInPendingList(long applicantId) {
		boolean result = false;
		if (applicantId != 0) {
			Set<Long> pendingApplicants = applicantDao.getApplicantsByStatus(ApplicantStatus.PENDING);
			Iterator<Long> it = pendingApplicants.iterator();
			while (it.hasNext()) {
				if (it.next() == applicantId) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

}
