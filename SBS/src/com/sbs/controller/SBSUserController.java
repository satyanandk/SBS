package com.sbs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.asu.ss.secure_banking_system.model.DepartmentEnum;
import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.model.dbhash;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;
import com.asu.ss.secure_banking_system.validation.CommonValidations;
import com.sbs.model.combined.UserandOTP;
import com.sbs.model.crypto.PkiUtils;
import com.sbs.model.notification.Notification;
import com.sbs.model.notification.NotificationList;
import com.sbs.model.notification.NotificationManager;
import com.sbs.model.transaction.Transaction;
import com.sbs.model.transaction.TransactionManager;
import com.sbs.model.transaction.TransactionType;
import com.sbs.model.user.User;
import com.sbs.model.user.UserManager;
import com.sbs.model.userpassword.UserIDPasswordMappingEntity;
import com.sbs.model.userpassword.userPasswordManager;


@Controller("SBSUserController")
public class SBSUserController {
	
	//testing purpose
	@RequestMapping("/requestrole")
    public String requestrPage(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
        model.addAttribute("user", user);
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isRequestRoleAuthorized())
		{
	        return "request_role";	
		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Request role page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}

    }
	@RequestMapping("/internal_error")
    public String internalError(Model model, HttpSession session) {	
			return "internal_error";

    }

	@RequestMapping("/process_taa")
    public String processtaa(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isProcessTAAAuthorized())
		{
			return "process_taa";
		}
		else
		{
			return "not_authorized";
		}
    }

	@RequestMapping("/invalid_request")
    public String invalidRequest(Model model) {	
        return "invalid_request";
    }
	
	@RequestMapping("/taa_notification")
    public String taanotification(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isTAANotificationAuthorized())
		{
			 return "taa_notification";
		}
		else
		{
			return "not_authorized";
		}       
    }	
	@RequestMapping("/assign_taa")
    public String assigntaa(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAssignTAAAuthorized())
		{
	        return "assign_taa";		
	    }
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to assign Technical Account access page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}		
    }	
	@RequestMapping("/profilePage")
    public String profilePa(Model model, HttpSession session) {	
		User user = new User();
		if(session == null||session.getAttribute("UserID") == null)
			return "not_authorized";
        model.addAttribute("user", user);
    	model.addAttribute("Emailid", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getEmailid());
    	model.addAttribute("Dob", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getDOB());
    	model.addAttribute("Contact",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getContact());
    	model.addAttribute("Address",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getAddress());
    	model.addAttribute("Firstname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getFirstname());
    	model.addAttribute("Lastname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getLastname());
    	model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
		//need LDAP server to check UserID
    	if(session.getAttribute("UserID") == null)
			return "not_authorized";
    	user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		System.out.println(user.getRoleID());
		System.out.println(user.getUserID());
		if(user.getPageAuthorization().isProfilePageAuthorized())
		{
	        return "Profile";
		}
		else
		{
			return "not_authorized";
		} 
    }
	
	@RequestMapping("/welcomepage")
    public String welcomePageFunciton(Model model, HttpSession session) {	
		User user = new User();
        model.addAttribute("user", user);
        if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
        
        switch(UserManager.retrieiveUser((String)session.getAttribute("UserID")).getRoleID())
		{
        
		case 1: 
			return "welcomeMerchant";
		case 2: 
			return "welcomeExternal";
		case 3: 
			return "welcomeAdmin";
		case 4: 
			return "welcomeInternal";
		case 5: 
			return "welcomeInternal";
		case 6: 
			return "welcomeInternal";
		default : 
			return "loginPage";
		}
    }
	@RequestMapping("/admin_notif")
    public String adminnot(Model model, HttpSession session) {	
		User user = new User();
		if(session == null||session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAdminNotificationAuthorized())
		{
			return "admin_notification";
		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Admin notification page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}
    }

	@RequestMapping("/not_authorized")
    public String notAuthorized(Model model, HttpSession session) {	
		
			return "not_authorized";
    }
	
	@RequestMapping("/creditPage")
	public String creditPageFunction(Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isCreditAuthorized())
		{
			return "credit";		
		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Credit page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}


	}
	
	@RequestMapping("/TransferPage")
	public String TransferPageFunction(Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isTransferAuthorized())
		{
			return "Transfer";
		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Money Transfer page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}
	}
	
	@RequestMapping("/errorPagePage")
	public String errorPageFunction(Model model){
		return "errorPage";
	}
	
	@RequestMapping("/RequestPaymentPage")
	public String RequestPaymentPageFunction(Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isRequestPaymentAuthorized())
		{
			return "RequestPayment";
		}
		else
		{	Transaction t = new Transaction(
				TransactionType.UNAUTHORIZED_ACCESS, null, null,
				user.getUserID(), null, 0.0, 0.0,
				"Unauthorized access to Request payment page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}	

	}
	
	@RequestMapping("/createAccountPage")
	public String createAccountPageFunction(Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isCreateAccountAuthorized())
		{
			return "createAccount";
		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Create account page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}	

	}
	@RequestMapping("/AccountCriteriaPage")
    public String AccountCriteriaPageFunction(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDebitAuthorized())
		{
	        return "AccountCriteria";
	    }
		else
		{
			return "not_authorized";
		} 
    }
	
	@RequestMapping("/AccountSummaryPage")
    public String AccountSummaryPageFunction(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDebitAuthorized())
		{
	        return "AccountSummary";
	    }
		else
		{
			return "not_authorized";
		} 
    }
	
	@RequestMapping("/PaymentConfPage")
	public String PaymentConfPageFunction(Model model, HttpSession session){
		
			return "PaymentConf";
		
	}
	
	@RequestMapping("/request_taa")
    public String requesttaa(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isRequestTAAAuthorized())
		{
		       return "request_taa";
		}
		else
		{
			return "not_authorized";
		} 
    }
	@RequestMapping("/assign_role")
    public String assignrole(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAssignRoleAuthorized())
		{
	        return "assign_role";
	        }
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Assign Role page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		} 

    }
	
	@RequestMapping("/resultpagepage")
    public String resultpagepageFunction(Model model, HttpSession session) {
		
		/*User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);*/
		/*if(user.getPageAuthorization().isResultPageAuthorized())
		{*/
	        return "resultPage";
	       /* }
		else
		{
			return "not_authorized";
		} */
    }
	
	
	@RequestMapping("/FinancialOTPPage")
    public String FinancialOTPPageFunction(Model model, HttpSession session) {	
		User user = new User();
		
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isFinancialOTPAuthorized())
		{
	        return "FinancialOTP";
	    }
		else
		{
			return "not_authorized";
		} 
    }
	
	
	@RequestMapping("/DebitPage")
    public String DebitPageFunction(Model model, HttpSession session) {	
		User user = new User();
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		if(session.getAttribute("UserID") == null ||session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDebitAuthorized())
		{
	        return "Debit";
	    }
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Debit page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		} 
    }
	
	@RequestMapping("/CheckID")
    public String IDcheck(Model model, HttpSession session) {	
		User user = new User();
        model.addAttribute("user", user);
		//need LDAP server to check UserID
        return "CheckUserID";
    }
	
	
	@RequestMapping(value="/checkuserid", method = RequestMethod.POST)
	public String checkid(@ModelAttribute("user") User user, Model model, HttpSession session) throws Exception{
		
		
		String errorMessage = new String();
		errorMessage = CommonValidations.validateStringNormal(user.getUserID(), 15, errorMessage, "User ID",true);
		if(!errorMessage.isEmpty()){
			System.out.println("errorCheckUserid = "+errorMessage);
			model.addAttribute("errorCheckUserid",errorMessage);
			return "CheckUserID";
		}
		String userID = UserManager.userIDCheck(user.getUserID());
		session.setAttribute("UserID", userID);
		String error = "UserID does not exist";
		
		if(userID==null)
		{
			model.addAttribute("errorCheckUserid",error);
			return "CheckUserID";
		}
		//System.out.println(userID);
		else{
			if(userPasswordManager.CheckPasswordExistance(userID)==false){
		    	model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
		    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
		    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
				return "forgetPassword";
			}
			else{
				UserIDPasswordMappingEntity upme = new UserIDPasswordMappingEntity();
				model.addAttribute("upme",upme);
			return "loginPage"; 
			}

		}
	}
	@RequestMapping(value="/loginFunction", method = RequestMethod.POST)
	public String loginf(@ModelAttribute("upme") UserIDPasswordMappingEntity upme, Model model, HttpSession session) throws Exception{
		
		String error =null;

		if(upme.getPassword()== null || upme.getPassword().isEmpty())
		{
			error= "Password must be entered.";
			model.addAttribute("errorblocked",error);
			return "loginPage";
		}
		boolean b = CommonValidations.checkValidPassword(upme.getPassword());
		if(!b)
		{
			error= "Invalid password";
			model.addAttribute("errorblocked",error);
			return "loginPage";
		}
		String message = userPasswordManager.validate(session.getAttribute("UserID").toString(), upme.getPassword());
		System.out.println(message);
		
		if(message!=null)
		{
			
			switch(message)
			{
			case "b": 
				error = "User been blocked";
				model.addAttribute("errorblocked",error);
				return  "loginPage";
			case "p":
				error = "Wrong password";
				model.addAttribute("errorblocked",error);
				return  "loginPage";
			default:
				return "loginPage";
				
			}
		}
		//System.out.println(userID);
		else{
			session.setMaxInactiveInterval(180000);
			if(session == null || session.getAttribute("UserID") == null)
				return "not_authorized";
			System.out.println("User ID switch case= "+(String)session.getAttribute("UserID"));
			switch(UserManager.retrieiveUser((String)session.getAttribute("UserID")).getRoleID())
			{	
			case 1: 
				return "welcomeMerchant";
			case 2: 
				return "welcomeExternal";
			case 3: 
				return "welcomeAdmin";
			case 4: 
				return "welcomeInternal";
			case 5: 
				return "welcomeInternal";
			case 6: 
				return "welcomeInternal";
			default : 
				return "loginPage";
			}
		}
	}
	
	
	
