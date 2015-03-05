<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Edit Profile Page</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="type/css" href="../../css/bootstrap.min.css">
		<link rel="stylesheet" type="type/css" href="../../css/style.css">
		<link rel="stylesheet" type="type/css" href="../../css/m-styles.min.css">

		<script src="../../js/jquery-2.1.3.js"></script>
		<script src="../../js/detectmobilebrowser.js"></script>
		<script src="../../js/bootstrap.min.js"></script>
		<script src="../../js/jquery.validate.js"></script>
		<script src="../../js/jquery.bootpag.min.js"></script>
		

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
						//set css of table, text-align in mobile 
						$('.panel-default').css('width','100%');
						$('.panel-default div div div div div div').css('padding','0px');
						$('#body-content').css('width','100%');
						$('#body-content div:nth-child(2)').css('margin-left','0px');
						$('#body-content div:nth-child(2)').css('margin-right','0px');
						$('#body-content div:nth-child(2)').css('width','100%');
						$('.mainbox').css('padding-left','0px');
						$('.mainbox').css('padding-right','0px');
						$('.edit-profile-row').css('width','100%');
					}
					$('#edit-profile-form').validate({
						rules: {
							firstname: {
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
				            email: {
								required: true,
								email: true
							}
				        },
						messages: {
							firstname: "Firstname is required -- Less than 45 character",
							lastname: "Lastname is required -- Less than 45 character",
							password: {
								required: "Password is required",
								minlength: "Invalid password - More than 6 character"
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
				        },
					
					
				        submitHandler: function(){	
							//event.preventDefault();
							var firstname = $('#firstname').val();
							var lastname = $('#lastname').val();
							var email = $('#email').val();
							var status = $('#status').val();
							var password = $('#password').val();
							console.log('firstname '+ firstname);
							console.log('lastname '+ lastname);
							console.log('email '+ email);
							console.log('password '+ password);
							console.log('status '+ status);
							$.ajax({
								type:"POST",
								url:"edit",
								data: {firstname: firstname, lastname: lastname, email: email,
									status: status, password: password}
							}).done(function(data){
								console.log(data);
								console.log(data.message);
								if(data.message){
									$('#message').text(data.message);
								}
								if(data.firstnameError){
									$('#firstnameError').text(data.firstnameError);
								}
								if(data.lastnameError){
									$('#lastnameError').text(data.lastnameError);
								}
								if(data.passwordError){
									$('#passwordError').text(data.passwordError);
								}
								if(data.emailError){
									$('#emailError').text(data.emailError);
								}
								
								
							}).fail(function (){
								console.log('fail');
							});
				        }
					
					});
					
					$("select option").filter(function() {
						var statusHidden = $('#statusHidden').val();
				    	return $(this).text() == statusHidden;	
					     
					}).prop('selected', true);	
				});
				
		</script>
	</head>
	<body>
		
		<div class="container" style="margin:0 auto; background-color: #EEEEEE;min-height: 100%;">
			<div class="row" id="header">
				<nav class="navbar navbar-default navbar-fixed-top" id="header">
                    <div class="container" style="background-color: #9fc78a;">
                        <div class="navbar-header" id="header-button" >
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" >
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="../../mainpage">Mini Blog</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse" >
                            
                                <ul class="nav navbar-nav">
                                    <li id="header-button"><a href="../../mainpage">Main page</a></li>
                                    <li id="header-button"><a href="#">My blog</a></li>
                                    <li id="header-button"><a href="#">Add post</a></li>
                                </ul>
                                
                                <form class="navbar-form" style=" display:inline-block;" method="get" id="search-form" name="search-form">
                                    <div class="input-group" style="width:270px;">
                                        <input type="text" style="border-radius: 0px;" class="form-control" placeholder="username, firstname, lastname" id="query"  name="query" value="">
                                        <div class="input-group-btn">
                                            <button type="submit" style="border-radius: 0px;" class="btn btn-danger">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </div>
                                    </div>
                                </form>       
                                <ul class="nav navbar-nav navbar-right" >
                                    <li class="active"><a href="../profile">Uay Le U</a></li>
                                    <li id="header-button"><a href="../../logout">Logout</a></li>
                                </ul>    
                        </div>
                    </div>
                    
                </nav>
			</div>

			<div class="row" id="body-content"> 
				<div class="row" style="margin-top:50px">
                    <ul class="breadcrumb" style="border-radius: 0px;">
                        <li ><a href="../../mainpage">Main page</a></li>
                        <li ><a href="../profile">Profile</a></li>
                        <li class="active"><a href="">Edit Profile</a></li>
                    </ul>
                </div>
                <div class="row edit-profile-row" style="width:75%;margin:0 auto"> 
					<div class="mainbox col-md-12 col-sm-12 ">
	                    <div class="panel panel-default" style="border-radius: 0px;">
	                        
	                        <div class="panel-body">
	                        	<div style="text-align:center;height:30px;"><label class="message" id="message">${message}</label></div>
								<form role="form" action="edit" method="POST" id="edit-profile-form">
									<fieldset>
										<div class="row">
											<div class="col-sm-12 col-md-12" style="margin:0px; ">
												<div class="form-group" id="group-input-message">
				                                    <label for="firstname" class="col-md-3 control-label">First Name</label>
				                                    <div class="col-md-9" style="height:69px;">
				                                    	<div class="col-md-12" >
				                                        	<input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name" value="${firstname}">
				                                        </div>
				                                        <div class="col-md-12">
	                                        				<div style="text-align:left;"><span class="message" id="firstnameError">${firstnameError}</span></div>
				                                        </div>
				                                        
				                                    </div>
				                                </div>
				                                <div class="form-group" id="group-input-message">
				                                    <label for="lastname" class="col-md-3 control-label">Last Name</label>
				                                    <div class="col-md-9" style="height:69px;">
				                                    	<div class="col-md-12" >
				                                        	<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name" value="${lastname}">
				                                        </div>
				                                        <div class="col-md-12">
		                                        			<div style="text-align:left;"><span class="message" id="lastnameError">${lastnameError}</span></div>
				                                        </div>
				                                    </div>
				                                </div>

				                                <div class="form-group" id="group-input-message" style="height:69px;">
				                                    <label for="email" class="col-md-3 control-label">Email</label>
				                                    <div class="col-md-9" style="height:69px;">
				                                    	<div class="col-md-12" >
				                                        	<input type="text" class="form-control" id="email" name="email" placeholder="Email" value="${email}">
				                                        </div>
				                                        <div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="emailError">${emailError}</span></div>
				                                        </div>
				                                    </div>
				                                </div>

				                                <div class="form-group" id="group-input-message">
				                                    <label for="password" class="col-md-3 control-label">Password</label>
				                                    <div class="col-md-9" style="height:69px;">
				                                    	<div class="col-md-12" ">
				                                        	<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				                                        </div>
				                                        <div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="passwordError">${passwordError}</span></div>
				                                        </div>
				                                    </div>
				                                </div>
				                                
				                                <div class="form-group" id="group-input-message">
				                                	<label for="status" class="col-md-3 control-label">Status</label>
				                                	<div class="col-md-9" style="height:69px;">
				                                		<div class="col-md-12" >
				                                			<input type="hidden" id="statusHidden" name="password" value="${status}">
				                                			
															<div class="btn-group">
																<select class="btn btn-default dropdown-toggle" name="status" id="status">
																	<option value="1">Active</option>
																	<option value="0">Inactive</option>
																</select>
															</div>
															
				                                		</div>
				                                		<div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="statusError">${statusError}</span></div>
				                                        </div>
				                                	</div>
			                                	</div>
				                                

												
												<div class="form-group" style="text-align:center;">
													<input type="submit" class="m-btn blue" style="width:100px" value="Edit" id="buttonSubmit">
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
			
		</div>
		
	</body>
</html>