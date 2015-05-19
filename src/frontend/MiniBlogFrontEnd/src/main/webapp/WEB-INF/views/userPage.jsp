<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>User Page</title>

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
            $(document).ready(function(){    
                if($.browser.mobile)
                {
                    $('#body-content').css('width','100%');
                }
                $('#buttonSearchPost').click(function(e){
                	
        			e.preventDefault();
        			console.log('user id= '+ $('input#userId').val());
        			if(getUrlParameter('userId')){
            			getListPost(1, getUrlParameter('userId'), $('#description').val());
            		}else{
            			getListPost(1, $('input#userId').val(), $('#description').val());
            		}
        			
        		});
        		
    			getUserInfo();     	
        		
        		

        		//handle click on pagination
        		$('#page-selection').bootpag().on("page",
        				function(event, num) {
        					var userId = $('#userId').text();
        					var description = $('#description').text();
        					getListPost(num, userId, description);
        		});
        		
            });
            
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
 						$('#username').val(data.user.username);
 						$('#userStatus').val(data.user.status);
 						$('#userId').val(data.user.id);
 						$('#firstname').val(data.user.firstname);
 						$('#lastname').val(data.user.lastname);
 						$('#userEmail').val(data.user.email);
 						$('#current-blog-name').text(data.user.firstname + ' ' + data.user.lastname);
 						loadPage();
 					}
 				}).fail(function() {
					//hide loading spinner image
					$('#spinner').fadeOut();
   				});
    		}
    		
    		function loadPage(){
        		//load ajax when load page
        		if(getUrlParameter('userId')){
        			$('#userIdHidden').text(getUrlParameter('userId'));
        			getListPost(1,getUrlParameter('userId'), '');	
        		}else{
        			console.log('user id= '+ $('input#userId').val());
        			getListPost(1,$('#userId').val(), '');
        		}
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
    		

    		//get list animal function
    		function getListPost(pageNum, userId, description) {
    			//set hidden searching animal type
    			$('#userIdHidden').text(userId);
    			//show loading spinner image
    			$('#spinner').fadeIn();
    			$.ajax({
   					type : "GET",
   					url : "getAllPostForUser",
   					data : {
   						pageNum : pageNum,
   						userId : userId,
   						description: description
   					},
   					}).done(function(data) {
   						var data = jQuery.parseJSON(data);
   						console.log('log = '+data);
   						//hide loading spinner image when ajax done
   						$('#spinner').fadeOut();
   						//check valid message form server
   						if (data.message) {
   							$('#message').text(data.message);
   						} else {
   							//if have no message, jquery set data to table, hide message
   							$('#message').text("");
   							setDataTable(data.listPost);
   							setInfoData(
   									data.totalRow,
   									data.limitRow,
   									data.pageNum,
   									data.totalPage);
   							setPagination(
   									data.totalPage,
   									pageNum, userId);
   						}
   					}).fail(function() {
   						//hide loading spinner image
   						$('#spinner').fadeOut();
   						//set message error
   						$('#message')
   								.text(
   										"Have something error--Please try agian later");
   						//remove data in table
   						$("#content-posts").empty();
   						$("#infor-data").empty();
   						$("#page-selection").empty();
   				});
    		}

    		//build hmlt data in table
    		function setDataTable(listPost) {
    			$("#content-posts").empty();
    			$('.blog-name').text(listPost[0].user.firstname + ' ' + listPost[0].user.lastname
    					+ '\'s Blog' );
    			var body = '';
    			for (var i = 0, size = listPost.length; i < size; i++) {

    				body += '<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">';
    					if(listPost[i].user.id == $('input#userId').val()){
							body += '<label><a href="detailpost?postId='+listPost[i].id+'" >'+ listPost[i].title +'</a></label>'
			            	               + '<label style="  float: right;margin-right: 15%;"><a class="glyphicon glyphicon-pencil" href="../mainpage/editpost?postId='+listPost[i].id+'" ></a>'
			            	               +    '&nbsp;&nbsp;'
			                               +    '<a class="glyphicon glyphicon-trash" href="" id="deletePost_'+listPost[i].id+'" onClick="deletePost('+listPost[i].id+')" ></a>'
			            	               + '</label>';
			            		   
		               	}else{
		            	   body +='<label><a href="detailpost?postId='+listPost[i].id+'" >'+ listPost[i].title +'</a></label>';
		            	}
    					body += '<p id="postInfo">Date created : '
    						+ listPost[i].created_at
    						+ ', Date modifed: '
    						+ listPost[i].modified_at
    						+ ', Author: <a href="">'
    						+ listPost[i].user.username
    						+ '</a></p>'
    						+ '<p>'+listPost[i].content+'</p>'
    						+ '</div>'
    			}

    			$('#content-posts').append(body);
    		}

    		//set information of data in table
    		function setInfoData(totalRow, limitRow, page,
    				totalPage) {
    			if (page * limitRow >= totalRow) {
    				$("#infor-data").text(
    						"Current page " + page + " of "
    								+ totalPage + ", from "
    								+ ((page - 1) * limitRow +1)
    								+ " to " + totalRow + " of "
    								+ totalRow);
    			} else {
    				$("#infor-data").text(
    						"Current page " + page + " of "
    								+ totalPage + ", from "
    								+ ((page - 1) * limitRow + 1)
    								+ " to " + page * limitRow
    								+ " of " + totalRow);
    			}

    		}

    		//set pagination to div page-selection 
    		function setPagination(totalPage, pageNum, userId) {
    			$('#page-selection').bootpag({
    				total : totalPage,
    				page : pageNum,
    				maxVisible : 5
    			});
    		}
            
            function deletePost(id){
				event.preventDefault();
    			console.log('id = '+id);
    			$('#spinner').fadeIn();
    			$.ajax({
    				type : "GET",
    				url : "../mainpage/post/delete",
    				data:{postId: id}
    			}).done(function(data) {
    				console.log('log'+data);
    				var data = jQuery.parseJSON(data);
    				if(data.message){
    					if(data.message == 209){
    						$('#message').text('Delete post success');
    						loadPage()
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
            
        </script>
    </head>
    <body>

        <div class="container" style="margin:0 auto;background-color:#EEEEEE; ">
                
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
                                    <li class="active"><a href="">My blog</a></li>
                                    <li id="header-button"><a href="../mainpage/addpost">Add post</a></li>
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
                                    <li id="header-button"><a href="../mainpage/profile" id="current-blog-name"></a></li>
                                    <li id="header-button"><a href="../logout">Logout</a></li>
                                </ul>    
                            
                        </div>
                    </div>
                    
                </nav>
            </div>
            
            <div class="row" id="body-content">
				<div class="row breadcrumb-row" style="margin-top: 50px">
					<ul class="breadcrumb" style="border-radius: 0px;">
						<li class="active"><a href="../mainpage">Main page</a></li>
						<li class="active"><a href="">User page</a></li>
					</ul>
				</div>
				
				<div class="row">
					<div style="text-align: center; height: 30px;">
								<label class="message" id="message">${message}</label>
							</div>
					<form method="get" id="search-post-form" name="search-post-form">
						<div class="input-group"
							style="width: 100%; margin: 0 auto; display: 0 auto;">
							<input type="text" style="border-radius: 0px;"
								class="form-control" placeholder="title, content of post"
								id="description" name="description" value="">
							<div class="input-group-btn">
								<button type="submit" class="btn btn-danger" id="buttonSearchPost"
									style="border-radius: 0px;">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</div>
						</div>
					</form>
				</div>
				
				<div class="row" id="blog-name">
                    <label class="blog-name" id="blog-name"></label>    
                </div>
	
				<div class="row" id="content-posts">
	
				</div>
				<div class="row" id="infor-table">
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 ">
						<div id="infor-data"></div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 ">
						<div id="page-selection"></div>
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
			<div class="row">
				<input type="hidden" id="username">
				<input type="hidden" id="firstname">
				<input type="hidden" id="lastname">
				<input type="hidden" id="userStatus">
				<input type="hidden" id="userEmail">
				<input type="hidden" id="userId">
				<input type="hidden" id="userIdHidden">
				
			</div>
        </div>
    </body>
</html>