////	session.setAttribute("Emailid", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getEmailid());
////	session.setAttribute("Date of Birth", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getDOB());
////	session.setAttribute("Contact",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getContact());
////	session.setAttribute("Address",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getAddress());
////	session.setAttribute("Firstname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getFirstname());
////	session.setAttribute("Lastname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getLastname());
////	session.setAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
////	session.setAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
////	session.setAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());
//return "Profile";
	@RequestMapping("/forgetPasswordPage")
	public String forget(@ModelAttribute("user") User user, Model model, HttpSession session){
		model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
    	user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isForgetPasswordAuthorized())
		{
			return "forgetPassword";		
		}
		else
		{
			return "not_authorized";
		} 

	}
	
	@RequestMapping(value = "/forgetPassFunction", method = RequestMethod.POST)
	public String forgetpasswordfuntion(@ModelAttribute("user") User user, Model model, HttpSession session) throws Exception{
		
		String error=null;
		boolean b = true;
		if(user.getDOB() ==null || user.getDOB().isEmpty())
		{
			error = "Date of Birth must be entered.";
			model.addAttribute("errorblocked",error);
			model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
	    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
	    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
			return "forgetPassword";
		}
		b = CommonValidations.validateDateOfBirth(user.getDOB());
		if(!b){
			error = "Invalid date of birth.";
			model.addAttribute("errorblocked",error);
			model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
	    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
	    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
			return "forgetPassword";
		}
		
		error= CommonValidations.validateStringNormal(user.getSecureA1(), 40, error, "Security question 1 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("errorblocked",error);
			model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
	    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
	    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
			return "forgetPassword";
		}
		
		error= CommonValidations.validateStringNormal(user.getSecureA2(), 40, error, "Security question 2 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("errorblocked",error);
			model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
	    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
	    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
			return "forgetPassword";
		}
		
		error= CommonValidations.validateStringNormal(user.getSecureA3(), 40, error, "Security question 3 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("errorblocked",error);
			model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
	    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
	    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
			return "forgetPassword";
		}
		
		
		String message = UserManager.forgetpasswordcheck((String)session.getAttribute("UserID"), user.getDOB(), user.getSecureA1(), user.getSecureA2(), user.getSecureA3());
		System.out.print(message);
		UserandOTP userandotp = new UserandOTP() ;
		model.addAttribute("userandotp",userandotp);
		if(message ==null)
		{
			OneTimePasswd otp = new OneTimePasswd((String)session.getAttribute("UserID"));
			otp.insertOTPCodeForUser();
			user.setUserID(session.getAttribute("UserID").toString());
			user = AuthorizationUtil.loadAuthorization(user);
			if(user.getPageAuthorization().isNewPasswordSetupAuthorized())
			{
				return "NewPasswordSetup";
			}
			else
			{
				return "not_authorized";
			}	
			
		}
		else
		{
			user.setUserID(session.getAttribute("UserID").toString());
			user = AuthorizationUtil.loadAuthorization(user);
			if(user.getPageAuthorization().isForgetPasswordAuthorized())
			{
				model.addAttribute("errorblocked",message);
				model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
		    	model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
		    	model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());	
				return "forgetPassword";			
			}
			else
			{
				return "not_authorized";
			}

		}
	}
	
	@RequestMapping(value = "/newpasswdfunction", method = RequestMethod.POST)
	public String newpassfunction(@ModelAttribute("userandotp") UserandOTP userandotp, Model model, HttpSession session,  HttpServletRequest request) throws Exception{
		
		OneTimePasswd otp = new OneTimePasswd((String)session.getAttribute("UserID"));
		String error=null;
		
		
		if(userandotp.getUser().getPassword()== null || userandotp.getUser().getPassword().isEmpty())
		{
			error= "Password must be entered.";
			model.addAttribute("error",error);
			return "NewPasswordSetup";
		}
		boolean b = CommonValidations.checkValidPassword(userandotp.getUser().getPassword());
		if(!b)
		{
			error= "Invalid password";
			model.addAttribute("error",error);
			return "NewPasswordSetup";
		}
		
		
		if(userandotp.getComfirmpassword()== null || userandotp.getComfirmpassword().isEmpty())
		{
			error= "Password must be entered.";
			model.addAttribute("error",error);
			return "NewPasswordSetup";
		}
		boolean c = CommonValidations.checkValidPassword(userandotp.getComfirmpassword());
		if(!c)
		{
			error= "Invalid password";
			model.addAttribute("error",error);
			return "NewPasswordSetup";
		}
		
		
		
		error= CommonValidations.validateStringNormal(userandotp.getOtpcode(), 6, error, "OTP ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "NewPasswordSetup";
		}
		
		
		otp.setUserId((String) session.getAttribute("UserID"));
		boolean bo =  otp.checkTheUserEnteredOTPCodeNoException(userandotp.getOtpcode());
		if (!bo)
		{
			error = "Expired or Invalid OTP.";
			model.addAttribute("error",error);
	        return "NewPasswordSetup";
		}
		
		else{
			
			if(userandotp.getUser().getPassword().equals(userandotp.getComfirmpassword()))
			{
				String password=userandotp.getUser().getPassword();
				password=dbhash.hash(password);
				userPasswordManager.saveUserPasswordInfo(session.getAttribute("UserID").toString(), password);
				User user = new User();
		        model.addAttribute("user", user);
				return "CheckUserID";
			}
			else{
				error="Passwords not matched.";
				model.addAttribute("error",error);
		        return "NewPasswordSetup";
			}
			
		}
	}
	
	
	
	@RequestMapping("/changecontactPage")
	public String changecPage(@ModelAttribute("user") User user, Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isContactChangeAuthorized())
		{
			return "ChangeContactInfo";			
		}
		else
		{
			return "not_authorized";
		}
	}
	
	@RequestMapping("/changesecureQAPage")
	public String changesQAPage(@ModelAttribute("user") User user, Model model){
		return "ChangeSecurityQ";
	}
	
	@RequestMapping("/addUser")
    public String rdraddUser(@ModelAttribute("user") User user,Model model, HttpSession session) {	
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAddUserAuthorized())
		{
			user.setUserID(null);
			return "addUser";		
		}
		else
		{
			return "not_authorized";
		}

    }
	
	@RequestMapping("/deleteUserPage")
    public String rdrdeleteFadd(@ModelAttribute("user") User user,Model model, HttpSession session) {
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDeleteUserPageAuthorized())
		{
			return "deleteUser";		}
		else
		{
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Delete User page");
			TransactionManager.createTransaction(t);
			return "not_authorized";
		}
    }
	@RequestMapping("/transferUserPage")
	public String transfU(@ModelAttribute("user") User user, Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isTransferUserAuthorized())
		{
			return "transferUser";		}
		else
		{
			return "not_authorized";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String handleRequest(HttpServletRequest request, HttpServletResponse response){
	   request.getSession().invalidate();
	   return "logoutPage";
	 }      
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteU(@ModelAttribute("user") User user, Model model, HttpSession session){
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		String error=null;
		boolean b = true;		
//userID
		error =CommonValidations.validateStringNormal(user.getUserID(), 15, error, "User ID",true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "deleteUser";
		}	
		b=UserManager.existedUserID(user.getUserID());
		if(!b)
		{
			error = "User ID does not exist.";
			model.addAttribute("error",error);
			return "deleteUser";
		}
		
				
		User user11 = new User();
		user11.setUserID(session.getAttribute("UserID").toString());
		
		
//checkUserRole
		User activeUser = UserManager.queryUser(session.getAttribute("UserID").toString());
		if(activeUser.getRoleID()<= user.getRoleID()){
			error="You are not authorized to delete this user";
			model.addAttribute("error",error);
			return "deleteUser";
		}
		

		UserManager.deleteuser(user.getUserID());
		Transaction t = new Transaction(TransactionType.DEL_USER_ACCT, null,
				null, session.getAttribute("UserID").toString(),
				user.getUserID(), 0.0, 0.0, "Transfer new user");
		return "deleteUser";

	}
	
	@RequestMapping(value = "/transferuser", method = RequestMethod.POST)
	public String transfUse(@ModelAttribute("user") User user, Model model, HttpSession session, HttpServletRequest request)
	{
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		String error=null;
		boolean b = true;
//userID
		error =CommonValidations.validateStringNormal(user.getUserID(), 15, error, "User ID",true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "transferUser";
		}	
		b=UserManager.existedUserID(user.getUserID());
		if(!b)
		{
			error = "User ID does not exist.";
			model.addAttribute("error",error);
			return "transferUser";
		}
		model.addAttribute("error",error);
		//where to transfer
		String activeUserId = session.getAttribute("UserID").toString();
		User activeUser = UserManager.queryUser(activeUserId);

		//If manager
		if(activeUser.getRoleID() == 5) {
			// Check if user in same department
			List<User> deptUsers = UserManager.getUsersByDept(activeUser.getDeptID());
			boolean userValid = false;
			for (User u : deptUsers) {
				if (u.getUserID().equals(user.getUserID())) {
					userValid = true;
					break;
				}
			}
			
			// Check User role
			if (userValid) {
				User tfrUser = UserManager.queryUser(user.getUserID());
				if (tfrUser.getRoleID() < activeUser.getRoleID()) {
					UserManager.transferuser(user.getUserID(), user.getDeptID());
					Transaction t = new Transaction(
							TransactionType.TFR_USER_ACCT, null, null,
							activeUser.getUserID(), user.getUserID(), 0.0, 0.0,
							"Transfer new user");
				} else {
					error = "You are not allowed to transfer this User";
					model.addAttribute("error",error);
				}
			} else {
				error = "You are not allowed to transfer this User";
				model.addAttribute("error",error);
			}
		}
		
		// If corp
		else if (activeUser.getRoleID() == 6) {

			boolean status = UserManager.transferuser(user.getUserID(), user.getDeptID());
			Transaction t = new Transaction(
					TransactionType.TFR_USER_ACCT, null, null,
					activeUser.getUserID(), user.getUserID(), 0.0, 0.0,
					"Transfer new user");
			if (! status) {
				error = "Not a valid user";
				model.addAttribute("error",error);
			}
		}
		
		return "transferUser";
	}
	
	@RequestMapping(value = "/changecontactin", method = RequestMethod.POST)
	public String changeCon(@ModelAttribute("user") User user, Model model,HttpSession session){
		String error=null;
		boolean b = true;
		System.out.println("user.getContact() = "+user.getContact());
		String x = String.valueOf(user.getContact());
		System.out.println("x = "+x);
		String phoneNum =user.getContact();
		if(user.getContact() ==null || user.getContact().isEmpty())
		{
			error = "Please Enter the Phone Number.";
			model.addAttribute("error",error);
			return "ChangeContactInfo";
		}
		b = CommonValidations.validatePhoneNumber(user.getContact());
		if(!b){
			error = "Invalid Phone Number.";
			model.addAttribute("error",error);
			return "ChangeContactInfo";
		}
		
		if(user.getAddress() ==null || user.getAddress().isEmpty())
		{
			error = "Please Enter the Address.";
			model.addAttribute("error",error);
			return "ChangeContactInfo";
		}
		b = CommonValidations.validateAddress(user.getAddress());
		if(!b){
			error = "Invalid Address.";
			model.addAttribute("error",error);
			return "ChangeContactInfo";
		}
		
		
		
		UserManager.changeContactFunction((String)session.getAttribute("UserID"), user.getContact(), user.getAddress());
//		model.addAttribute("Contact",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getContact());
//		model.addAttribute("Address",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getAddress());
		model.addAttribute("Emailid", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getEmailid());
		model.addAttribute("Dob", UserManager.retrieiveUser((String)session.getAttribute("UserID")).getDOB());
		model.addAttribute("Contact",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getContact());
		model.addAttribute("Address",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getAddress());
		model.addAttribute("Firstname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getFirstname());
		model.addAttribute("Lastname",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getLastname());
		model.addAttribute("SecureQ1",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ1());
		model.addAttribute("SecureQ2",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ2());
		model.addAttribute("SecureQ3",UserManager.retrieiveUser((String)session.getAttribute("UserID")).getSecureQ3());		
		System.out.println(UserManager.retrieiveUser((String)session.getAttribute("UserID")).getDOB());
		return "Profile";
	}

	@RequestMapping("/userinfo")
    public String userInfo(Model model) {
		User user = new User();
        model.addAttribute("user", user);
        return "userinfo";
    }
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user, Model model,HttpServletRequest request, HttpSession session) {
		String message = "";    	
//		System.out.println(user.getSecureQ1());
//		System.out.println(user.getSecureQ2());
//		System.out.println(user.getSecureQ3());
//		System.out.println(user.getSecureA1());
//		System.out.println(user.getSecureA2());
//		System.out.println(user.getSecureA3());
		
		
		String error=null;
		boolean b = true;
		
/*//roleID
		RoleType type = (RoleType) request.getAttribute("role");
		user.setRoleID(type.getValue());
		
//DepartmentID
		DepartmentEnum dep = (DepartmentEnum) request.getAttribute("dept");
		user.setDeptID(dep.getValue());*/
		if(session == null || session.getAttribute("UserID") == null)
			return "not_authorized";
		
		User activeUser = UserManager.queryUser(session.getAttribute("UserID").toString());
		if (activeUser.getRoleID() < user.getRoleID()) {
			error = "Cannot create a higher level user";
			model.addAttribute("error",error);
			return "addUser";
		}
		
		if (user.getRoleID() < 3 && user.getDeptID() != 0)
		{
			error = "Role - Departent combination not valid";
			model.addAttribute("error",error);
			return "addUser";
		}
		
		if (user.getRoleID() > 2 && user.getDeptID() == 0)
		{
			error = "Role - Departent combination not valid";
			model.addAttribute("error",error);
			return "addUser";
		}
		
//emailID
		if(user.getEmailid() ==null || user.getEmailid().isEmpty())
		{
			error = "Please Enter the Email Address.";
			model.addAttribute("error",error);
			return "addUser";
		}
		b = CommonValidations.validateEmailFormat(user.getEmailid());
		if(!b){
			error = "Invalid Email.";
			model.addAttribute("error",error);
			return "addUser";
		}
		
//contact
		if(user.getContact() ==null || user.getContact().isEmpty())
		{
			error = "Please Enter the Phone Number.";
			model.addAttribute("error",error);
			return "addUser";
		}
		b = CommonValidations.validatePhoneNumber(user.getContact());
		if(!b){
			error = "Invalid Phone Number.";
			model.addAttribute("error",error);
			return "addUser";
		}
		
//address
		if(user.getAddress() ==null || user.getAddress().isEmpty())
		{
			error = "Please Enter the Address.";
			model.addAttribute("error",error);
			return "addUser";
		}
		b = CommonValidations.validateAddress(user.getAddress());
		if(!b){
			error = "Invalid Address.";
			model.addAttribute("error",error);
			return "addUser";
		}
		
//seqa1	
		error= CommonValidations.validateStringNormal(user.getSecureA1(), 40, error, "Security answer 1 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);

			return "addUser";
		}
//seqa2		
		error= CommonValidations.validateStringNormal(user.getSecureA2(), 40, error, "Security answer 2 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);

			return "addUser";
		}
		
//seqa3	
		error= CommonValidations.validateStringNormal(user.getSecureA3(), 40, error, "Security answer 3 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);

			return "addUser";
		}
//seq1
		error= CommonValidations.validateStringNormal(user.getSecureQ1(), 40, error, "Security question 1 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);

			return "addUser";
		}		

//seq2
		error= CommonValidations.validateStringNormal(user.getSecureQ2(), 40, error, "Security question 2 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "addUser";
		}					
//seq3
		error= CommonValidations.validateStringNormal(user.getSecureQ3(), 40, error, "Security question 3 ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "addUser";
		}	

//firstname
		error= CommonValidations.validateStringNormal(user.getFirstname(), 30, error, "Firstname ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "addUser";
		}	
//lastname
		error= CommonValidations.validateStringNormal(user.getLastname(), 30, error, "Lastname ", true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "addUser";
		}					
//userid	
		error =CommonValidations.validateStringNormal(user.getUserID(), 15, error, "User ID",true);
		if(!error.isEmpty())
		{
			model.addAttribute("error",error);
			return "addUser";
		}	
		b=UserManager.existedUserID(user.getUserID());
		if(b)
		{
			error = "User ID exists, Change to another one.";
			model.addAttribute("error",error);
			return "addUser";
		}
				
//dob
		if(user.getDOB() ==null || user.getDOB().isEmpty())
		{
			error = "Date of Birth must be entered.";
			model.addAttribute("error",error);
			return "addUser";
		}
		b = CommonValidations.validateDateOfBirth(user.getDOB());
		if(!b){
			error = "Invalid date of birth.";
			model.addAttribute("error",error);
			return "addUser";
		}

		String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdIzOkSAAAAAM-qhGSfpfAsRKKw0xcAUUmSdTyL");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (!reCaptchaResponse.isValid()) {
        	model.addAttribute("error","Invalid Captcha Value");
        	return "addUser";
        } 

		
//department check
		
		if(user.getRoleID()<3)
		{
			user.setDeptID(0);
		}
			
		
		

    	message = UserManager.createUser(user.getFirstname(), user.getLastname(), user.getUserID(), user.getEmailid(),
    			user.getAddress(), user.getContact(), user.getDOB(),user.getSecureQ1(), user.getSecureQ2(),
    			user.getSecureQ3(), user.getSecureA1(), user.getSecureA2(), user.getSecureA3(), 
    			user.getIdtype(), user.getIdNo(), user.getDeptID(), user.getRoleID());
    	userPasswordManager.createUserPasswordTableInfo(user.getUserID());
    	
    	RoleType roleType = AuthorizationUtil.getEnumValueOfRole(user.getRoleID());
    	//create authorization
    	AuthorizationUtil.createAuthorizationForUser(user, roleType);
    	
    	PkiUtils p = new PkiUtils();
		p.createUserKeys(user.getUserID());
    	
		Transaction t = new Transaction(
				TransactionType.ADD_USER_ACCT, null, null,
				activeUser.getUserID(), user.getUserID(), 0.0, 0.0,
				"Created new user");
		TransactionManager.createTransaction(t);
		
    	model.addAttribute("message", message);
    	String referer = request.getHeader("Referer");
    	System.out.println("referer = "+referer);
        //return "redirect:"+referer;
    	return "addUser";
    }
	
	@RequestMapping("/External_TXN")
    public ModelAndView extTxn(HttpSession session) {
		if(session == null || session.getAttribute("UserID") == null)
			return new ModelAndView("not_authorized");
		String userId = session.getAttribute("UserID").toString();
		User user = new User();
		user.setUserID(userId);
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isExternalTransactionAuthorized()) {
			//List<Transaction> tList = TransactionManager.getAllTransactions();
			List<Transaction> tList = TransactionManager.getExtTransactions(userId);
			ModelAndView mv = new ModelAndView("/External_TXN");
			mv.addObject("extTxnList", tList);
	        return mv;
		} else {
			Transaction t = new Transaction(
					TransactionType.UNAUTHORIZED_ACCESS, null, null,
					user.getUserID(), null, 0.0, 0.0,
					"Unauthorized access to Customer Transaction page");
			TransactionManager.createTransaction(t);
			return new ModelAndView("not_authorized");
		}
    }
	
	@RequestMapping("/Internal_TXN")
    public ModelAndView intTxn(HttpSession session) {
		if(session == null || session.getAttribute("UserID") == null)
			return new ModelAndView("not_authorized");
		Transaction t = null;
		String userId = session.getAttribute("UserID").toString();
		User user = new User();
		user.setUserID(userId);
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isInternalTransactionAuthorized()) {
			List<Transaction> tList = TransactionManager.getIntTransactions(userId);
			t = new Transaction(TransactionType.VIEW_INT_TXN, null, null, userId, null, 0.0, 0.0, "Internal Transactions viewed");
			TransactionManager.createTransaction(t);
			ModelAndView mv = new ModelAndView("/Internal_TXN");
			mv.addObject("intTxnList", tList);
	        return mv;
		} else {
			t = new Transaction(TransactionType.UNAUTHORIZED_ACCESS, null,
					null, userId, null, 0.0, 0.0,
					"Unauthorized access to Internal Txn page");
			TransactionManager.createTransaction(t);
			return new ModelAndView("not_authorized");
		}
    }
	
	@RequestMapping("/System_Log")
    public ModelAndView sysLog(HttpSession session) {
		if(session == null || session.getAttribute("UserID") == null)
			return new ModelAndView("not_authorized");
		String userid = session.getAttribute("UserID").toString();
		User user = new User();
		user.setUserID(userid);
		user = AuthorizationUtil.loadAuthorization(user);
		Transaction t = null;
		if(user.getPageAuthorization().isSystemLogAuthorized())
		{
			List<Transaction> tList = TransactionManager.getSystemLog();
			t = new Transaction(TransactionType.VIEW_SYS_LOG, null, null, userid, null, 0.0, 0.0, "System Log viewed");
			TransactionManager.createTransaction(t);
			/*Transaction t = new Transaction(TransactionType.VIEW_SYS_LOG, null, null, userid, null, 0.0, "System Log was viewed");
			TransactionManager.createTransaction(t);
			t = new Transaction(TransactionType.CREDIT, null, null, null, "qingyunl", 50.0, "50.0$ credited");
			TransactionManager.createTransaction(t);
			t = new Transaction(TransactionType.DEBIT, null, null, "satya", null, 123.0, "123.0$ debited");
			TransactionManager.createTransaction(t);
			t = new Transaction(TransactionType.USR_ROLE_CHANGED, null, null, userid, "satya", 0.0, "User Role Changed to Merchant");
			TransactionManager.createTransaction(t);
			t = new Transaction(TransactionType.PAYMENT_REQUESTED, null, null, "satya", "qingyunl", 12.0, "12$ payment requested");
			TransactionManager.createTransaction(t);*/
			ModelAndView mv = new ModelAndView("/System_Log");
			mv.addObject("sysLogList", tList);
	        return mv;
		}
		else {
			t = new Transaction(TransactionType.UNAUTHORIZED_ACCESS, null,
					null, userid, null, 0.0, 0.0,
					"Unauthorized access to System Log page");
			TransactionManager.createTransaction(t);
			return new ModelAndView("not_authorized");
		}
    }
	
	@RequestMapping("/notifications")
    public ModelAndView notifications(HttpSession session) {	
		if(session == null || session.getAttribute("UserID") == null)
			return new ModelAndView("not_authorized");
		String userId = session.getAttribute("UserID").toString();
		List<Notification> notList = NotificationManager.getNotifications(userId);
		ModelAndView mv = new ModelAndView("/notifications");
		NotificationList nList = new NotificationList();
		nList.setNotifications(notList);
		mv.addObject("notificationList", nList);
        return mv;
    }
	
	@RequestMapping(value = "/notificationRequest", method = RequestMethod.POST)
    public String notificationRequest(@ModelAttribute("notificationList") NotificationList nList, HttpServletRequest request) {	
		//List<Notification> nList = NotificationManager.getNotifications("kdvyas");
		//ModelAndView mv = new ModelAndView("/notifications");
		//mv.addObject("notificationList", nList);
		for (Notification n : nList.getNotifications()) {
			if ("A".equals(n.getStatus()) || "D".equals(n.getStatus()))
			NotificationManager.updateNotification(n);
		}
		String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
	
	@RequestMapping(value = "/intTxnRequest", method = RequestMethod.POST)
    public String intTxnRequest(@RequestParam(value = "selectedId", required=true) int[] txnIds, HttpServletRequest request, HttpSession session) {
		if(session == null || session.getAttribute("UserID") == null) {
			return "not_authorized";
		}
		String userId = session.getAttribute("UserID").toString();
        for (int txnId : txnIds) {
        	// get transaction for both the parties
        	Transaction t = TransactionManager.getTransaction(txnId);
        	if (t.getFromUserId() != null && !t.getFromUserId().equals("")) {
				Notification n = new Notification(t.getFromUserId(), userId, "N", txnId);
				NotificationManager.createNotification(n);
        	}
        	if (t.getToUserId() != null && !t.getToUserId().equals("")) {
				Notification n = new Notification(t.getToUserId(), userId, "N", txnId);
				NotificationManager.createNotification(n);
        	}
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}
