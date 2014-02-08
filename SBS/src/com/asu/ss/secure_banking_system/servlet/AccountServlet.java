package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.AccountEntity;
import com.asu.ss.secure_banking_system.model.AccountService;
import com.asu.ss.secure_banking_system.validation.CommonValidations;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServlet() {
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
		try {
		request.removeAttribute("exception");
		HttpSession session = request.getSession(true);
		
		String userID = (String)session.getAttribute("UserID");
		
		String accountNo = request.getParameter("accountNo");
		System.out.println("user ID = "+userID);
		String errorMessage = new String();
		errorMessage = CommonValidations.validateStringNormal(accountNo, 10, errorMessage, "Account No", true);
		if(!(errorMessage == null || errorMessage.isEmpty()))
		{
			throw new Exception(errorMessage.toString());
		}
		
		AccountService accServ = new AccountService();
		AccountEntity accEnt = new AccountEntity();
		
		accServ.setAccountID(accountNo);
		accServ.setUserID(userID);
		
		accEnt = accServ.getAccountSummary();
		
		session.setAttribute("AccSum.accNo", accEnt.getAccountID());
		session.setAttribute("AccSum.bal", accEnt.getAcctBalance()+"");
		session.setAttribute("AccSum.userID", accEnt.getUserID());
		request.getRequestDispatcher("AccountSummaryPage.html").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("AccountCriteriaPage.html").forward(request, response);
		}
	}

}
