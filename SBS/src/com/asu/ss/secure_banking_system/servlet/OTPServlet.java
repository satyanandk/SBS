package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.model.TranConfResult;
import com.asu.ss.secure_banking_system.model.TransferService;
import com.asu.ss.secure_banking_system.model.TypeOfTransfer;
import com.asu.ss.secure_banking_system.validation.CommonValidations;

/**
 * Servlet implementation class OTPServlet
 */
@WebServlet(description = "OTP handling Servlet", urlPatterns = { "/OTPServlet" })
public class OTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OTPServlet() {
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
		TranConfResult tranConfResult;
		request.removeAttribute("exception");
		//session.removeAttribute("exception1");
		
		TransferService transferService = (TransferService)session.getAttribute("transferService");
		try
		{
			request.removeAttribute("exception");
			String otpCode = request.getParameter("otpCode");
			if(otpCode == null || otpCode.isEmpty())
				throw new Exception("Enter the OTP Code!");
			
			if(!CommonValidations.valiateSpecialCharacters(otpCode))
				throw new Exception("Special characters are not allowed in OTP Code!");
			
			CommonValidations.validateLength(otpCode, 6, "OTP Code");
			
			String userID = (String)session.getAttribute("UserID");
			System.out.println("SATYA Page OTP userID = "+userID);
			
			OneTimePasswd otp = new OneTimePasswd(userID);
			otp.checkTheUserEnteredOTPCode(otpCode);
			
			
			TypeOfTransfer transferInd = transferService.getTypeOfTrans();
			if((transferInd == TypeOfTransfer.DEBIT) || (transferInd == TypeOfTransfer.CREDIT))
			{
				transferService.DebitOrCreditAccount();
				tranConfResult = transferService.getTranConfResult();
				
			}
			else if(transferInd == TypeOfTransfer.TRANSFER)
			{
				transferService.fundTransferBetweenAccounts();
				tranConfResult = transferService.getTranConfResult();
				
			}
			else
			{
				throw new Exception("Invalid Operation!");
			}
			session.setAttribute("tranConfResult", tranConfResult);
			session.setAttribute("resPageInd", "TRAN_CONF");
			request.getRequestDispatcher("resultpagepage.html").forward(request, response);
			
		}
		catch(Exception e)
		{
			request.setAttribute("exception", e.getMessage());
			//session.setAttribute("exception1", e.getMessage());
			System.out.println("exception occered satya : "+e.getMessage());
			
				request.getRequestDispatcher("FinancialOTPPage.html").forward(request, response);
			
		}
		
		
	}

}
