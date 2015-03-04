<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Home Page</title>

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="type/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="type/css" href="css/style.css">
		<link rel="stylesheet" type="type/css" href="css/m-styles.min.css">

		<script src="js/jquery-2.1.3.js"></script>
		<script src="js/detectmobilebrowser.js"></script>
		<script src="js/jquery.bootpag.min.js"></script>
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
					$('#body-content').css('width','100%');
				}
				$('#login-form').validate({
					rules: {
						username: {
			                required: true
			            },
			            password: {
			                required: true
			            }
			        },
					messages: {
						
						username: {
							required: "Username is required"
							
						},
						password: {
							required: "Password is required"
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
			        }
				});
			});
		</script>
	</head>
	<body>

		<div class="container" style="margin:0 auto;background-color: #EEEEEE; min-height: 100%;height: 100%;">
			
			<div class="row" style="height:51px;">
				<!-- Fixed navbar -->
			    <nav class="navbar navbar-default navbar-fixed-top" id="header" >
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
			            <li class="active"><a href="#">Login</a></li>
			            <li id="header-button"><a href="register">Register</a></li>
			          </ul>
			        </div>
			      </div>
			    </nav>
			</div>
	
			<div class="row" id="login-form">
				<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">   
					<div class="panel panel-default" style="border-radius: 0px;">
						<div class="panel-heading" style="border-radius: 0px;background-color: #9fc78a">
                        	<div class="panel-title">Login</div>        
                    	</div>  
						<div class="panel-body">
							<form role="form" action="" method="POST" id="login-form">
								<fieldset>
									<div class="row">
										<div class="center-block">
											<img class="profile-img"
												src="images/logo.png" alt="">
										</div>
									</div>
									<div class="row">
										<div style="text-align:center;"><label class="message">${message}</label></div>
										<div class="col-sm-12 col-md-10  col-md-offset-1 " >
											<div class="form-group" id="group-input-message">
												<div class="col-md-12">
													<div class="input-group">
														<span class="input-group-addon">
															<i class="glyphicon glyphicon-user"></i>
														</span> 
														<input class="form-control" placeholder="Username" name="username" type="text" autofocus>
													</div>
												</div>
												<div class="col-md-12">
													<p>Error message</p>
												</div>
											</div>
											<div class="form-group" id="group-input-message">
												<div class="col-md-12">
													<div class="input-group">
														<span class="input-group-addon">
															<i class="glyphicon glyphicon-lock"></i>
														</span>
														<input class="form-control" placeholder="Password" name="password" type="password" value="">
													</div>
												</div>
												<div class="col-md-12">
													<p>Error message</p>
												</div>
											</div>
											<div class="form-group" style="text-align:center;">
												<input type="submit" class="m-btn blue" style="width:150px" value="Sign in">
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
