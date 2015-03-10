<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="
">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Detail post page</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" type="type/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="type/css" href="../css/style.css">
        <link rel="stylesheet" type="type/css" href="../css/m-styles.min.css">
        <link rel="stylesheet" type="text/css" href="../js/jquery-confirm/css/jquery-confirm.css" />

        <script type="text/javascript" src="../js/jquery-2.1.3.js"></script>
        <script type="text/javascript" src="../js/detectmobilebrowser.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/jquery.validate.js"></script>
        <script type="text/javascript" src="../js/jquery-confirm/js/jquery-confirm.js"></script>

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
                
                getUserInfo();

        		function getUserInfo() {
        			//show loading spinner image
        			$('#spinner').fadeIn();
        			$.ajax({
        				type : "GET",
        				url : "../mainpage/getUserInfo"
        			}).done(function(data) {
        				var data = jQuery.parseJSON(data);
        				console.log('log = ' + data.user);
        				//hide loading spinner image when ajax done
        				//check valid message form server
        				if (data.message) {
        					$('#message').text(data.message);
        				} else {
        					$('.blog-name').text(data.user.firstname + ' ' + data.user.lastname);
        					$('#userId').val(data.user.id);
        					$('#username').val(data.user.username);
        				}
        			}).fail(function() {
        				//hide loading spinner image
        				$('#message').text('Have some thing error--Try agian later');
        				$('#spinner').fadeOut();
        			});
        		}
                
                if(getUrlParameter('postId') && getUrlParameter('postId') != ''){
                	console.log('log postId = '+getUrlParameter('postId'));
                	$('#postId').val(getUrlParameter('postId'));
                	getPostInfo(getUrlParameter('postId'));
                }else{
                	console.log('log message');
                	$('#message').text('Invalid post Id');
                }

        		function getPostInfo(postId) {
        			//show loading spinner image
        			$('#spinner').fadeIn();
        			$.ajax({
        				type : "GET",
        				url : "getPostInfo",
        				data:{postId: postId}
        			}).done(function(data) {
        				console.log('log'+data);
        				if(data.message){
        					$('#message').text(data.message);
        				}else{
        					setPostData(data.data.post);
        				}
        				$('#spinner').fadeOut();
        			}).fail(function() {
        				//hide loading spinner image
        				$('#spinner').fadeOut();
        			});
        		}
        		
        		function setPostData(post){
        		   console.log('user id = '+$('input#userId').val());
        		   console.log('post user id = '+post.user.id);
        		   $('#post-content-withoud-comment').empty();
        	       $('#comment-tbody').empty();
        		   var postBody = '';
        		   postBody += '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >';
	               if(post.user.id == $('input#userId').val()){
	            	   postBody += '<label><a href="?postId='+post.id+'" >'+ post.title +'</a></label>'
	            	               + '<label style="  float: right;margin-right: 15%;"><a class="glyphicon glyphicon-pencil" href="../mainpage/editpost?postId='+post.id+'" ></a>'
	            	               +    '&nbsp;&nbsp;'
	                               +    '<a class="glyphicon glyphicon-trash" href="" id="deletePost_'+post.id+'" onClick="deletePost('+post.id+')" ></a>';
	            	               + '</label>';
	            		   
	               }else{
	            	   postBody +='<label><a href="?postId='+post.id+'" >'+ post.title +'</a></label>';
	               }
	               postBody += '</div>'
	                    + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">'
	                    +   '<p >Date create: ' + post.created_at + ', Date modified: ' + post.modified_at+ ', Author: <a href="../mainpage/userpage?userId='+post.user.id+'">'+post.user.username+'</a></p>'
	                    + '</div>'    
	                    + '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="color: black;">'
	                    + post.content    
	                	+ '</div>';
	                	
	                var commentBody = '';
	                var comments = post.comments;
	                if(comments){
	                
		                for (var i=0, size=comments.length; i<size; i++) {
			                commentBody += 
			                	'<tr>'
			                    	+'<td>'
				                    	+'<p id="contentComment_'+comments[i].id+'">'+comments[i].content+'</p>'
				                    	+ '<p >Date create: ' + comments[i].created_at + ', Date modified: ' + comments[i].modified_at+ ', Author: <a href="../userpage?userId='+comments[i].user.id+'">'+comments[i].user.username+'</a></p>'
				                	+'</td>'
				                	+'<td>'
				                	+'<p>';
				             if($('input#userId').val() == comments[i].user.id){
				            	 
				             
				          		commentBody +=    '<a class="glyphicon glyphicon-pencil" href="#" id="editComment_('+comments[i].id+')" onclick="editComment('+comments[i].id+')"></a>'
		                                +    '&nbsp;&nbsp;'
		                                +    '<a class="glyphicon glyphicon-trash" href="" id="deleteComment_'+comments[i].id+'" onClick="deleteComment('+comments[i].id+')" ></a>';
				             }
				             commentBody += '</p>'
				                	+'</td>'
			            		+'</tr>';
		                }
	                }
        			$('#post-content-withoud-comment').append(postBody);
        			$('#comment-tbody').append(commentBody);
        			
        		}
        	
        		
        		function getUrlParameter(sParam)
        		{
        		    var sPageURL = window.location.search.substring(1);
        		    var sURLVariables = sPageURL.split('&');
        		    for (var i = 0; i < sURLVariables.length; i++) 
        		    {
        		        var sParameterName = sURLVariables[i].split('=');
        		        if (sParameterName[0] == sParam) 
        		        {
        		            return sParameterName[1];
        		        }
        		    }
        		}
        		$('#add-comment-form').validate(
  				{
  					rules : {
  						contentComment : {
  							required : true,
  							minlength : 1,
  							maxlength : 500
  						}
  					},
  					messages : {
  						contentComment : {
  							required : "Comment's content is required",
  							maxlength : "Comment is invalid - Less than 500 characters"
  						}
  					},
  					highlight : function(element) {
  						$(element).closest('.form-group').addClass('has-error');
  					},
  					unhighlight : function(element) {
  						$(element).closest('.form-group').removeClass('has-error');
  					},
  					errorElement : 'span',
  					errorClass : 'help-block',
  					errorPlacement : function(error,
  							element) {
  						if (element.parent('.input-group').length) {
  							error.insertAfter(element.parent());
  						} else {
  							error.insertAfter(element);
  						}
  					},
  					submitHandler : function() {
  						$('#spinner').fadeIn();
  						event.preventDefault();
  				
  						var contentComment = $('#contentComment').val();
  						
  						var postId = $('input#postId').val();
  						
  						$.ajax(
  						{
  							type : "POST",
  							url : "comments/addcomment",
  							data : {
  								content : contentComment,
  								postId : postId
  							}
  						}).done(function(data) {											
  								var data = jQuery.parseJSON(data);								
  								if (data.message) {
  									$('#message').text(data.message);
  								}
  								$('#postId').val();
  			                	getPostInfo($('#postId').val());
  								$('#spinner').fadeOut();	
  						})
  						.fail(function() {
							$('#spinner').fadeOut();														
																
  						});
  					}

  				});
        		
        		$('#edit-comment-form').validate(
          				{
          					rules : {
          						contentCommentEdit : {
          							required : true,
          							minlength : 1,
          							maxlength : 500
          						}
          					},
          					messages : {
          						contentCommentEdit : {
          							required : "Comment's content is required",
          							maxlength : "Comment is invalid - Less than 500 characters"
          						}
          					},
          					highlight : function(element) {
          						$(element).closest('.form-group').addClass('has-error');
          					},
          					unhighlight : function(element) {
          						$(element).closest('.form-group').removeClass('has-error');
          					},
          					errorElement : 'span',
          					errorClass : 'help-block',
          					errorPlacement : function(error,
          							element) {
          						if (element.parent('.input-group').length) {
          							error.insertAfter(element.parent());
          						} else {
          							error.insertAfter(element);
          						}
          					} ,
          					submitHandler : function() {
          						
          						event.preventDefault();
          				
          						$('#spinner').fadeIn();
          						var contentComment = $('#contentCommentEdit').val();
          						
          						var commentEditId = $('input#commentEditId').val();
          						
          						$.ajax(
          						{
          							type : "POST",
          							url : "comments/editcomment",
          							data : {
          								content : contentComment,
          								commentId : commentEditId
          							}
          						}).done(function(data) {
          							console.log('data edit comment +'+data);
       								var data = jQuery.parseJSON(data);
       								
       								if (data.message) {
       									if(data.message == 211){
       										getPostInfo($('#postId').val());
       										$("#formComment").modal('toggle');
       										$('#message').text('Update comment success.');
       									}else{
       										$('#editCommentMessage').text(data.message);	
       									}
       									
       									
       								}
       								
       								$('#spinner').fadeOut();	
       							})
          						.fail(function() {
          							$('#editCommentMessage').text('Have some error--Try again later');
  									$('#spinner').fadeOut();														
  									console.log('fail');														
          						});
          					} 

          				});
            });
            function deleteComment(id){
    			event.preventDefault();
    			console.log('id = '+id);
    			
    			$('#spinner').fadeIn();
    			$.ajax({
    				type : "GET",
    				url : "comments/deleteComment",
    				data:{commentId: id}
    			}).done(function(data) {
    				console.log('log'+data);
    				if(data.message){
    					
    					if(data.message == 212){
    						$('#deleteComment_'+id).parents("tr").empty();
    						$('#message').text('Delete comment success.');
    					}else{
    						$('#message').text(data.message);	
    					}
    					
    				}	
    				$('#spinner').fadeOut();
    			}).fail(function() {
    				//hide loading spinner image
    				$('#spinner').fadeOut();
    			});
    		}
			function deletePost(id){
				event.preventDefault();
    			console.log('id = '+id);
    			$('#spinner').fadeIn();
    			$.ajax({
    				type : "GET",
    				url : "post/delete",
    				data:{postId: id}
    			}).done(function(data) {
    				console.log('log'+data);
    				var data = jQuery.parseJSON(data);
    				if(data.message){
    					if(data.message == 209){
    						$.alert({
								title : 'Alert!',
								content : "Delete post success"
										+ "--Redirecting to main page",
								confirm : function() {
									window.location.href = "../mainpage";
								}
							});
							console.log(data.redirect);
							setTimeout(function() {
								window.location.href = "../mainpage";
									},5000);
    					}else{
    						$('#message').text(data.message);	
    					}
    					
    				}	
    				$('#spinner').fadeOut();
    			}).fail(function() {
    				//hide loading spinner image
    				$('#spinner').fadeOut();
    			});
    		}
            
            function editComment(id) {
            	console.log('id = '+id);
            	$("#formComment").modal('show');
        		var oldContent = $('#contentComment_'+id).text();
        		$('#contentCommentEdit').text(oldContent);
        		$('#commentEditId').val(id);
        	};
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
                                    <li class="active"><a href="../mainpage">Main page</a></li>
                                    <li id="header-button"><a href="../mainpage/userpage">My blog</a></li>
                                    <li id="header-button"><a href="../mainpage/addpost">Add post</a></li>
                                </ul>
                                
                                <form class="navbar-form" action="../mainpage/searchUser" style=" display:inline-block;" method="get" id="search-form" name="search-form">
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
                                    <li id="header-button"><a href="../mainpage/profile" class="blog-name"></a></li>
                                    <li id="header-button"><a href="../logout">Logout</a></li>
                                </ul>    
                            
                        </div>
                    </div>
                    
                </nav>
            </div>
            
            <div class="row" id="body-content">

                <div class="row" style="margin-top:50px">
                    <ul class="breadcrumb" style="border-radius: 0px;">
                        <li><a href="#">Main page</a></li>
                        <li class="active"><a href="#">Detail post page</a></li>
                        
                    </ul>
                </div>

                <div class="row" id="blog-name" style="margin-bottom:20px;margin-top:10px">
                    <label>Detail post</label>    
                </div>
                
                
                
                <div class="row" style="height:400px;" id="post-content">
                    
                    <div style="text-align: center; height: 20px;">
						<label class="message" id="message">${message}</label>
						<input type="hidden" id="username">
						<input type="hidden" id="userId">
						<input type="hidden" id="postId">
					</div>
                    
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id = "post-content-withoud-comment">
	                    
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                            
                        </div>
                        <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
                            
                            <table class="table " id="dataTable" >
                                <colgroup>
                                    <col style="width:100%">
                                </colgroup>  
                                
                                <tbody id="comment-tbody">
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
                    	</div>
                    	<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
                    		<div class="form-group">   
		                        <form method="POST" action="" accept-charset="UTF-8"
		                            role="form" class="form-signin" id="add-comment-form">
		                            <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
		                                <input name="articles_id" id="postIdCommentHidden" type="text" value="${post.id}"
		                                    required hidden="true">
		                                <textarea class="panel-body" rows="auto" id="contentComment" name="contentComment"
		                                    style="width:100%;" placeholder="Your comment..." required></textarea>
		                            </div>
		                            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
		                                <input class="btn btn-primary " type="submit" id="submitComment" value="Add"
		                                    name="add" style="float: right" />
                                    </div>
		                        </form>
	                        </div>
                        </div>
                    </div>
                    
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
			<form method="POST" action="" id="edit-comment-form" accept-charset="UTF-8"
				name="edit-comment-form">
				<div style="text-align: center; height: 20px;">
						<label class="message" id="editCommentMessage">${editCommentMessage}</label>
					
				</div>
				<div id="formComment" class="modal fade form-group">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Edit Comment</h4>
							</div>
							<div class="modal-body">
								<input name="id" type="hidden" id="commentEditId" required
									>
								<textarea class="panel-body"
									placeholder="Your comment....!" id="contentCommentEdit" name="contentCommentEdit" style="width:100%"></textarea>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-default" data-dismiss="modal" id="buttonEditComment">Close</button>
								<button type="submit" class="btn btn-primary">Edit</button>
							</div>
						</div>
					</div>
				</div>
			</form>
        </div>
    </body>
</html>