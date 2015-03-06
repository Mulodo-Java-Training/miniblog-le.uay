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
        <link rel="stylesheet" type="type/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="type/css" href="css/style.css">
        <link rel="stylesheet" type="type/css" href="css/m-styles.min.css">

        <script src="js/jquery-2.1.3.js"></script>
        <script src="js/detectmobilebrowser.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.bootpag.min.js"></script>

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
            });
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
                            <a class="navbar-brand" href="#">Mini Blog</a>
                        </div>
                        <div id="navbar" class="navbar-collapse collapse" style="background-color: #9fc78a;">
                            
                                <ul class="nav navbar-nav">
                                    <li id="header-button"><a href="#">Main page</a></li>
                                    <li class="active"><a href="#">My blog</a></li>
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
                        <li><a href="mainpage.html">Main page</a></li>
                        <li class="active"><a href="#">User page</a></li>
                    </ul>
                </div>

                

                <div class="row">
                    <form method="get" id="search-form" name="search-post-form">
                        <div class="input-group"  style="width:100%;margin: 0 auto; display: 0 auto; ">
                            <input type="text" style="border-radius: 0px;" class="form-control" placeholder="title, content of post" id="query"  name="query" value="" >
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-danger" style="border-radius: 0px;">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="row" id="blog-name">
                    <label>Uay Le U Blog</label>    
                </div>  

                <div class="row" id="content-posts">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 1</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 2</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 3</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 3</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 5</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 6</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 7</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 8</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 9</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="post-style">
                        <p ><a href="#">Post 10</a></p>
                        <p >Date : 2015/02/03 12:15:20, Author: <a href="#">Uay Le U</a></p>
                        <p>Software prototyping is the activity of creating prototypes of software applications, i.e., incomplete versions of the software program being developed. It is an activity that can occur in software development and is comparable to prototyping as known from other…</p>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="pagination">
                        <ul class="pagination">
                            <li><a href="#">&laquo;</a></li>
                            <li><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">&raquo;</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>