package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
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
 * Servlet implementation class TransferServlet
 */
@WebServlet(description = "Transfer money between Accounts", urlPatterns = { "/TransferServlet" })
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
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

		TranConfResult tranConfResult;
		TransferService transferService;
		request.removeAttribute("exception");
		HttpSession session = request.getSession(true);
		try
		{
			String fromAccount = request.getParameter("fromAccount");
			if(fromAccount ==null ||fromAccount.isEmpty())
				throw new Exception("From Account must be entered!");
			
			if(!CommonValidations.valiateSpecialCharacters(fromAccount))
				throw new Exception("Special characters are not allowed in From Account!");
			
			CommonValidations.validateLength(fromAccount, 15, "From Account");
			
			String toAccount = request.getParameter("toAccount");
			if(toAccount == null || toAccount.isEmpty())
				throw new Exception("To Account must be entered!");
			
			if(!CommonValidations.valiateSpecialCharacters(toAccount))
				throw new Exception("Special characters are not allowed in To Account!");
			
			CommonValidations.validateLength(toAccount, 15, "To Account");
			
			String amount = request.getParameter("amount");
			if(amount == null || amount.isEmpty())
				throw new Exception("Amount must be entered!");
			
			double dAmount = Double.valueOf(amount);
			
			if(fromAccount.equalsIgnoreCase(toAccount))
				throw new Exception("From Account and To Account must be different!");
			
			String pageInd = request.getParameter("pageInd");
			System.out.println("Page = "+pageInd);
			String userID = (String)session.getAttribute("UserID");
			System.out.println("SATYA Page TRANSFER userID = "+userID);
			
			transferService = new TransferService(fromAccount,toAccount,TypeOfTransfer.TRANSFER,dAmount,userID);
			session.setAttribute("transferService", transferService);
			session.setAttribute("pageInd", pageInd);
			OneTimePasswd otp = new OneTimePasswd(userID);
			otp.insertOTPCodeForUser();
			
			/*transferService.fundTransferBetweenAccounts();
			
			tranConfResult = transferService.getTranConfResult();
			session.setAttribute("tranConfResult", tranConfResult);
			session.setAttribute("resPageInd", "TRAN_CONF");*/
			request.getRequestDispatcher("FinancialOTPPage.html").forward(request, response);
			
		}
		catch(Exception e)
		{
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("TransferPage.html").forward(request, response);
		}
	}

}
