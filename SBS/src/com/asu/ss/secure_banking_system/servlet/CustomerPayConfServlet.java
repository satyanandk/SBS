package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.MerchantPayConfService;
import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.validation.CommonValidations;

/**
 * Servlet implementation class CustomerPayConfServlet
 */
@WebServlet("/CustomerPayConfServlet")
public class CustomerPayConfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerPayConfServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		try {
			request.removeAttribute("exception");
			String accountNo = (String) request.getParameter("accountNo");
			
			if (accountNo == null||accountNo.isEmpty())
				throw new Exception("Enter account number!");
			if(!CommonValidations.valiateSpecialCharacters(accountNo))
				throw new Exception("Special characters are not allowed in the Account No!");
			CommonValidations.validateLength(accountNo, 10, "Account No");
			
			String merchantUserID = (String)session.getAttribute("Payment.mer");
			if(merchantUserID==null || merchantUserID.isEmpty())
				throw new Exception("URL tampered!");
			
			String amount = (String)session.getAttribute("Payment.amount");
			if((amount==null)||amount.isEmpty())
				throw new Exception("URL tampered!");
			
			double dAmount = Double.valueOf(amount);
			
			String customerUserID = (String)session.getAttribute("Payment.CusUserID");
			if(customerUserID==null || customerUserID.isEmpty())
				throw new Exception("URL tampered!");
			
			
			String otpKey = (String)request.getParameter("otpKey");
			if(otpKey==null || otpKey.isEmpty())
				throw new Exception("OTP Key must be entered!");
			
			if(!CommonValidations.valiateSpecialCharacters(otpKey))
				throw new Exception("Special characters are not allowed in the OTP Code!");
			CommonValidations.validateLength(otpKey, 6, "OTP Code");
			
			String payID = (String)session.getAttribute("Payment.payID");
			System.out.println("Merchant Pay Conf Page accountNo = "+accountNo);
			System.out.println("Merchant Pay Conf Page merchantUserID = "+merchantUserID);
			System.out.println("Merchant Pay Conf Page dAmount = "+dAmount);
			System.out.println("Merchant Pay Conf Page customerUserID = "+customerUserID);
			System.out.println("Merchant Pay Conf Page PayID = "+payID);
			//OneTimePasswd otp = new OneTimePasswd(CustomerUserID);
			OneTimePasswd otp = new OneTimePasswd(customerUserID);
			otp.checkTheUserEnteredOTPCode(otpKey);
			
			MerchantPayConfService merPayConfService = new MerchantPayConfService(merchantUserID,customerUserID,dAmount,accountNo,payID);
			merPayConfService.confirmPayment();
			
			session.setAttribute("tranConfResult", merPayConfService.getTranConfResult());
			session.setAttribute("resPageInd", "TRAN_CONF");
			request.getRequestDispatcher("resultpagepage.html").forward(request, response);
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
				System.out.println("Exception at merchant payment confirmation page"+e.getMessage());
				e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("PaymentConfPage.html").forward(request, response);
		}
	}

}
