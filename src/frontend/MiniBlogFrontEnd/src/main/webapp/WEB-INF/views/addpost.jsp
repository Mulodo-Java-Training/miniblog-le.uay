<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="
">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Add post Page</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/style.css">
        <link rel="stylesheet" href="../css/m-styles.min.css">
        <link rel="stylesheet" href="../css/jquery.cleditor.css" />

        <script src="../js/jquery-2.1.3.js"></script>
        <script src="../js/detectmobilebrowser.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/jquery.bootpag.min.js"></script>
        <script src="../js/tinymce/tinymce.min.js"></script>
        <script src="../js/jquery.validate.js"></script>


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <script type="text/javascript">
            tinymce.init({
                selector: "textarea",
                plugins: [
                        "autolink link image print preview",  
                    ],
                toolbar: "insertfile undo redo | styleselect | bold italic | link image | alignleft aligncenter alignright alignjustify | bullist numlist | outdent indent ",
                autosave_ask_before_unload: false,
                max_height: 200,
                min_height: 160,
                height : 180,
                onchange_callback: function(editor) {
        			tinyMCE.triggerSave();
        			$("#" + editor.id).valid();
        		}
             });
        	$(function() {
        		var validator = $("#add-post").submit(function(e) {
        			e.preventDefault();
        			$('#spinner').fadeIn();
        			tinyMCE.triggerSave();
        			var content = tinyMCE.activeEditor.getContent(); // get the content
        		    $('#content').val(content); // put it in the textarea
        		    var title = $('#title').val();
        		    console.log('content'+ content);
        		    $.ajax({
        				type:"POST",
        				url:"addpost",
        				data:{title: title,content: content}
        			}).done(function(data){
        				$('#spinner').fadeOut();
        				console.log('data='+data);
        				var data = jQuery.parseJSON(data);
        				if(data.message){
        					$('#message').text(data.message);
        				}
        			}).fail(function (){
        				$('#spinner').fadeOut();
        				$('#message').text("Have some error AJAX--Please try again later");
        			});
        		}).validate({
        			ignore: "",
        			rules: {
        				title: {
        					minlength: 1,
			                maxlength: 150,
			                required: true
        				},
        				content: {
			                required: true
        				},
        			},messages: {
        				title: {
        					required: "Title is required",
        					maxlength: " Invalid title - Less than 150 character"
        				},
						content:{
							required: "Content is required"
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
        			errorPlacement: function(label, element) {
        				// position error label after generated textarea
        				if (element.is("textarea")) {
        					label.insertAfter(element.next());
        				} else {
        					label.insertAfter(element)
        				}
        			}
        		});
        		validator.focusInvalid = function() {
        			// put focus on tinymce on submit validation
        			if (this.settings.focusInvalid) {
        				try {
        					var toFocus = $(this.findLastActive() || this.errorList.length && this.errorList[0].element || []);
        					if (toFocus.is("textarea")) {
        						tinyMCE.get(toFocus.attr("id")).focus();
        					} else {
        						toFocus.filter(":visible").focus();
        					}
        				} catch (e) {
        					// ignore IE throwing errors when focusing hidden elements
        				}
        			}
        		}
        	})
            $(document).ready(function(){
                if($.browser.mobile)
                {
                    $('#body-content').css('width','100%');
                    $('#add-post div div:first-child label').css('text-align','left');
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
     					console.log('log = '+data.user.id);
     					//hide loading spinner image when ajax done
     					//check valid message form server
     					if (data.message) {
     						$('#message').text(data.message);
     					} else {
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

        <div class="container" style="margin:0 auto;background-color:#EEEEEE; min-height:100%">
                
            <div class="row" id="header" >
                <!-- Fixed navbar -->
                <nav class="navbar navbar-default navbar-fixed-top" id="header">
                    <div class="container" >
                        <div class="navbar-header" id="header-button">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" >
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="../mainpage">Mini Blog</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse" style="background-color: #9fc78a;">
                            
                                <ul class="nav navbar-nav">
                                    <li id="header-button"><a href="../mainpage">Main page</a></li>
                                    <li id="header-button"><a href="../mainpage/userpage">My blog</a></li>
                                    <li class="active"><a href="../mainpage/addpost">Add post</a></li>
                                </ul>
                                
                                <form class="navbar-form" style=" display:inline-block;" method="get" action="../mainpage/searchUser" id="search-form" name="search-form">
                                    <div class="input-group" style="width:270px;">
                                        <input type="text" style="border-radius: 0px;" class="form-control" placeholder="username, firstname, lastname" id="name"  name="name" value="">
                                        <div class="input-group-btn">
                                            <button type="submit" style="border-radius: 0px;" class="btn btn-danger">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </div>
                                    </div>
                                </form>       
                                
                            
                                <ul class="nav navbar-nav navbar-right" >
                                    <li id="header-button"><a href="profile" class="blog-name"></a></li>
                                    <li id="header-button"><a href="../logout">Logout</a></li>
                                </ul>    
                            
                        </div>
                    </div>
                    
                </nav>
            </div>
            
            <div class="row" id="body-content">

                <div class="row" style="margin-top:50px">
                    <ul class="breadcrumb" style="border-radius: 0px;">
                        <li><a href="../mainpage">Main page</a></li>
                        <li class="active"><a href="">Add post</a></li> 
                    </ul>
                </div>
                
                <div class="row">
                    <form method="POST" name="add-post" id="add-post">
                    	<div style="text-align:center;height:30px;"><label class="message" id="message">${message}</label></div>
                        <div class="row form-group" style="height:50px">
                            <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                                <label>Title</label>
                            </div>
                            <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10" >
                                <input type="text" id="title" name="title">
                            </div>
                        </div>
                        <div class="row form-group" style="margin-top:10px;height:50px;">
                            <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                                <label>Content</label>
                            </div>
                            <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10">
                                <textarea id="post-description" name="content" id="content"
                                     placeholder="Type description here..."></textarea>
                            </div>
                        </div>
                        <div class="row" style="text-align:center">
                            <div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
                            </div>
                            <div class="col-xs-12 col-sm-10 col-md-10 col-lg-10">
                                <input type="submit" class="m-btn blue" style="width:100px" value="Add post">
                                <input id="btn btn-danger" class="m-btn blue" style="width:100px" type="reset" value="Clear">
                            </div>
                        </div>
                    </form>        
                </div>
            </div>
            <div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
					<div id="spinner" style="display:none">
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