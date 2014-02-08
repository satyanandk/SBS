package com.sbs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.model.PageAuthorizationEntity;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;
import com.asu.ss.secure_banking_system.validation.CommonValidations;
import com.sbs.model.combined.UserandOTP;
import com.sbs.model.notification.Notification;
import com.sbs.model.notification.NotificationList;
import com.sbs.model.notification.NotificationManager;
import com.sbs.model.transaction.Transaction;
import com.sbs.model.transaction.TransactionManager;
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
		user.setUserID(session.getAttribute("UserID").toString());
        model.addAttribute("user", user);
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isRequestRoleAuthorized())
		{
	        return "request_role";	
		}
		else
		{
			return "not_authorized";
		}

    }

	@RequestMapping("/process_taa")
    public String processtaa(Model model, HttpSession session) {	
		User user = new User();
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
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAssignTAAAuthorized())
		{
	        return "assign_taa";		
	    }
		else
		{
			return "not_authorized";
		}		
    }	
	@RequestMapping("/profilePage")
    public String profilePa(Model model, HttpSession session) {	
		User user = new User();
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
    	
    	user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
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
        switch(UserManager.retrieiveUser((String)session.getAttribute("UserID")).getRoleID())
		{
        
		case 1: 
			return "welcomeExternal";
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
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAdminNotificationAuthorized())
		{
			return "admin_notification";
		}
		else
		{
			return "not_authorized";
		}
    }

	@RequestMapping("/not_authorized")
    public String notAuthorized(Model model, HttpSession session) {	
		
			return "not_authorized";
    }
	
	@RequestMapping("/creditPage")
	public String creditPageFunction(Model model, HttpSession session){
		if(session.getAttribute("UserID").toString() == null)
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
			return "not_authorized";
		}


	}
	
	@RequestMapping("/TransferPage")
	public String TransferPageFunction(Model model, HttpSession session){
		if(session.getAttribute("UserID").toString() == null)
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
			return "not_authorized";
		}
	}
	
	@RequestMapping("/errorPagePage")
	public String errorPageFunction(Model model){
		return "errorPage";
	}
	
	@RequestMapping("/RequestPaymentPage")
	public String RequestPaymentPageFunction(Model model, HttpSession session){
		if(session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isRequestPaymentAuthorized())
		{
			return "RequestPayment";
		}
		else
		{
			return "not_authorized";
		}	

	}
	
	@RequestMapping("/createAccountPage")
	public String createAccountPageFunction(Model model, HttpSession session){
		if(session.getAttribute("UserID").toString() == null)
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
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isAssignRoleAuthorized())
		{
	        return "assign_role";
	        }
		else
		{
			return "not_authorized";
		} 

    }
	
	@RequestMapping("/resultpagepage")
    public String resultpagepageFunction(Model model, HttpSession session) {
		if(session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		User user = new User();
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isResultPageAuthorized())
		{
	        return "resultPage";
	        }
		else
		{
			return "not_authorized";
		} 
    }
	
	
	@RequestMapping("/FinancialOTPPage")
    public String FinancialOTPPageFunction(Model model, HttpSession session) {	
		User user = new User();
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
		if(session.getAttribute("UserID").toString() == null)
			return "not_authorized";
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDebitAuthorized())
		{
	        return "Debit";
	    }
		else
		{
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
			session.setMaxInactiveInterval(3000000);
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
				userPasswordManager.saveUserPasswordInfo((String) session.getAttribute("UserID"), userandotp.getUser().getPassword());
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
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDeleteUserPageAuthorized())
		{
			return "deleteUser";		}
		else
		{
			return "not_authorized";
		}
    }
	@RequestMapping("/transferUserPage")
	public String transfU(@ModelAttribute("user") User user, Model model, HttpSession session){
		user.setUserID(session.getAttribute("UserID").toString());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isTransferAuthorized())
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
		user.setUserID(session.getAttribute("UserID").toString());
		UserManager.deleteuser(user.getUserID());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDeleteUserPageAuthorized())
		{
			return "result";		
		}
		else
		{
			return "not_authorized";
		}

	}
	
	@RequestMapping(value = "/transferuser", method = RequestMethod.POST)
	public String transfUse(@ModelAttribute("user") User user, Model model, HttpSession session)
	{
		user.setUserID(session.getAttribute("UserID").toString());
		UserManager.deleteuser(user.getUserID());
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isDeleteUserPageAuthorized())
		{
			UserManager.transferuser(user.getUserID(), user.getDeptID());
			return "result";
		}
		else
		{
			return "not_authorized";
		}
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
		
