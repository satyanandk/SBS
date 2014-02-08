package com.asu.ss.secure_banking_system.servlet;

import java.io.IOException;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
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
import com.asu.ss.secure_banking_system.validation.CommonValidations;
import com.sbs.model.crypto.PkiUtils;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet(description = "Servlet for creating the account", urlPatterns = { "/CreateAccountServlet" })
@MultipartConfig
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
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
		
		HttpSession session = request.getSession(true);
		try
		{	
			String accountID = "";
			
			request.removeAttribute("exception");
			String userID = request.getParameter("userID");
			System.out.println("user ID = "+userID);
			if(userID == null || userID.isEmpty())
				throw new Exception("User ID must be entered!");
			
			if(!CommonValidations.valiateSpecialCharacters(userID))
				throw new Exception("Special characters are not allowed in User ID!");
		
			CommonValidations.validateLength(userID, 15, "User ID");
			
			Part filePart = request.getPart("fileName"); // Retrieves <input type="file" name="file">
		    String filename = getFilename(filePart);
		    System.out.println("filename = "+filename);
		    InputStream filecontent = filePart.getInputStream();
		    CertificateFactory cf = CertificateFactory.getInstance("X509");
		    X509Certificate cert = (X509Certificate) cf.generateCertificate(filecontent);
		    PkiUtils p = new PkiUtils();
		    PublicKey publicKey = p.getUserPublicKey((String)session.getAttribute("UserID"));
			AccountService accService = new AccountService();
			accService.setUserID(userID);
			cert.checkValidity();
			cert.verify(publicKey);
			accountID = accService.createAccountForUser();
			
			String remoteAddr = request.getRemoteAddr();
	        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
	        reCaptcha.setPrivateKey("6LdIzOkSAAAAAM-qhGSfpfAsRKKw0xcAUUmSdTyL");

	        String challenge = request.getParameter("recaptcha_challenge_field");
	        String uresponse = request.getParameter("recaptcha_response_field");
	        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

	        if (!reCaptchaResponse.isValid()) {
	        	throw new Exception("Invalid captcha value!");
	        } 
			
			session.setAttribute("createAccount.accountID", accountID);
			session.setAttribute("resPageInd", "CREATE_ACCOUNT");
			request.getRequestDispatcher("resultpagepage.html").forward(request, response);
		}
		catch (CertificateException | NoSuchAlgorithmException| InvalidKeyException | NoSuchProviderException| SignatureException e) {
			request.setAttribute("exception", "Certificate can not be verified");
			request.getRequestDispatcher("createAccountPage.html").forward(request, response);
		}
		catch(Exception e)
		{
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("createAccountPage.html").forward(request, response);
		}
		
		
		
		
	}

}
