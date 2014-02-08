package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asu.ss.secure_banking_system.model.UserEntity;
import com.asu.ss.secure_banking_system.service.TAARequestService;
import com.asu.ss.secure_banking_system.validation.CommonValidations;
import com.sbs.model.user.User;

/**
 * Servlet implementation class TAARequest
 */
@WebServlet("/TAARequest")
public class TAARequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TAARequest() {
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
		RequestDispatcher rd=null;
		String errorMessage = new String();
		try{
		String username = request.getParameter("uname");
		
		errorMessage = CommonValidations.validateStringNormal(username, 15, errorMessage, "User Name", true);
		if(!(errorMessage == null || errorMessage.isEmpty()))
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
			
		String email = request.getParameter("email");
		if(email == null || email.isEmpty())
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		if(email.length() > 30)
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		boolean result = CommonValidations.validateEmailFormat(email);
		if(!result)
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		
		String dob = request.getParameter("dob");
		if(dob == null || dob.isEmpty())
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		result = CommonValidations.validateDateOfBirth(dob);
		if(!result)
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		String description = request.getParameter("description");
		if(description == null || description.isEmpty())
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		errorMessage = CommonValidations.validateStringNormal(description, 30, errorMessage, "Description", true);
		if(!(errorMessage == null || errorMessage.isEmpty()))
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		TAARequestService taasvc = new TAARequestService();
		User ue = new User();
		ue.setUserID(request.getSession().getAttribute("UserID").toString());
		User user = taasvc.getUserVerfied(username, dob, email);
		if(user!=null)
		{
			taasvc.createTAARequest(ue, description);
			rd = getServletContext().getRequestDispatcher("/request_taa.html");
			rd.forward(request, response);
		}
		else
		{
			rd = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		}
		catch(Exception exception)
		{
			rd = getServletContext().getRequestDispatcher("/internal_error.html");
			rd.forward(request, response);
			return;
		}
		
	}
}