//email		
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
		b=UserManager.duplicatedUserID(user.getUserID());
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
			model.addAttribute("errorblocked",error);
			return "addUser";
		}
		

    	message = UserManager.createUser(user.getFirstname(), user.getLastname(), user.getUserID(), user.getEmailid(),
    			user.getAddress(), user.getContact(), user.getDOB(),user.getSecureQ1(), user.getSecureQ2(),
    			user.getSecureQ3(), user.getSecureA1(), user.getSecureA2(), user.getSecureA3(), 
    			user.getIdtype(), user.getIdNo(), user.getDeptID(), user.getRoleID());
    	userPasswordManager.createUserPasswordTableInfo(user.getUserID());
    	
    	RoleType roleType = AuthorizationUtil.getEnumValueOfRole(user.getRoleID());
    	//create authorization
    	AuthorizationUtil.createAuthorizationForUser(user, roleType);
    	
    	model.addAttribute("message", message);
    	String referer = request.getHeader("Referer");
        return "redirect:"+referer;
    }
	
	@RequestMapping("/External_TXN")
    public ModelAndView extTxn(HttpSession session) {
		String userId = session.getAttribute("UserID").toString();
		//List<Transaction> tList = TransactionManager.getAllTransactions();
		List<Transaction> tList = TransactionManager.getExtTransactions(userId);
		ModelAndView mv = new ModelAndView("/External_TXN");
		mv.addObject("extTxnList", tList);
        return mv;
    }
	
	@RequestMapping("/Internal_TXN")
    public ModelAndView intTxn(HttpSession session) {
		String userId = session.getAttribute("UserID").toString();
		List<Transaction> tList = TransactionManager.getIntTransactions();
		ModelAndView mv = new ModelAndView("/Internal_TXN");
		mv.addObject("intTxnList", tList);
        return mv;
    }
	
	@RequestMapping("/System_Log")
    public ModelAndView sysLog(HttpSession session) {
		String userid = session.getAttribute("UserID").toString();
		User user = new User();
		user.setUserID(userid);
		user = AuthorizationUtil.loadAuthorization(user);
		if(user.getPageAuthorization().isSystemLogAuthorized())
		{
		List<Transaction> tList = TransactionManager.getSystemLog();
		ModelAndView mv = new ModelAndView("/System_Log");
		mv.addObject("sysLogList", tList);
        return mv;
		}
		else 
			return new ModelAndView("not_authorized");
    }
	
	@RequestMapping("/notifications")
    public ModelAndView notifications(HttpSession session) {	
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
    public String intTxnRequest(@RequestParam(value = "selectedId", required=true) int[] txnIds, HttpServletRequest request) {
        for (int txnId : txnIds) {
        	// get transaction for both the parties
        	Transaction t = TransactionManager.getTransaction(txnId);
        	if (t.getFromUserId() != null && !t.getFromUserId().equals("")) {
				Notification n = new Notification(t.getFromUserId(), "requester", "N", txnId);
				NotificationManager.createNotification(n);
        	}
        	if (t.getToUserId() != null && !t.getToUserId().equals("")) {
				Notification n = new Notification(t.getToUserId(), "requester", "N", txnId);
				NotificationManager.createNotification(n);
        	}
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}
