<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profile Page</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"  href="../css/bootstrap.min.css">
<link rel="stylesheet"  href="../css/style.css">
<link rel="stylesheet"  href="../css/m-styles.min.css">

<script src="../js/jquery-2.1.3.js"></script>
<script src="../js/detectmobilebrowser.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.bootpag.min.js"></script>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
<script type="text/javascript">
	$(document).ready(function() {

		if ($.browser.mobile) {
			//set css of table, text-align in mobile 
			$('#body-profile').css('width', '100%');
			$('#body-content').css('width', '100%');
		}

		getUserInfo();

		function getUserInfo() {
			//show loading spinner image
			$('#spinner').fadeIn();
			$.ajax({
				type : "GET",
				url : "getUserInfo"
			}).done(function(data) {

				var data = jQuery.parseJSON(data);
				console.log('log = ' + data.user);
				//hide loading spinner image when ajax done
				//check valid message form server
				if (data.message) {
					$('#message').text(data.message);
				} else {
					$('#username').text(data.user.username);
					if (data.user.status == 1) {
						$('#status').text('Active');
					} else if (data.user.status == 0) {
						$('#status').text('Inactive');
					}

					$('#id').text(data.user.id);
					$('#firstname').text(data.user.firstname);
					$('#lastname').text(data.user.lastname);
					$('#email').text(data.user.email);
					$('.blog-name').text(data.user.firstname + ' ' + data.user.lastname);
				}
				$('#spinner').fadeOut();
			}).fail(function() {
				//hide loading spinner image
				$('#spinner').fadeOut();
			});
		}

	});
</script>
</head>
<body>

	<div class="container"
		style="margin: 0 auto; background-color: #EEEEEE; min-height: 100%; height: 100%;">
		<div class="row" id="header">
			<nav class="navbar navbar-default navbar-fixed-top" id="header">
			<div class="container" style="background-color: #9fc78a;">
				<div class="navbar-header" id="header-button">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="../mainpage">Mini Blog</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">

					<ul class="nav navbar-nav">
						<li id="header-button"><a href="../mainpage">Main page</a></li>
						<li id="header-button"><a href="../mainpage/userpage">My
								blog</a></li>
						<li id="header-button"><a href="../mainpage/addpost">Add
								post</a></li>
					</ul>

					<form class="navbar-form" style="display: inline-block;"
						action="../mainpage/searchUser" method="get" id="search-form" name="search-form">
						<div class="input-group" style="width: 270px;">
							<input type="text" style="border-radius: 0px;"
								class="form-control" placeholder="username, firstname, lastname"
								id="name" name="name" value="">
							<div class="input-group-btn">
								<button type="submit" style="border-radius: 0px;"
									class="btn btn-danger">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</div>
						</div>
					</form>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="profile" class="blog-name"></a></li>
						<li id="header-button"><a href="../logout">Logout</a></li>
					</ul>
				</div>
			</div>

			</nav>
		</div>
		<div class="row" id="body-content">
			<div class="row" style="margin-top: 50px">
				<ul class="breadcrumb" style="border-radius: 0px;">
					<li><a href="../mainpage">Main page</a></li>
					<li class="active"><a href="profile">Profile</a></li>
				</ul>
			</div>

			<div class="row" id="body-profile">
				<div class="row" style="width: 100%">
					<div class="col-xs-5 col-sm-4 col-md-4 col-lg-4">
						<label>Username:</label>
					</div>
					<div class="col-xs-7 col-sm-8 col-md-8 col-lg-8">
						<span id="username">${username}</span>
					</div>
				</div>
				<div class="row" style="width: 100%">
					<div class="col-xs-5 col-sm-4 col-md-4 col-lg-4">
						<label>First name:</label>
					</div>
					<div class="col-xs-7 col-sm-8 col-md-8 col-lg-8">
						<span id="firstname">${firstname}</span>
					</div>
				</div>
				<div class="row" style="width: 100%">
					<div class="col-xs-5 col-sm-4 col-md-4 col-lg-4">
						<label>Lastname:</label>
					</div>
					<div class="col-xs-7 col-sm-8 col-md-8 col-lg-8">
						<span id="lastname">${lastname}</span>
					</div>
				</div>
				<div class="row" style="width: 100%">
					<div class="col-xs-5 col-sm-4 col-md-4 col-lg-4">
						<label>Email:</label>
					</div>
					<div class="col-xs-7 col-sm-8 col-md-8 col-lg-8">
						<span id="email">${email}</span>
					</div>
				</div>

				<div class="row" style="width: 100%">
					<div class="col-xs-5 col-sm-4 col-md-4 col-lg-4">
						<label>Status:</label>
					</div>
					<div class="col-xs-7 col-sm-8 col-md-8 col-lg-8">
						<span id="status">${status}</span>
					</div>
				</div>
				<div class="row" style="text-algin: center; margin-bottom: 10px">
					<a class="m-btn blue" href="profile/edit"> Edit</a> <a
						class="m-btn blue" href="profile/changepassword"> Change
						password</a>

				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
				<div id="spinner" style="display: none">
					<div id="spinnerContent">
						<img src="../images/spinner.gif">
					</div>
					<div id="spinnerExp"></div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>