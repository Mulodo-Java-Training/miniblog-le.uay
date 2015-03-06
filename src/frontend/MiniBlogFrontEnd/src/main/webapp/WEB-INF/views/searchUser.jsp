<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="
">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>User Page</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" type="type/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="type/css" href="../css/style.css">
        <link rel="stylesheet" type="type/css" href="../css/m-styles.min.css">

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
                
                if($('#nameHidden').val() != ""){
                	searchUser($('#nameHidden').val());
                }
                
                $('#buttonSubmit').click(function(e){
                	e.preventDefault();
                	console.log('name = '+$('#name').val())
    				if($('#name').val() != ""){
    					searchUser($('#name').val())
    				}
					
    			});
                
                function searchUser(name){
        			console.log("name ="+name);
        			$.ajax({
        				type:"GET",
        				url:"searchUserAJAX",
        				data:{name: name}
        			}).done(function(data){
        				var data = jQuery.parseJSON( data );
        				if(data.data.listUser){
        					setDataTable(data.data.listUser);
        				}
        			}).fail(function (){
        				$('#message').text("Have some error AJAX--Please try again later");
        			});
        		}
                
                function setDataTable(listUsers){
        			$("tbody").children().remove();
        			var body = '';
        			for (var i=0, size=listUsers.length; i<size; i++) {

        				body += '<tr><td style="text-align:right">'
        		  			 + '<a href="../mainpage/userpage">'+ listUsers[i].username +'</a>'					             
        		             + '</td><td>'
        		             + 	'  <p>Username: '+listUsers[i].username+'</p>'
	                         +  '  <p>First name:'+listUsers[i].firstname+'</p>' 
        		             + '</td><td>'
        		             + 	'  <p>Email: '+listUsers[i].email+'</p>'
	                         +  '  <p>Last name:'+listUsers[i].lastname+'</p>' 
        		             + '</td></tr>';
        			}

        			$('#userDataTable').append(body);
        			$('#userDataTable tr, td p:nth-child(even)').css('color','black');
        		}
            });
        </script>
    </head>
    <body>

        <div class="container" style="margin:0 auto;background-color:#EEEEEE; height:100%; min-height:100%">
                
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
                                    <li id="header-button"><a href="#">My blog</a></li>
                                    <li id="header-button"><a href="#">Add post</a></li>
                                </ul>
                                
                                <form class="navbar-form" style=" display:inline-block;" method="" id="search-form" name="search-form">
                                    <div class="input-group" style="width:270px;">
                                    	<input type="hidden" id="nameHidden" name="nameHidden" value="${name}">
                                        <input type="text" style="border-radius: 0px;" class="form-control" placeholder="username, firstname, lastname" id="name"  name="name" value="">
                                        <div class="input-group-btn">
                                            <button type="submit" style="border-radius: 0px;" id="buttonSubmit" class="btn btn-danger">
                                                <span class="glyphicon glyphicon-search"></span>
                                            </button>
                                        </div>
                                    </div>
                                </form>      
                                
                            
                                <ul class="nav navbar-nav navbar-right" >
                                    <li id="header-button"><a href="profile">Uay Le U</a></li>
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
                        <li class="active"><a href="#">Search user page</a></li>
                        
                    </ul>
                </div>

                <div class="row" id="blog-name" style="margin-bottom:20px;margin-top:10px">
                    <label>Search result</label>    
                </div>
                
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
                        <div style="height:400px;overflow: auto;">
                        <table class="table " id="userDataTable" >
                            <colgroup>
                                <col style="width:25%">
                                <col style="width:25%">
                                <col style="width:50%">
                            </colgroup>  
                            
                            <tbody>
                                

                            </tbody>
                        </table>
                        </div>
                    </div>
            </div>
            </div>
        </div>
    </body>
</html>