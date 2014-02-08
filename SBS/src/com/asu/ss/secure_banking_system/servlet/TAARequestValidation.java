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
import com.asu.ss.secure_banking_system.model.TAARequestEntity;
import com.asu.ss.secure_banking_system.service.AdminNotificationService;
import com.asu.ss.secure_banking_system.service.TAARequestService;

/**
 * Servlet implementation class TAARequestValidation
 */
@WebServlet("/TAARequestValidation")
public class TAARequestValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TAARequestValidation() {
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
		RequestDispatcher rd=null;		
		// TODO Auto-generated method stub
		try{

		int index = Integer.parseInt(request.getParameter("TAARequestToValidate"));
		TAARequestService svc = new TAARequestService();
		AdminNotificationService ansvc = new AdminNotificationService();
	    ArrayList<RequestEntity> requestList = ansvc.getAllRequestes(request.getSession().getAttribute("UserID").toString());
		
		HttpSession session = request.getSession();
		if(index>=0 && index<requestList.size()&&requestList.get(index).getClass().equals(TAARequestEntity.class))
		{
		TAARequestEntity taa = (TAARequestEntity)requestList.get(index);
		session.setAttribute("TAARequestDetails", taa);
			if(svc.checkIfValidTAARequest(taa))
		{
			session.setAttribute("isValidTAARequest", true);
			session.setAttribute("TAARequestToValidate", index);

		}
		else
		{
			session.setAttribute("isValidTAARequest", false);			
		}
		rd = getServletContext().getRequestDispatcher("/assign_taa.html");
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
