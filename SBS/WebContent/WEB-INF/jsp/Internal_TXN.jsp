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
<title>Internal_TXN</title>
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
			<a href="#">Internal Transactions</a>
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
			<form:form action="intTxnRequest.html" method ="post">
				<c:if test="${not empty intTxnList}">
					<table border="1" width="100%" cellpadding="10" cellspacing="10">
						<tbody>
							<tr>
								<th>Transaction ID</th>
								<th>Type</th>
								<th>Date</th>
								<th>From User</th>
								<th>By User</th>
								<th>Details</th>
								<th>Get Authorization</th>
							</tr>
							<c:forEach var="intTxn" items="${intTxnList}">
								<tr>
									<td>${intTxn.txnId}</td>
									<td>${intTxn.txnType}</td>
									<td>${intTxn.txnDate}</td>
									<td>${intTxn.fromUserId}</td>
									<td>${intTxn.toUserId}</td>
									<td>${intTxn.details}</td>
									<c:if test="${intTxn.status eq 'none'}">
										<td><input type="checkbox" name="selectedId" value="${intTxn.txnId}"/></td>
									</c:if>
									<c:if test="${intTxn.status eq 'R'}">
										<td>Authorization Requested</td>
									</c:if>
									<c:if test="${intTxn.status eq 'A'}">
										<td>Authorized</td>
									</c:if>
									<c:if test="${intTxn.status eq 'D'}">
										<td>Denied</td>
									</c:if>
									<c:if test="${intTxn.status eq 'X'}">
										<td>Self Transaction</td>
									</c:if>
									<c:if test="${intTxn.status eq 'S'}">
										<td>Same Department Transaction</td>
									</c:if>
									<%-- <td>
										<c:if test="${intTxn[6]}">
											<input value="Ask Authorization" type="button" class="btn btn-small btn-primary">
										</c:if>
										<c:if test="${intTxn[7]}">
											<input value="Authorized" disabled="disabled" type="button" class="btn btn-small btn-primary">
										</c:if>
									</td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input  class="btn btn-success" type="submit" value="Submit" />
				</c:if>
				<c:if test="${empty intTxnList}">
					No Transactions found!
				</c:if>
				</form:form>


				<!--   <table cellpadding="10"
                cellspacing="10">
                <tbody>
                        <tr>
                                <th>Transaction ID</th>
                                <th>Type</th>
                                <th>Date</th>
                                <th>From User</th>
                                <th>By User</th>
                                <th>Details</th>
                                <th>Authorized</th>
                        </tr>
                        <tr>
                                <td>124124</td>
                                <td>Security</td>
                                <td>10/13/2012</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>Change security to log page</td>
                                <td><input value="Ask Authorization" type="button" class="btn btn-small btn-primary"></td>
                        </tr>
                        <tr>
                                <td>6463423</td>
                                <td>Credit</td>
                                <td>10/15/2013</td>
                                <td>sandip</td>
                                <td>tom</td>
                                <td>50$</td>
                                <td><input value="Authorized" disabled="disabled" type="button" class="btn btn-small btn-primary"></td>
                        </tr>
                </tbody>
        </table> -->
			</div>
		</div>
	</div>

</body>
</html>