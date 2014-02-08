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

import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.service.TAANotificationService;
import com.asu.ss.secure_banking_system.util.AuthorizationUtil;
import com.sbs.model.user.User;

/**
 * Servlet implementation class ProcessTAARequest
 */
@WebServlet("/ProcessTAARequest")
public class ProcessTAARequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessTAARequest() {
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
		RequestDispatcher rd;
		try
		{

			HttpSession session = request.getSession();
			int index = Integer.parseInt(request.getParameter("TAAToProcess"));
			TAANotificationService svc =  new  TAANotificationService();
			User user = new User();
			user.setUserID(session.getAttribute("UserID").toString());
			List<TAARequestEntity> requests = svc.getAllTAARequestsForUser(user);
			TAARequestEntity taa = (TAARequestEntity)requests.get(index);
			if(AuthorizationUtil.checkIfExternalUser(taa.getRequestedBy().getUserID()))
			{
				User requestedUser = new User();
				requestedUser.setUserID(taa.getRequestedBy().getUserID());
				session.setAttribute("userToProcess", requestedUser);
				rd  = getServletContext().getRequestDispatcher("/process_taa.html");
				rd.forward(request, response);
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
