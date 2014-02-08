package com.asu.ss.secure_banking_system.servlet;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.RequestEntity;
import com.asu.ss.secure_banking_system.model.RoleRequestEntity;
import com.asu.ss.secure_banking_system.service.AdminNotificationService;
import com.asu.ss.secure_banking_system.service.RoleAssignmentService;

/**
 * Servlet implementation class RoleRequestValidation
 */
@WebServlet("/RoleRequestValidation")
public class RoleRequestValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoleRequestValidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		try{
			
		RoleAssignmentService resvc = new RoleAssignmentService();
		HttpSession session = request.getSession();
		int index = Integer.parseInt(request.getParameter("RoleRequestToValidate"));
		AdminNotificationService ansvc = new AdminNotificationService();
	    ArrayList<RequestEntity> requestList = ansvc.getAllRequestes(session.getAttribute("UserID").toString());
	    
	    if(index>=0&& index<requestList.size() && requestList.get(index).getClass().equals(RoleRequestEntity.class))
		{
	    	RoleRequestEntity re = (RoleRequestEntity) requestList.get(index);
	    	session.setAttribute("requestDetails", re);
			if(resvc.isValidRequest(re))
				session.setAttribute("isValidRequest", true);
			else
				session.setAttribute("isValidRequest", false);
			rd = getServletContext().getRequestDispatcher("/assign_role.html");
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
		}
	}

}