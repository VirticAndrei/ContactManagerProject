<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="no-cache"> 
		<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
		
		<link href="static/css/bootstrap.min.css" rel="stylesheet">
		
		<title>Contact Manager | Home</title>
	</head>
	<body>
	    <div role="navigation">
			<div class="navbar navbar-inverse">
				<a href="/" class="navbar-brand">ContactManager</a>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li><a href="new-contact">New Contact</a></li>
						<li><a href="all-contacts">All Contacts</a></li>
					</ul>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${mode == 'MODE_HOME'}">
				<div class="container" id="homeDiv">
					<div class="jumbotron text-center">
						<h1>Welcome to Contact Manager</h1>
						<form class="form-horizontal" method="GET" action="search-contact">
							<div class="form-group">
								<input type="text" list="contactNames" class="form-control" name="name" />			
								<datalist id="contactNames" >
									<c:forEach var="contact" items="${contacts}">
										<option data-tokens="${contact.getName()}">${contact.getName()}</option>
									</c:forEach>
								</datalist>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary">
	      							<span class="glyphicon glyphicon-search"></span> Search
	    						</button>
							</div>
						</form>
					</div>
				</div>
			</c:when>
			<c:when test="${mode == 'MODE_CONTACTS'}">
				<div class="container text-center" id="contactsDiv">
					<h3>Contacts</h3>
					<hr>
					<div class="table-responsive">
						<table class="table table-striped table-bordered text-left">
							<thead>
								<tr>
									<th>Name</th>
									<th>Picture</th>
									<th>Email</th>
									<th>Phone</th>
									<th>Address</th>
									<th>Option</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="contact" items="${contacts}">
									<tr>
										<td>${contact.name}</td>
										<td><img src= "${contact.getPicture()}" class="img-rounded" alt="${contact.name}_picture" height="42" width="42"></td>
										<td>${contact.getAddress().email}</td>
										<td>${contact.getAddress().phone}</td>
										<td>Street ${contact.getAddress().street} nr. ${contact.getAddress().number}</td>
										<td>
											<a href="update-contact?idContact=${contact.getId()}"><span class="glyphicon glyphicon-pencil"></span>Edit</a>
											<a href="delete-contact?idContact=${contact.getId()}" class="text-danger"><span class="glyphicon glyphicon-trash"></span>Delete</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>	
			<c:when test="${mode == 'MODE_NEW' || mode == 'MODE_UPDATE'}">
				<div class="container text-center">
					<h3>${titleString}</h3>
					<hr>
					<form class="form-horizontal" enctype="multipart/form-data" method="POST" action="save-contact">
						<input type="hidden" name="id_contact" value="${contact.getId()}"/>
						<input type="hidden" name="id_address" value="${address.getId()}"/>
						<input type="hidden" name="pictureString" value="${pictureFile}"/>
						<div class="form-group">
							<label class="control-label col-md-3">Name:</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="name" value="${contact.name}"/>
							</div>
							<label class="control-label col-md-3">${errorMessages.get(0)}</label>				
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Email:</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="email" value="${address.email}"/>
							</div>
							<label class="control-label col-md-3">${errorMessages.get(1)}</label>				
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Phone:</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="phone" value="${address.phone}"/>
							</div>	
							<label class="control-label col-md-3">${errorMessages.get(2)}</label>			
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Street:</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="street" value="${address.street}"/>
							</div>	
							<label class="control-label col-md-3">${errorMessages.get(3)}</label>			
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Number:</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="number" value="${address.number}"/>
							</div>			
							<label class="control-label col-md-3">${errorMessages.get(4)}</label>	
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Picture:</label>
							<div class="col-md-6">
								<input type="file" class="form-control" name="pictureFile"/>
							</div>
							<label class="control-label col-md-3">*maximum size: 1MB</label>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-primary" value="Save"/>
							<a href="/" class="btn btn-default" role="button">Back</a>
						</div>
					</form>
				</div>
			</c:when>
			<c:when test="${mode == 'MODE_SEARCH'}">
				<div class="container text-center">
					<h3>Contact</h3>
					<hr>
					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<tbody>
								<tr>
									<td class="text-right">Name:</td>
									<td class="text-left">${contact.name}</td>
									<td class="text-left" rowspan="4"><img src= "${contact.getPicture()}" class="img-rounded" alt="${contact.name}_picture" height="150" width="150"></td>
								</tr>
								<tr>
									<td class="text-right">Email:</td>
									<td class="text-left">${contact.getAddress().email}</td>
								</tr>
								<tr>
									<td class="text-right">Phone:</td>
									<td class="text-left">${contact.getAddress().phone}</td>
								</tr>
								<tr>
									<td class="text-right">Address:</td>
									<td class="text-left" rowspan="2">Str. ${contact.getAddress().street} nr. ${contact.getAddress().number} </td>
								</tr>
							</tbody>
						</table>
						<form class="form-horizontal" method="GET" action="update-contact">
							<input type="hidden" name="idContact" value="${contact.getId()}"/>
							<div class="form-group">
								<input type="submit" class="btn btn-primary" value="Edit"/>
								<a href="/" class="btn btn-default" role="button">Back</a>
							</div>
						</form>
					</div>
				</div>
			</c:when>
			<c:when test="${mode == 'MODE_SEARCH_NOT_FOUND'}">
				<div class="container" id="searchDiv">
					<div class="jumbotron text-center">
						<h3>Contact not found!</h3>
						<div class="form-group">
							<a href="/" class="btn btn-default" role="button">Back</a>
						</div>
					</div>
				</div>
			</c:when>	
		</c:choose>	
		<script src="static/js/jquery-1.11.1.min.js"></script>    
		<script src="static/js/bootstrap.min.js"></script>
	</body>
</html>
