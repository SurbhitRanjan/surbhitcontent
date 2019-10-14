package com.cg.ibs.im.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.Messaging.SyncScopeHelper;

import com.cg.ibs.bean.AccountBean;
import com.cg.ibs.bean.AccountType;
import com.cg.ibs.bean.AddressBean;
import com.cg.ibs.bean.ApplicantBean;
import com.cg.ibs.bean.ApplicantBean.ApplicantStatus;
import com.cg.ibs.bean.ApplicantBean.Gender;
import com.cg.ibs.bean.CustomerBean;
import com.cg.ibs.im.exception.IBSCustomException;
import com.cg.ibs.im.service.BankerService;
import com.cg.ibs.im.service.BankerSeviceImpl;
import com.cg.ibs.im.service.CustomerService;
import com.cg.ibs.im.service.CustomerServiceImpl;

public class IdentityManagementUI {
	static Scanner scanner;

	private CustomerService customer = new CustomerServiceImpl();
	private BankerService banker = new BankerSeviceImpl();
	private AccountBean account = new AccountBean();
	private ApplicantBean applicant = new ApplicantBean();
	private CustomerBean newCustomer = new CustomerBean();

	void init() {
		UserMenu choice = null;
		while (UserMenu.QUIT != choice) {
			System.out.println("------------------------");
			System.out.println("Choose your identity from MENU:");
			System.out.println("------------------------");
			for (UserMenu menu : UserMenu.values()) {
				System.out.println((menu.ordinal()) + 1 + "\t" + menu);
			}
			System.out.println("Choice");
			int ord = 0;
			String ordinal = scanner.next();
			boolean check = true;
			while (check) {
				if (ordinal.equals("1") || ordinal.equals("2") || ordinal.equals("3") || ordinal.equals("4")) {
					ord = Integer.parseInt(ordinal);
					check = false;
				} else {
					System.out.println("Please Re-enter: ");
					ordinal = scanner.next();
				}

			}

			if (0 < (ord) && UserMenu.values().length >= (ord)) {
				choice = UserMenu.values()[ord - 1];
				switch (choice) {
				case BANKER:
					try {
						selectBankerAction();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case CUSTOMER:
					try {
						selectCustomerAction();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case SERVICE_PROVIDER:
					selectSPAction(); // Group no. 6
					break;
				case QUIT:
					System.out.println("Application closed!!");
					break;
				}
			} else {
				System.out.println("Please enter a valid option.");
				choice = null;
			}

		}

	}

	public void selectBankerAction() {

		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Enter a login ID:");
			String bankUser = keyboardInput.readLine();
			System.out.println("Enter password:");
			String bankPassword = keyboardInput.readLine();

			while (!banker.verifyLogin(bankUser, bankPassword)) {
				System.out.println("Please enter valid details!");
				System.out.println("Enter login Id:");
				bankUser = keyboardInput.readLine();
				System.out.println("Enter password:");
				bankPassword = keyboardInput.readLine();
			}

			BankerAction choice = null;
			while (choice != BankerAction.GO_BACK) {
				System.out.println("------------------------");
				System.out.println("Choose a valid option");
				System.out.println("------------------------");
				for (BankerAction menu : BankerAction.values()) {
					System.out.println(menu.ordinal() + 1 + "\t" + menu);
				}
				System.out.println("Choices:");
				int ordinal = 0;
				String ord = scanner.next();
				boolean check = true;
				while (check) {
					if (ord.equals("1") || ord.equals("2") || ord.equals("3") || ord.equals("4")) {
						ordinal = Integer.parseInt(ord);
						check = false;
					} else {
						System.out.println("Please Re-enter: ");
						ord = scanner.next();
					}

				}

				if (0 < ordinal && BankerAction.values().length >= ordinal) {
					choice = BankerAction.values()[ordinal - 1];
					switch (choice) {
					case VIEW_PENDING_DETAILS:
						try {
							pendingApplications();
						} catch (Exception exception) {
							System.out.println(exception.getMessage());
						}
						break;
					case VIEW_APPROVED_DETAILS:
						approvedApplications();
						break;
					case VIEW_DENIED_DETAILS:
						deniedApplications();
						break;
					case GO_BACK:
						System.out.println("BACK ON HOME PAGE!!");
						break;
					}
				} else {
					System.out.println("Please enter a valid option.");
					choice = null;
				}
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

	public void selectCustomerAction() {
		CustomerMenu choice = null;
		while (choice != CustomerMenu.GO_BACK) {
			System.out.println("------------------------");
			System.out.println("Choose an appropriate option from MENU:");
			System.out.println("------------------------");
			for (CustomerMenu menu : CustomerMenu.values()) {
				System.out.println(menu.ordinal() + 1 + "\t" + menu);
			}
			System.out.println("Choice");
			int ordinal = 0;
			String ord = scanner.next();
			boolean check = true;
			while (check) {
				if (ord.equals("1") || ord.equals("2") || ord.equals("3") || ord.equals("4")) {
					ordinal = Integer.parseInt(ord);
					check = false;
				} else {
					System.out.println("Please Re-enter: ");
					ord = scanner.next();
				}

			}

			if (0 < ordinal && UserMenu.values().length >= ordinal) {
				choice = CustomerMenu.values()[ordinal - 1];
				switch (choice) {
				case SIGNUP:
					try {
						signUp();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case LOGIN:
					try {
						login();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case CHECK_STATUS:
					try {
						checkStatus();
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
					break;
				case GO_BACK:
					System.out.println("Going back...");
					break;
				}
			} else {
				System.out.println("Please enter a valid option.");
				choice = null;
			}
		}
	}

	public void selectSPAction() {
		System.out.println("Under Maintainence. (Use-Case 4!)");
	}

	void pendingApplications() {
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		Set<Long> pendingList = banker.viewPendingApplications();
		while (pendingList.size() > 0) {
			System.out.println("The list of the pending applicants is here:");
			Iterator<Long> iterator = pendingList.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}

			System.out.println("Enter an application number to check details:");
			long applicantId = scanner.nextLong();
//
			while (!banker.isApplicantPresentInPendingList(applicantId)) {
				System.out.println("applicant is not present. Please enter a valid id:");
				applicantId = scanner.nextLong();
			}
			try {
				applicant = banker.displayDetails(applicantId);
				if (applicant.getAccountType() == AccountType.INDIVIDUAL) {
					System.out.println("---------------------------");
					System.out.println(applicant.toString());
				} else if (applicant.getAccountType() == AccountType.JOINT) {
					System.out.println("---------------------------");
					System.out.println("Primary Holder: ");
					System.out.println(applicant.toString());
					System.out.println("");
					System.out.println("---------------------------");
					long secondaryHolder = applicant.getLinkedApplication();
					System.out.println("Secondary Holder: " + secondaryHolder);
					ApplicantBean secondaryAccountHolder = banker.displayDetails(secondaryHolder);
					System.out.println(secondaryAccountHolder.toString());
				}
				List<String> fnms = banker.getFilesAvialable();
				for (int i = 0; i < fnms.size(); i++) {
					System.out.println(i + "\t" + fnms.get(i));
				}
				System.out.println("Enter file index to download: ");
				int index = scanner.nextInt();
				System.out.println("Enter a download folder loc: ");
				String dwnLoc = scanner.next();
				banker.download(dwnLoc, fnms.get(index));
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
			}

			System.out.println("------------------------");
			System.out.println("Choose valid option:");
			System.out.println("------------------------");
			System.out.println("1.\tApprove application");
			System.out.println("2.\tDeny application");
			int choice = scanner.nextInt();
			while (choice != 1 && choice != 2) {
				System.out.println("Please enter a valid choice");
				choice = scanner.nextInt();
			}

			if (choice == 1) {
				try {
					ApplicantBean applicant = customer.getApplicantDetails(applicantId);
					applicant.setApplicantStatus(ApplicantStatus.APPROVED);
					customer.storeApplicantDetails(applicant);
					CustomerBean newCustomer = banker.createNewCustomer(applicant);
					System.out.println("The status has been approved for the applicant.\nCustomer ID: "
							+ newCustomer.getUci() + "\n");

// Add changes here!
					Set<AccountBean> accounts = newCustomer.getAccounts();
					for (AccountBean newAccount : accounts) {
						System.out.println("Account generated: " + newAccount.getAccountNumber());
					}

				} catch (Exception exception) {
					System.out.println(exception.getMessage());
				}

			} else if (choice == 2) {
				try {
					banker.updateStatus(applicantId, ApplicantStatus.DENIED);
					System.out.println("The application has been denied.\n");
				} catch (Exception exception) {
					System.out.println(exception.getMessage());
				}
			}
			pendingList = banker.viewPendingApplications();
			if (pendingList.size() > 0) {
				System.out.println("Do you want to keep reviewing applications?\n1. yes\n2. no");
				int continueChoice = scanner.nextInt();
				while (continueChoice != 1 && continueChoice != 2) {
					System.out.println(
							"Please enter an appropriate value. Do you want to keep reviewing applications?\n1. yes\n2. no");
					continueChoice = scanner.nextInt();
				}
				if (continueChoice == 2) {
					break;
				}
			}
		}
		if (pendingList.size() == 0) {
			System.out.println("There are no pending applicant requests.");
		}

	}

	void approvedApplications() {
		Set<Long> approvedList = banker.viewApprovedApplications();
		if (approvedList.size() > 0) {
			Iterator<Long> iterator = approvedList.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		} else {
			System.out.println("There are no approved applications.");
		}
	}

	void deniedApplications() {
		Set<Long> deniedList = banker.viewDeniedApplications();
		if (deniedList.size() > 0) {
			Iterator<Long> iterator = deniedList.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		} else {
			System.out.println("There are no denied applications.");
		}
	}

	public void signUp() { // newAccount
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(
					"Do you want to create an individual account or a joint account?\n1. Individual\n2. Joint");
			String typeOfAccount = keyboardInput.readLine();
			while (!typeOfAccount.equals("1") && !typeOfAccount.equals("2")) {
				System.out.println("Please enter a valid choice. Do you want to create an individual account "
						+ "or a joint account?\n1. Individual\n2. Joint");
				typeOfAccount = keyboardInput.readLine();
			}
			if (typeOfAccount.equals("1")) {
				System.out.println("Enter the following details: ");
				System.out.println("--------------------------------------");
				ApplicantBean applicant1 = new ApplicantBean();
				newApplication(applicant1);
				applicant1.setAccountType(AccountType.INDIVIDUAL);
				customer.saveApplicantDetails(applicant1);
				System.out.println(
						"Keep updated with your status.\nYour applicant id " + "is " + applicant1.getApplicantId());
			} else if (typeOfAccount.equals("2")) {
				System.out.println("Enter details for the primary account");
				System.out.println("--------------------------------------");
				ApplicantBean applicant1 = new ApplicantBean();
				newApplication(applicant1);
				applicant1.setAccountType(AccountType.JOINT);

				ApplicantBean applicant2 = new ApplicantBean();
				System.out.println("Enter details for the secondary customer: ");
				newApplication(applicant2);
				if (applicant1.getAadharNumber() != applicant2.getAadharNumber()) {
					applicant2.setAccountType(AccountType.JOINT);
					customer.saveApplicantDetails(applicant2);
					applicant1.setLinkedApplication(applicant2.getApplicantId());
					customer.saveApplicantDetails(applicant1);

					System.out.println(
							"Keep updated with your status.\nYour applicant" + " id is " + applicant1.getApplicantId());

				} else {
					System.out.println("Both the applicants are having same aadhar number");
				}

			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

	public void newApplication(ApplicantBean applicant) {
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter the first name");
		try {
			String firstName = keyboardInput.readLine();
			while (!customer.verifyName(firstName)) {
				System.out.println("Please enter an appropriate first name");
				firstName = keyboardInput.readLine();
			}
			applicant.setFirstName(firstName);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Enter the last name");
		try {
			String lastName = keyboardInput.readLine();
			while (!customer.verifyName(lastName)) {
				System.out.println("Please enter an appropriate last name");
				lastName = keyboardInput.readLine();
			}
			applicant.setLastName(lastName);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Enter Father's name");
		try {
			String fatherName = keyboardInput.readLine();
			while (!customer.verifyName(fatherName)) {
				System.out.println("Please enter an appropriate father's name");
				fatherName = keyboardInput.readLine();
			}
			applicant.setFatherName(fatherName);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Enter Mother's name");
		try {
			String motherName = keyboardInput.readLine();
			while (!customer.verifyName(motherName)) {
				System.out.println("Please enter an appropriate mother's name");
				motherName = keyboardInput.readLine();
			}
			applicant.setMotherName(motherName);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		String date = null;
		while (date == null) {
			System.out.println("Enter your Date of Birth in DD-MM-YYYY format");
			try {
				date = keyboardInput.readLine();

				LocalDate localDate = null;
				CustomerService customer = new CustomerServiceImpl();
				if (customer.dateVerifier(date)) {
					DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					localDate = LocalDate.parse(date, dtFormat);
//				boolean result = customer.verifyDob(localDate);
//				System.out.println(result);
					if (customer.verifyDob(localDate)) {
						System.out.println("Valid date");
					} else {
						System.out.println("Your age should be between 18 & 110 years.");
					}
				} else {
					System.out.println("Wrong date");
					date = null;
				}
			} catch (Exception exp) {
				System.out.println(exp.getMessage());
			}
		}
// System.out.println("Please enter a valid date of birth in correct
// format(dd-MM-yyyy).\nYour age should be greater than 18!");
// date = keyboardInput.readLine();
// Pattern pattern =
// Pattern.compile("((3[01])|([12][0-9])|(0[1-9]))\\-((1[0-2])|(0[1-9]))\\-([0-9]{4})");
// Matcher matcher = pattern.matcher(date);
// if (matcher.matches()) {
// localDate = LocalDate.parse(date, dtFormat);
// } else {
// localDate = null;
// }
// }

// Enter Gender
		Gender genderChoice = null;
		System.out.println("Choose your gender from the menu:");
		for (Gender menu : Gender.values()) {
			System.out.println(menu.ordinal() + 1 + "\t" + menu);
		}
		System.out.println("Choice");
		int ordinal = 0;
		String ord = scanner.next();
		boolean check = true;
		while (check) {
			if (ord.equals("1") || ord.equals("2") || ord.equals("3")) {
				ordinal = Integer.parseInt(ord);
				check = false;
			} else {
				System.out.println("Please Re-enter: ");
				ord = scanner.next();
			}

		}

		if (0 < ordinal && Gender.values().length > ordinal) {
			genderChoice = Gender.values()[ordinal - 1];
			switch (genderChoice) {
			case MALE:
				applicant.setGender(Gender.MALE);
				break;
			case FEMALE:
				applicant.setGender(Gender.FEMALE);
				break;
			case OTHERS:
				applicant.setGender(Gender.OTHERS);
				break;
			}
		}

// Permanent Address
		AddressBean address = addAddress();
		applicant.setPermanentAddress(address);

// Current Address
		System.out.println("Is your current address same as permanent address?\n1. yes\n2. no");
		int addressSame = scanner.nextInt();
		while (addressSame != 1 && addressSame != 2) {
			System.out.println("Please enter a valid choice. Is your current address same as"
					+ " permanent address?\1. yes\n2. no");
			addressSame = scanner.nextInt();
		}

		if (addressSame == 1) {
			applicant.setCurrentAddress(address);
		} else if (addressSame == 2) {
			address = addAddress();
			applicant.setCurrentAddress(address);
		}

		System.out.println("Enter Mobile number");
		String mobileNumber = scanner.next();
		while (!customer.verifyMobileNumber(mobileNumber)) {
			System.out.println("Please enter an appropriate phone number");
			mobileNumber = scanner.next();
		}
		applicant.setMobileNumber(mobileNumber);

		System.out.println("Enter Alternate Mobile Number");
		String alternateMobileNumber = scanner.next();
		while (!customer.verifyMobileNumber(alternateMobileNumber)) {
			System.out.println("Please enter an appropriate phone number");
			alternateMobileNumber = scanner.next();
		}

		while (customer.verifyMobileNumbers(mobileNumber, alternateMobileNumber)) {
			System.out.println("Alternate mobile number can't be the same as primary mobile number");
			alternateMobileNumber = scanner.next();
			while (!customer.verifyMobileNumber(alternateMobileNumber)) {
				System.out.println("Please enter an appropriate phone number");
				alternateMobileNumber = scanner.next();
			}
		}
		applicant.setAlternateMobileNumber(alternateMobileNumber);

		System.out.println("Enter email id");
		try {
			String emailId = keyboardInput.readLine();
			while (!customer.verifyEmailId(emailId)) {
				System.out.println("Please enter an appropriate email Id");
				emailId = keyboardInput.readLine();
			}
			applicant.setEmailId(emailId);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Enter Aadhar Number");
		try {
			String aadharNumber = keyboardInput.readLine();
			while (!customer.verifyAadharNumber(aadharNumber)) {
				System.out.println("Please enter an appropriate aadhar number");
				aadharNumber = keyboardInput.readLine();
			}
			applicant.setAadharNumber(aadharNumber);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

		System.out.println("Enter Pan Number");
		String panNumber = scanner.next();
		while (!customer.verifyPanNumber(panNumber)) {
			System.out.println("Please enter an appropriate PAN number");
			panNumber = scanner.next();
		}
		applicant.setPanNumber(panNumber);

		System.out.println("Upload two Government ID proofs");

		try {
			System.out.println("Enter the Path of Document 1 ");
			String filePath = keyboardInput.readLine();
			boolean choice = customer.upload(filePath);

			while (choice != true) {
				System.out.println("Please enter the path appropriately");
				filePath = keyboardInput.readLine();
				choice = customer.upload(filePath);
			}

			applicant.setApplicationDate(LocalDate.now());
			applicant.setApplicantStatus(ApplicantStatus.PENDING);

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public AddressBean addAddress() {
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your permanent address:");
		AddressBean address = new AddressBean();

		try {
			System.out.println("House Number:");
			String houseNumber = keyboardInput.readLine();
			while (houseNumber.length() == 0) {
				System.out.println("Please enter an appropriate house number.");
				houseNumber = keyboardInput.readLine();
			}
			address.setHouseNumber(houseNumber);

			System.out.println("Street Name:");
			String streetName = keyboardInput.readLine();
			while (streetName.length() == 0) {
				System.out.println("Please enter an appropriate street name.");
				streetName = keyboardInput.readLine();
			}
			address.setStreetName(streetName);

			System.out.println("Landmark:");
			String landmark = keyboardInput.readLine();
			while (landmark.length() == 0) {
				System.out.println("Please enter an appropriate landmark name.");
				landmark = keyboardInput.readLine();
			}
			address.setLandmark(landmark);

			System.out.println("Area:");
			String area = keyboardInput.readLine();
			while (area.length() == 0) {
				System.out.println("Please enter an appropriate area name.");
				area = keyboardInput.readLine();
			}
			address.setArea(area);

			System.out.println("City:");
			String city = keyboardInput.readLine();
			while (city.length() == 0) {
				System.out.println("Please enter an appropriate city name.");
				city = keyboardInput.readLine();
			}
			address.setCity(city);

			System.out.println("State:");
			String state = keyboardInput.readLine();
			while (state.length() == 0) {
				System.out.println("Please enter an appropriate state name.");
				state = keyboardInput.readLine();
			}
			address.setState(state);

			System.out.println("Country:");
			String country = keyboardInput.readLine();
			while (country.length() == 0) {
				System.out.println("Please enter an appropriate country name.");
				country = keyboardInput.readLine();
			}
			address.setCountry(country);

			System.out.println("Pincode:");
			String pinCode = keyboardInput.readLine();
			while (!customer.verifyPincode(pinCode)) {
				System.out.println("Please enter an appropriate pincode");
				pinCode = keyboardInput.readLine();
			}
			address.setPincode(pinCode);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return address;
	}

	public void login() {

		System.out.println("Please enter the UCI to login");
		String userUci = scanner.next();
		System.out.println("Enter the password");
		String password = scanner.next();
		try {
			if (customer.isCustomerValid(userUci) == false) {
				System.out.println("INVALID DETAILS! Enter the details again.");
				login();
			}
			try {
				if (customer.login(userUci, password)) {
					newCustomer = customer.getCustomerDetails(userUci);
					if (customer.firstLogin(userUci)) {
						firstLogin(userUci, password);
					}
					System.out.println("----------------------------------");
					System.out.println("Welcome to the Home Page!!");
					System.out.println("----------------------------------");
					System.out.println("Your accounts: ");
					Set<AccountBean> customerAccounts = newCustomer.getAccounts();
					for (AccountBean account : customerAccounts) {
						System.out.println("Account number:\t" + account.getAccountNumber());
					}

					System.out.println("----------------------------------");
					System.out.println("TABS: ");
					System.out.println("1. Create new Account");
					System.out.println("2. LOG OUT");

					String choiceLead = scanner.next();
					while (!choiceLead.equals("1") && !choiceLead.equals("2")) {
						System.out.println("Please enter an appropriate value.");
						choiceLead = scanner.next();
					}

					if (choiceLead.equals("1")) {
						BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
						try {
							System.out.println("Do you want to create an individual account or a joint "
									+ "account?\n1. Individual\n2. Joint");
							String typeOfAccount = keyboardInput.readLine();
							while (!typeOfAccount.equals("1") && !typeOfAccount.equals("2")) {
								System.out.println("Please enter a valid choice. Do you want to create an "
										+ "individual account or a joint account?\n1. Individual\n2. Joint");
								typeOfAccount = keyboardInput.readLine();
							}
							if (typeOfAccount.equals("1")) {
// create
								long applicantId = newCustomer.getApplicant().getApplicantId();
								ApplicantBean newApplicant = customer.getApplicantDetails(applicantId);

							} else if (typeOfAccount.equals("2")) {
// 
							}
						} catch (Exception exception) {
							System.out.println(exception.getMessage());
						}
					}

				}
			} catch (Exception exception) {
				System.out.println();
			}
		} catch (Exception exception) {
			System.out.println();
		}

	}

	public void firstLogin(String userUci, String password) {

		System.out.println("Reset your username");
		String userId = scanner.next();
		while (!customer.checkCustomerDetails(userId, userId)) {
			System.out.println("User Ids don't match.");
			System.out.println("Enter username again");
			userId = scanner.next();

		}
		try {
			CustomerBean newCustomer = customer.getCustomerDetails(userUci);
			if (customer.updateUserId(newCustomer, userId)) {
				System.out.println("User Id updated");

			}

			System.out.println("Reset your password");
			String userPassword = scanner.next();
			System.out.println("Confirm password");
			String confirmPassword = scanner.next();
			while (!customer.checkCustomerDetails(userPassword, confirmPassword)) {
				System.out.println("Passwords don't match.");
				System.out.println("Enter password again");
				userPassword = scanner.next();
				System.out.println("Confirm the password");
				confirmPassword = scanner.next();
			}
			try {
				customer.updatePassword(newCustomer, confirmPassword);
				System.out.println("Password updated");
			} catch (Exception exception) {
				System.out.println(exception.getMessage());
			}

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void checkStatus() {
		System.out.println("Enter the applicant ID to check status:");
		try {
			long applicantId;
			while (!scanner.hasNextLong()) {
				System.out.println("Enter a valid Applicant ID!");
				scanner.next();
				scanner.nextLine();
			}
			applicantId = scanner.nextLong();
			while (!customer.verifyApplicantId(applicantId)) {
				System.out.println("Please enter a valid applicant ID");
				applicantId = scanner.nextLong();
			}
			ApplicantStatus status = customer.checkStatus(applicantId);
			System.out.println("Your application status is: " + status);

			if (status == ApplicantStatus.APPROVED) {
				CustomerBean newCustomer = customer.getCustomerByApplicantId(applicantId);
				String uci = newCustomer.getUci();
				String userId = newCustomer.getUserId();
				String password = newCustomer.getPassword();
				System.out.println("Login using the following details:");
				System.out.println("\nUCI: " + uci);
				System.out.println("\nUser ID: " + userId);
				System.out.println("\nPassword: " + password);
				Set<AccountBean> accounts = newCustomer.getAccounts();
				for (AccountBean account : accounts) {
					System.out.println("\nAccount Number: " + account.getAccountNumber());
				}
// get Last account number. the last generated
// String accountNumber = account.getAccountNumber();
// System.out.println("\nAccount Number: " + accountNumber);

			} else if (status == ApplicantStatus.DENIED) {
				System.out.println("The application has been denied.");
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

	public static void main(String[] args) {

		scanner = new Scanner(System.in);
		IdentityManagementUI identityManagement = new IdentityManagementUI();
		identityManagement.init();
		scanner.close();

	}
}
