<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="
">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Register Page</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="type/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="type/css" href="css/style.css">
		<link rel="stylesheet" type="type/css" href="css/m-styles.min.css">

		<script src="js/jquery-2.1.3.js"></script>
		<script src="js/detectmobilebrowser.js"></script>
		<script src="js/jquery.validate.js"></script>
		<script src="js/bootstrap.min.js"></script>

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->

		<script type="text/javascript">
			$(document).ready(function(){
				
				if($.browser.mobile)
				{
					$('.panel-default').css('width','100%');
					$('.panel-default div div div div div div').css('padding','0px');
					$('.container div:nth-child(2)').css('margin-left','0px');
					$('.container div:nth-child(2)').css('margin-right','0px');
					$('#registerbox').css('padding-left','0px');
					$('#registerbox').css('padding-right','0px');
					$('.register-row').css('width','100%');

				}
				$('#register-form').validate({
					rules: {
						username: {
			                minlength: 1,
			                maxlength: 40,
			                required: true
			            },firstname: {
			                minlength: 1,
			                maxlength: 45,
			                required: true
			            },lastname: {
			                minlength: 1,
			                maxlength: 45,
			                required: true
			            },
			            password: {
			                minlength: 6,
			                required: true
			            },
			            confirmPassword: {
							required: true,
							minlength: 6,
							equalTo: "#password"
						},email: {
							required: true,
							email: true
						}
			        },
					messages: {
						firstname: "Firstname is required -- Less than 45 character",
						lastname: "Lastname is required -- Less than 45 character",
						username: {
							required: "Username is required",
							minlength: "Invalid username - From 6 to 40",
							maxlength: "Invalid username - From 6 to 40",
						},
						password: {
							required: "Password is required",
							minlength: "Invalid password - More than 6 character"
						},
						confirmPassword: {
							required: "Confirm Password is required",
							minlength: "Invalid password - More than 6 character",
							equalTo: "Please enter the same password as above"
						},
						email: "Email is  invalid format -- Less than 100 character"
					},
			        highlight: function(element) {
			            $(element).closest('.form-group').addClass('has-error');
			        },
			        unhighlight: function(element) {
			            $(element).closest('.form-group').removeClass('has-error');
			        },
			        errorElement: 'span',
			        errorClass: 'help-block',
			        errorPlacement: function(error, element) {
			            if(element.parent('.input-group').length) {
			                error.insertAfter(element.parent());
			            } else {
			                error.insertAfter(element);
			            }
			        }
				}); 
				
			});
		</script>
	</head>
	<body>

		<div class="container" style="background-color: #EEEEEE; min-height: 100%;height:100%;">
				
			<div class="row" style="height:51px">
				<!-- Fixed navbar -->
			    <nav class="navbar navbar-default navbar-fixed-top" id="header">
			      <div class="container" style="background-color: #9fc78a;">
			        <div class="navbar-header" id="header-button">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" >
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">Mini Blog</a>
			        </div>
			        <div id="navbar" class="navbar-collapse collapse">
			          <ul class="nav navbar-nav navbar-right">
			            <li id="header-button"><a href="/mibiblog">Login</a></li>
			            <li class="active"><a href="#">Register</a></li>
			          </ul>
			        </div>
			      </div>
			    </nav>
			</div>
			
			<div class="row register-row" style="width:75%;
    margin: 0 auto;">
				<div id="registerbox" style="margin-top:30px" class="col-md-12  col-sm-12">
                    <div class="panel panel-default" style="border-radius: 0px;">
                        <div class="panel-heading" style="border-radius: 0px;background-color: #9fc78a">
                            <div class="panel-title">Register</div>
                            <div style="float:right; font-size: 85%; position: relative; top:-10px"></div>
                        </div>  
                        <div class="panel-body">
                        	
							<form role="form" action="register" method="POST" id="register-form">
								<fieldset>
									<div class="row">
										<div class="center-block">
											<img class="profile-img"
												src="images/logo.png" alt="">
										</div>
									</div>
									
									<div class="row">
										<div style="text-align:center;"><label class="message">${message}</label></div>
										<div class="col-sm-12 col-md-12" style="margin-top:15px;">
											 <div class="form-group" id="group-input-message">
			                                    <label for="username" class="col-md-3 control-label">Username</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12">
			                                        	<input type="text" class="form-control" name="username" placeholder="User name" value=${username}>
			                                        </div>
			                                        <div class="col-md-12">
			                                        	<div style="text-align:left;"><span class="message">${usernameError}</span></div>	
			                                        </div>
			                                    </div>
			                                </div>
		                                </div>
		                                <div class="col-sm-12 col-md-12" >
											<div class="form-group" id="group-input-message">
			                                    <label for="firstname" class="col-md-3 control-label">First Name</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12">
			                                        	<input type="text" class="form-control" name="firstname" placeholder="First Name" value=${firstname}>
			                                        </div>
			                                        <div class="col-md-12">
			                                        	<div style="text-align:left;"><span class="message">${firstnameError}</span></div>
			                                        </div>
			                                        
			                                    </div>
			                                </div>
		                                </div>
		                                <div class="col-sm-12 col-md-12" >
			                                <div class="form-group" id="group-input-message">
			                                    <label for="lastname" class="col-md-3 control-label">Last Name</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12">
			                                        	<input type="text" class="form-control" name="lastname" placeholder="Last Name" value=${lastname}>
			                                        </div>
			                                        <div class="col-md-12">
			                                        	<div style="text-align:left;"><span class="message">${lastnameError}</span></div>
			                                        </div>
			                                        
			                                    </div>
			                                </div>
		                                </div>
		                                <div class="col-sm-12 col-md-12" >
			                                <div class="form-group" id="group-input-message">
			                                    <label for="password" class="col-md-3 control-label">Password</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12">
			                                        	<input type="password" class="form-control" id="password" name="password" placeholder="Password" value=${password}>
			                                        </div>
			                                        <div class="col-md-12">
			                                        	<div style="text-align:left;"><span class="message">${passwordError}</span></div>
			                                        </div>
			                                    </div>
			                                </div>
	                                    </div>
		                                <div class="col-sm-12 col-md-12">
			                                <div class="form-group" id="group-input-message">
			                                    <label for="confirmPassword" class="col-md-3 control-label">Confirm password</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12" style="height:69px;">
			                                        	<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm password">
			                                        </div>
			                                        <div class="col-md-12">
			                                        	
			                                        </div>
			                                        
			                                    </div>
			                                </div>
										</div>
		                                <div class="col-sm-12 col-md-12" >
											<div class="form-group" id="group-input-message">
			                                    <label for="email" class="col-md-3 control-label">Email</label>
			                                    <div class="col-md-9" style="height:69px;">
			                                    	<div class="col-md-12" >
			                                        	<input type="text" class="form-control" name="email" placeholder="Email" value=${email}>
			                                        </div>
			                                        <div class="col-md-12">
			                                        	<div style="text-align:left;"><span class="message">${emailError}</span></div>	
			                                        </div>
			                                    </div>
			                                </div>
		                                </div>
		                                <div class="col-sm-12 col-md-12" style="margin:0px;">
											<div class="form-group" style="text-align:center;">
												<input type="submit" class="m-btn blue" style="width:100px" value="Register" id="registerSubmit">
												<input id="btn btn-danger" class="m-btn blue" style="width:100px" type="reset" value="Clear">
											</div>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
						
	                </div>
                </div>
            </div>	
     	</div>

	</body>
</html>