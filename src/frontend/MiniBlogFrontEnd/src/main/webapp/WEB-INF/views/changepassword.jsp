<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Change password Page</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="type/css" href="../../css/bootstrap.min.css">
		<link rel="stylesheet" type="type/css" href="../../css/style.css">
		<link rel="stylesheet" type="type/css" href="../../css/m-styles.min.css">

		<script src="../../js/jquery-2.1.3.js"></script>
		<script src="../../js/detectmobilebrowser.js"></script>
		<script src="../../js/bootstrap.min.js"></script>
		<script src="../../js/jquery.bootpag.min.js"></script>
		<script src="../../js/jquery.validate.js"></script>
		

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
				}
				$('#change-password-form').validate({
					rules: {
						currentPassword: {
							required: true,
							minlength: 6
			            },newPassword: {
			            	required: true,
							minlength: 6
			            },confirmPassword: {
							required: true,
							minlength: 6,
							equalTo: "#newPassword"
						}
			        },
					messages: {
						currentPassword: {
							required: "Current password is required",
							minlength: "Invalid password - More than 6 character"
						},
						newPassword: {
							required: "New password is required",
							minlength: "Invalid password - More than 6 character"
						},
						confirmPassword: {
							required: "Confirm password is required",
							minlength: "Invalid password - More than 6 character"
						}						
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
			        } ,
			        submitHandler: function(){	
						//event.preventDefault();
						var currentPassword = $('#currentPassword').val();
						var newPassword = $('#newPassword').val();
						console.log('currentPassword '+ currentPassword);
						console.log('newpassword '+ newPassword);
						$.ajax({
							type:"POST",
							url:"changePassword",
							data: {currentPassword: currentPassword, newPassword: newPassword}
						}).done(function(data){
							console.log(data);
							console.log(data.message);
							if(data.message){
								$('#message').text(data.message);
							}
							if(data.passwordError){
								$('#currentPasswordError').text(data.passwordError);
							}
							
						}).fail(function (){
							console.log('fail');
						});
			        } 
				
				});
			});
			
			$(document).ready(function(){
			
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
                            <a class="navbar-brand" href="#">Mini Blog</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse" >
                            
                                <ul class="nav navbar-nav">
                                    <li class="active"><a href="#">Main page</a></li>
                                    <li id="header-button"><a href="#">My blog</a></li>
                                    <li id="header-button"><a href="#">Add post</a></li>
                                </ul>
                                
                                <form class="navbar-form" style=" display:inline-block;" method="get" id="search-user-form" name="search-form">
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
                                    <li id="header-button"><a href="profile.html">Uay Le U</a></li>
                                    <li id="header-button"><a href="homepage.html">Logout</a></li>
                                </ul>    
                        </div>
                    </div>
                    
                </nav>
			</div>

			<div class="row" id="body-content"> 
				<div class="row" style="margin-top:50px">
                    <ul class="breadcrumb" style="border-radius: 0px;">
                        <li ><a href="#">Main page</a></li>
                        <li ><a href="#">Profile</a></li>
                        <li class="active"><a href="#">Edit Profile</a></li>
                    </ul>
                </div>
                <div class="row" style="display: table;
    margin: 0 auto;
    ">
					<div class="mainbox col-md-12 col-sm-12" >
	                    <div class="panel panel-default" style="border-radius: 0px; width: 400px">
	                        
	                        <div class="panel-body">
	                        	<div style="text-align:center;height:30px;"><label class="message" id="message">${message}</label></div>
								<form role="form" action="" method="GET" id="change-password-form">
									<fieldset>
										<div class="row">
											<div class="col-sm-12 col-md-12" style="margin:0px; ">
												<div class="form-group" id="group-input-message">
				                                    <label for="currentpassord" class="col-md-5 control-label" style="text-align:right">Current password</label>
				                                    <div class="col-md-7" style="height:69px;">
				                                    	<div class="col-md-12">
				                                        	<input type="password" class="form-control" name="currentPassword" id="currentPassword" placeholder="Current password">
				                                        </div>
				                                        <div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="currentPasswordError">${currentPasswordError}</span></div>			
				                                        </div>
				                                        
				                                    </div>
				                                </div>
				                                <div class="form-group" id="group-input-message">
				                                    <label for="newPassword" class="col-md-5 control-label" style="text-align:right">New password</label>
				                                    <div class="col-md-7" style="height:69px;">
				                                    	<div class="col-md-12">
				                                        	<input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="New Password">
				                                        </div>
				                                        <div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="newPasswordError">${newPasswordError}</span></div>			
				                                        </div>
				                                        
				                                    </div>
				                                </div>

				                                <div class="form-group" id="group-input-message">
				                                    <label for="confirmPassword" class="col-md-5 control-label" style="text-align:right">Confirm password</label>
				                                    <div class="col-md-7" style="height:69px;">
				                                    	<div class="col-md-12">
				                                        	<input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm password">
				                                        </div>
				                                        <div class="col-md-12">
				                                        	<div style="text-align:left;"><span class="message" id="confirmPasswordError">${confirmPasswordError}</span></div>		
				                                        </div>
				                                    </div>
				                                </div>
												
												<div class="form-group" style="text-align:center;">
													<input type="submit" class="m-btn blue" style="width:100px" value="Change">
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