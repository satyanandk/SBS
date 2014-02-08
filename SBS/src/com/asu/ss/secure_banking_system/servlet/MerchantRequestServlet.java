package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.asu.ss.secure_banking_system.model.AccountService;
import com.asu.ss.secure_banking_system.model.MerchantService;
import com.asu.ss.secure_banking_system.validation.CommonValidations;
import com.sbs.model.crypto.PkiUtils;

/**
 * Servlet implementation class MerchantRequestServlet
 */
@WebServlet(description = "Servlet for generating an activation link and mailing to customer's email for requesting payment", urlPatterns = { "/MerchantRequestServlet" })
@MultipartConfig
public class MerchantRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MerchantRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try
		{
			request.removeAttribute("exception");
			HttpSession session = (HttpSession)request.getSession(true);
			
			String merchantUserID = (String)session.getAttribute("UserID");
			//merchantUserID = "MERCHANT";
			System.out.println("SATYA Page MERCHANT userID = "+merchantUserID);
			String userID = (String)request.getParameter("customerUserID");
			if(userID == null||userID.isEmpty())
			{
				throw new Exception("Enter the customer user ID!");
			}
			if(!CommonValidations.valiateSpecialCharacters(userID))
				throw new Exception("Special Characters are not allowed for User ID!");
			
			CommonValidations.validateLength(userID, 15, "User ID");
			
			String amount = (String)request.getParameter("amount");
			if(amount == null||amount.isEmpty())
			{
				throw new Exception("Enter the amount!");
			}
			
			double dAmount = Double.valueOf(amount);
			
			String remarks = (String)request.getParameter("remarks");
			if(remarks==null || remarks.isEmpty())
				throw new Exception("Enter the remarks!");
			if(!CommonValidations.valiateSpecialCharacters(remarks))
				throw new Exception("Special characters are not allowed in Remarks!!");
			CommonValidations.validateLength(remarks, 10, "Remarks");
			
			Part filePart = request.getPart("fileName"); // Retrieves <input type="file" name="file">
		    String filename = getFilename(filePart);
		    System.out.println("filename = "+filename);
		    InputStream filecontent = filePart.getInputStream();
		    CertificateFactory cf = CertificateFactory.getInstance("X509");
		    X509Certificate cert = (X509Certificate) cf.generateCertificate(filecontent);
		    PkiUtils p = new PkiUtils();
		    PublicKey publicKey = p.getUserPublicKey(merchantUserID);
			AccountService accService = new AccountService();
			accService.setUserID(userID);
			cert.checkValidity();
			cert.verify(publicKey);
			
			MerchantService merchant = new MerchantService(merchantUserID, userID, dAmount, remarks);
			merchant.requestPayment();
			
			request.getRequestDispatcher("RequestPaymentPage.html").forward(request, response);
			

		}
		catch (CertificateException | NoSuchAlgorithmException| InvalidKeyException | NoSuchProviderException| SignatureException e) {
			request.setAttribute("exception", "Certificate can not be verified");
			request.getRequestDispatcher("RequestPaymentPage.html").forward(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			
			request.getRequestDispatcher("RequestPaymentPage.html").forward(request, response);
		}
		
			
	}

}
