package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.MerchantPayConfService;
import com.asu.ss.secure_banking_system.model.MerchantRequestEntity;
import com.asu.ss.secure_banking_system.model.MerchantService;
import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.validation.CommonValidations;

/**
 * Servlet implementation class CustomerPaymentServlet
 */
@WebServlet("/CustomerPaymentServlet")
public class CustomerPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerPaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		try
		{
			request.removeAttribute("exception");
			System.out.println("Entered Customer Payment Servlet.");
			MerchantService mer = new MerchantService();
			MerchantRequestEntity mReqEnt = new MerchantRequestEntity();
			HttpSession session = request.getSession(true);
			
			
			/*String merchantUserID = (String)request.getParameter("m");
			if(merchantUserID.isEmpty())
				throw new Exception("URL Tampered");
		
			String amount = (String)request.getParameter("amount");
			if(amount.isEmpty())
					throw new Exception("URL Tampered");
		
		String remarks = (String)request.getParameter("remarks");
		if(remarks.isEmpty())
			throw new Exception("URL Tampered");
		
		String userID = (String)request.getParameter("cus");
		if(userID.isEmpty())
			throw new Exception("URL Tampered");*/
			
			String payID = (String)request.getParameter("payID");
			if(payID == null||payID.isEmpty())
				throw new Exception("URL Tampered!!!");
			if(!CommonValidations.valiateSpecialCharacters(payID))
			{
				throw new Exception("URL Tampered!!!");
			}
			
			
			long longPayID = Long.valueOf(payID);
			mReqEnt = (MerchantRequestEntity)mer.getPaymentDetails(longPayID);
			
			System.out.println("mReqEnt Customer User ID"+mReqEnt.getCustomerUserID());
			System.out.println("mReqEnt "+mReqEnt.getdAmount());
			System.out.println("mReqEnt Customer User ID Remarks"+mReqEnt.getRemarks());
			
		MerchantPayConfService ser = new MerchantPayConfService(payID);
		if(1==ser.isPaymentDoneAlready())
			throw new Exception("URL Tampered!");
		if(2==ser.isPaymentDoneAlready())
			throw new Exception("Payment already done!!");
		
		/*Change here by giving userID*/
		OneTimePasswd otp = new OneTimePasswd(mReqEnt.getCustomerUserID());
		otp.insertOTPCodeForUser();
		
		session.setAttribute("Payment.mer", mReqEnt.getMerchantUserID());
		session.setAttribute("Payment.amount", mReqEnt.getdAmount()+"");
		session.setAttribute("Payment.remarks", mReqEnt.getRemarks());
		session.setAttribute("Payment.CusUserID", mReqEnt.getCustomerUserID());
		session.setAttribute("Payment.payID", payID);
		
			request.getRequestDispatcher("PaymentConfPage.html").forward(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in Customer Payment Page :"+e.getMessage());
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("errorPagePage.html").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
