package com.asu.ss.secure_banking_system.servlet;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
//import org.apache.catalina.Session;
import org.hibernate.Transaction;

import com.asu.ss.secure_banking_system.model.AccountEntity;
import com.asu.ss.secure_banking_system.model.OneTimePasswd;
import com.asu.ss.secure_banking_system.model.SessionFactoryUtil;
import com.asu.ss.secure_banking_system.model.TranConfResult;
import com.asu.ss.secure_banking_system.model.TransferService;
import com.asu.ss.secure_banking_system.model.TypeOfTransfer;
import com.asu.ss.secure_banking_system.validation.CommonValidations;

/**
 * Servlet implementation class DebitAndCreditServlet
 */
@WebServlet("/DebitAndCreditServlet")
public class DebitAndCreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DebitAndCreditServlet() {
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
		request.removeAttribute("exception");
		HttpSession session = request.getSession(true);
		//session.removeAttribute("exception1");
		
		TypeOfTransfer transferInd = null;
		String pageInd = request.getParameter("pageInd");
		System.out.println("Page = "+pageInd);
		
		if(pageInd.equalsIgnoreCase("credit"))
			transferInd = TypeOfTransfer.CREDIT;
		else if(pageInd.equalsIgnoreCase("debit"))
			transferInd = TypeOfTransfer.DEBIT;
		RequestDispatcher rd = request.getRequestDispatcher("FinancialOTPPage.html");
		try {
		String account = request.getParameter("accountNo");
		if(account==null ||account.isEmpty())
			throw new Exception("Enter the Account No!");
		
		if(!CommonValidations.valiateSpecialCharacters(account))
			throw new Exception("Special characters are not allowed for Account No!");
		System.out.println("account number = "+account);
		
		CommonValidations.validateLength(account, 15, "Account No");
		String amount = request.getParameter("amount");
		if(amount == null||amount.isEmpty())
			throw new Exception("Enter the amount!");
		
		double dAmount = Double.valueOf(request.getParameter("amount"));
		System.out.println("amount = "+dAmount);
		
		
	
		String userID = (String)session.getAttribute("UserID");
		System.out.println("SATYA Page DEBIT userID = "+userID);
		//AccountEntity accEnt;
		/*TranConfResult tranConfResult;*/
		TransferService transferService = new TransferService(account,transferInd, dAmount, userID);
		
		session.setAttribute("transferService", transferService);
		session.setAttribute("pageInd", pageInd);
		OneTimePasswd otp = new OneTimePasswd(userID);
		otp.insertOTPCodeForUser();
		
		/*transferService.DebitOrCreditAccount();
		tranConfResult = transferService.getTranConfResult();
		request.setAttribute("tranConfResult", tranConfResult);*/
		session.setAttribute("resPageInd", "TRAN_CONF");
		rd.forward(request, response);
		
		}
		catch(Exception e)
		{
			request.setAttribute("exception", e.getMessage());
			//session.setAttribute("exception1", e.getMessage());
			System.out.println("exception occered satya : "+e.getMessage());
			if(transferInd == TypeOfTransfer.CREDIT)
				request.getRequestDispatcher("creditPage.html").forward(request, response);
			else if(transferInd == TypeOfTransfer.DEBIT)
				request.getRequestDispatcher("DebitPage.html").forward(request, response);
		}
		
		/*try {*/
			/*tx = session.beginTransaction();
			accEnt = new AccountEntity();
			
			accEnt.setAccountID("account2");
			accEnt.setAcctName("Software Sec2");
			accEnt.setUserID("user2");
			accEnt.setAcctBalance(3000);
			System.out.println("check here");
			session.save(accEnt);
			
			tx.commit();*/
			
		/*}
		catch(Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}*/
		
		
		
		
		
		
	}

}
