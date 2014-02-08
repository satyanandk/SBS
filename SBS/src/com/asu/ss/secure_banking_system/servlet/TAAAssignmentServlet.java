package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asu.ss.secure_banking_system.model.RequestEntity;
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.service.AdminNotificationService;
import com.asu.ss.secure_banking_system.service.TAAAssignmentService;
import com.sbs.model.user.User;

/**
 * Servlet implementation class TAAAssignmentServlet
 */
@WebServlet("/TAAAssignmentServlet")
public class TAAAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TAAAssignmentServlet() {
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
			
		

		
		boolean isValidRequest= (Boolean)request.getSession().getAttribute("isValidTAARequest");
		
		if(isValidRequest==true)
		{
			AdminNotificationService ansvc = new AdminNotificationService();
		    ArrayList<RequestEntity> requestList = ansvc.getAllRequestes(request.getSession().getAttribute("UserID").toString());
			int index = Integer.parseInt(request.getSession().getAttribute("TAARequestToValidate").toString());
			if(index>=0&& index<requestList.size()&&requestList.get(index).getClass().equals(TAARequestEntity.class))
			{
				TAAAssignmentService svc = new TAAAssignmentService();
				List<User> users = svc.getAllInternalUsers(request.getSession().getAttribute("UserID").toString());
				TAARequestEntity taa = (TAARequestEntity)requestList.get(index);
				int userIndex = Integer.parseInt(request.getParameter("assignedUser"));
				if(userIndex>=0 && userIndex<users.size())
				{
						User user = users.get(Integer.parseInt(request.getParameter("assignedUser")));
						svc.assignTAAtoUser(taa, user);
						rd = getServletContext().getRequestDispatcher("/admin_notif.html");
						rd.forward(request, response);
				}
				else
				{
					rd = getServletContext().getRequestDispatcher("/invalid_request.html");
					rd.forward(request, response);
				}
			}
			else
			{
				rd = getServletContext().getRequestDispatcher("/invalid_request.html");
				rd.forward(request, response);
			}
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
