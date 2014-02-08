<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<SCRIPT type="text/javascript">
	window.oncontextmenu = function() {return false;}
    window.history.forward();
    function noBack() { window.history.forward(); }
</SCRIPT>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>External_TXN</title>
<!-- Bootstrap core CSS -->
<!--Online link to include bootstrap need not include any libraries in eclipse -->
<link
	href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>

</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
	<div class="container">
		<h1>
			<a href="#">External Transactions</a>
		</h1>
		<div class="row">
			<div class="span3">
				<ul class="nav nav-list">
					<li class="nav-header">Other Links</li>
					<li><a href="welcomepage.html">Welcome Page</a></li>
					<li><a href="logout.html">Logout</a></li>
				</ul>
			</div>
			<div class="span9">
				<c:if test="${not empty extTxnList}">
					<table border="1" width="100%" cellpadding="10" cellspacing="10">
						<tbody>
						<tr>
							<th>Transaction ID</th>
							<th>Type</th>
							<th>Date</th>
							<th>From User</th>
							<th>For User</th>
							<th>Amount</th>
							<th>Balance<br>
							</th>
						</tr>
						<c:forEach var="extTxn" items="${extTxnList}">
							<tr>
								<td>${extTxn[0]}</td>
								<td>${extTxn[1]}</td>
								<td>${extTxn[2]}</td>
								<td>${extTxn[3]}</td>
								<td>${extTxn[4]}</td>
								<td>${extTxn[5]}</td>
								<td>${extTxn[6]}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty extTxnList}">
					No Transactions found!
				</c:if>
				<!-- <table cellpadding="10" cellspacing="10">
					<tbody>
						<tr>
							<th>Transaction ID</th>
							<th>Type</th>
							<th>Date</th>
							<th>From User</th>
							<th>By User</th>
							<th>Amount</th>
							<th>Balance<br>
							</th>
						</tr>
						<tr>
							<td>35338</td>
							<td>Credit</td>
							<td>10/13/2012</td>
							<td>sandip</td>
							<td>gokul</td>
							<td>$50</td>
							<td>$242.74</td>
						</tr>
						<tr>
							<td>34249</td>
							<td>Debit</td>
							<td>10/15/2013</td>
							<td>sandip</td>
							<td>tom</td>
							<td>$30</td>
							<td>$192.74</td>
						</tr>
					</tbody>
				</table>
 -->			</div>
		</div>
	</div>

</body>

</html>