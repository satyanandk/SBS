<%@page import="com.asu.ss.secure_banking_system.model.TransactionEntity"%>
<%@page import="com.asu.ss.secure_banking_system.service.ProcessTAAService"%>
<%@page import="com.sbs.model.user.User"%>
<%@page import="com.asu.ss.secure_banking_system.model.TAARequestEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>View TAA</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet">
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>


</head>
<body>
	<% 
		ProcessTAAService taaService = new  ProcessTAAService();
		User user = (User)session.getAttribute("userToProcess");
		user = taaService.getUserDetails(user);
		List<TransactionEntity> transactions = taaService.getTransactionDetails(user);
	%>
<div class="container">
<div class="hero-unit">
  		<div class="container">
    		<h1>Process technical account access request</h1>
    		<p>Technical account access request page</p>
  		</div>
</div>
		<div id="mainFrame">
			<div id="content">
				<div class="row">
					<div class="span3">
						<ul class="nav nav-list">
							<li><a href="welcomepage.html">Welcome Page</a></li>
							<li><a href="logout.html">Logout</a></li>
						</ul>
					</div>
					<div class="span9">
					<form>
			<table border="2px" width="100%">
			<tr><h4>
					Profile information details
			<h4>
			</tr>
			<tr>	
			<td>First name :<%= (user.getFirstname()==null?"":user.getFirstname())%></td>
		
		
			<td>Last name:<%= (user.getLastname()==null?"":user.getLastname())%></td>
		
		
			<td>Address:<%= (user.getAddress()==null?"":user.getAddress()) %></td>
		
		
			<td>Contact:<%= (user.getContact()==null?"":user.getContact())%></td>
		
		
			<td>Email id:<%= (user.getEmailid()==null?"":user.getEmailid())%></td>
		
			<td>Date of birth:<%= (user.getDOB()==null?"":user.getDOB())%></td>
		
		
			<td>ID type:<%= (user.getIdtype()==null?"":user.getIdtype())%></td>
		
			<td>ID number:<%= (user.getIdNo()==null?"":user.getIdNo())%></td>
			</tr>
			</table>			
			<table border="2px" width="100%">
			<tr><th>Transactions</th></tr>
			<tr>
			<td>Transaction type</td>
			<td>Transaction date</td>
			<td>Account</td>
			<td>From user</td>
			<td>To user</td>
			<td>Amount</td>
			<td>Balance</td>
			</tr>
			<% for(int i=0;i<transactions.size();i++)
			{
			%>
		
			<tr>
			<td><%= transactions.get(i).getTranType()%></td>
			<td><%= transactions.get(i).getTranDate()%></td>
			<td><%= transactions.get(i).getAccountId()%></td>
			<td><%= transactions.get(i).getUserID()%></td>
			<td><%= transactions.get(i).getTranCreatedByUser()%></td>
			<td><%= transactions.get(i).getTranAmount()%></td>
			<td><%= transactions.get(i).getBalance()%></td>
		</tr>
		<%
			} 
		%>
		</table>
		</form>
		</div>
		</div>
		</div>
		</div>
	</div>
					
</body>
</html>