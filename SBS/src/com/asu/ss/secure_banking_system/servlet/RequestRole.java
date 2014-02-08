package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ss.secure_banking_system.model.RoleEntity;
import com.asu.ss.secure_banking_system.model.RoleType;
import com.asu.ss.secure_banking_system.model.UserEntity;
import com.asu.ss.secure_banking_system.service.RequestRoleService;
import com.asu.ss.secure_banking_system.service.RoleAssignmentService;
import com.sbs.model.user.User;

/**
 * Servlet implementation class RequestRole
 */
@WebServlet("/RequestRole")
public class RequestRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestRole() {
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
		RequestDispatcher rd;
		try{
		RequestRoleService rrsvc = new RequestRoleService();
		List<User> users = rrsvc.getAllInternalUsers(request.getSession().getAttribute("UserID").toString());
		
		int index = Integer.parseInt(request.getParameter("userSelect"));
		int roleIndex = Integer.parseInt(request.getParameter("roleSelect"));
		HttpSession session = request.getSession();
		session.removeAttribute("successMessage");
		if(index>=0&&index<users.size()&&roleIndex>=0&&roleIndex<RoleType.values().length)
		{
			User selectedUser = users.get(index);
			RoleType selectedRole = RoleType.values()[roleIndex];
			User requestingUser = new User();

			session.removeAttribute("errorDuplicateRequest");
			requestingUser.setUserID((String)session.getAttribute("UserID"));
			RoleAssignmentService resvc = new RoleAssignmentService();
			if(resvc.isDuplicateRequest(requestingUser.getUserID(),selectedUser.getUserID(), selectedRole.getValue()))
			{
				session.setAttribute("errorDuplicateRequest", "You have made duplicate requests");
				rd = request.getRequestDispatcher("/requestrole.html");
				rd.forward(request, response);
				return;
			}
			else
			{
				rrsvc.createRoleRequest(requestingUser, selectedUser, selectedRole);
				session.setAttribute("successMessage", "You have successfully made a request");
				rd  = getServletContext().getRequestDispatcher("/requestrole.html");
				rd.forward(request, response);

			}
		}
		else
		{
			rd  = getServletContext().getRequestDispatcher("/invalid_request.html");
			rd.forward(request, response);
		}
		}
		catch(Exception exception)
		{
			rd  = getServletContext().getRequestDispatcher("/internal_error.html");
			rd.forward(request, response);
		}
	}
}
