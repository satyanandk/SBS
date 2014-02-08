package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.RoleRequestEntity;
import com.asu.ss.secure_banking_system.service.RoleAssignmentService;

/**
 * Servlet implementation class AssignRole
 */
@WebServlet("/AssignRole")
public class AssignRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignRole() {
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
		RequestDispatcher rd =null;
		try{
			
		
		HttpSession session = request.getSession();
		
		if((Boolean)session.getAttribute("isValidRequest")==true)
		{
			RoleAssignmentService rasvc = new RoleAssignmentService();
			rasvc.assignRoleToUser((RoleRequestEntity)session.getAttribute("requestDetails"));
			rd = getServletContext().getRequestDispatcher("/admin_notif.html");
			rd.forward(request, response);
		}
		else
		{
			rd = getServletContext().getRequestDispatcher("/admin_notif.html");
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
