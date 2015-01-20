<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Demo spring mvc  4, hibernate 4.3.5</title>
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="resources/css/style.css" rel="stylesheet">
	
	<script src="resources/js/jquery-2.1.3.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/jquery.bootpag.min.js"></script>
	<script src="resources/js/detectmobilebrowser.js"></script>
	<script type="text/javascript">
	
		
	
	</script>
	<script type="text/javascript">
	 
		$(document).ready(function(){
			

			getListPersons(null, null, null, 1);
			

			function getListPersons(id, name, country, pageNum){
				$('#id').text(id);
				$('#name').text(name);
				$('#country').text(country);

				$('#spinner').fadeIn();
				$.ajax({
					type:"GET",
					url:"person/",
					data: {id: id, name: name, country: country, pageNum: pageNum}
				}).done(function(data){

					$('#spinner').fadeOut();

					if(data.message){
						$('#message').text(data.message);
						$("tbody").children().remove();
						$("#infor-data").empty();
						$("#page-selection").empty();
					}else{
						
						$('#message').text("");
						console.log("page size = " + data.listPersons[0].name);
						setDataTable(data.listPersons);
						setInfoData(data.totalAnimalByType, data.limitPage, data.pageNum, data.pageSize);
						setPagination(data.pageSize, data.pageNum);
					}
				}).fail(function (){

					$('#spinner').fadeOut();

					$('#message').text("Have some error--Please try agian later");

					$("tbody").children().remove();
					$("#infor-data").empty();
					$("#page-selection").empty();
				});			
			}
			
			function addPerson(id, name, country){
				console.log("name ="+name);
				console.log("country ="+country);
				$.ajax({
					type:"POST",
					url:"person/add",
					data:{id: id, name: name, country: country}
				}).done(function(data){
					
					console.log(data);
					console.log(data.message);
					if(data.message){
						$('#message').text(data.message);
					}else{
						var id = $('#id').text();
			        	var name = $('#name').text();
			        	var country = $('#country').text();
			        	getListPersons(id, name, country, 1);
					}        	
				}).fail(function (){
					$('#message').text("Have some error AJAX--Please try again later");
				});
				
			}
			
			$('#buttonSubmit').click(function(){
				var name = $('#nameInput').val();
				var country = $('#countryInput').val();
				addPerson(null,name,country);
			});
					

			function setDataTable(listPersons){
				$("tbody").children().remove();
				var body = '';
				for (var i=0, size=listPersons.length; i<size; i++) {

					body += '<tr><td >'
			  			 + listPersons[i].id					             
			             + '</td><td>'
			             + listPersons[i].name
			             + '</td><td>'
			             + listPersons[i].country
			             + '</td><td>'
			             + '<a href="person/edit/' + listPersons[i].id + '" >Edit</a>'
			             + '</td><td>'
			             + '<a href="person/delete/' + listPersons[i].id + '" >Delele</a>'
			             + '</td></tr>';
				}

				$('#dataTable').append(body);
			}	
			

			function setInfoData(totalAnimal, limitPage, page, totalPage){
				if(page*limitPage >= totalAnimal){
					$("#infor-data").text("Current page "+ page + " of " + totalPage + ", from "+ ((page-1)*limitPage+1) +" to " + totalAnimal + " of " + totalAnimal);
				}else{
					$("#infor-data").text("Current page "+ page + " of " + totalPage + ", from "+ ((page-1)*limitPage+1) +" to " + page*limitPage + " of " + totalAnimal);	
				}
				 
			}
			
 
			function setPagination(totalPage, page){
				$('#page-selection').bootpag({
		            total: totalPage,
		            page : page,
		            maxVisible: 5
		        });
			}
			

			$('#page-selection').bootpag().on("page", function(event, num){
	        	var id = $('#id').text();
	        	var name = $('#name').text();
	        	var country = $('#country').text();
	        	getListPersons(id, name, country,num);
	        });
			

			$('#table').ready(function(){
				if($.browser.mobile)
				{
 
					$('#table').css('width','100%');
					$('#infor-data').css('text-align','center');
					$('#page-selection').css('text-align','center');
				}
			});
	
		});
	</script>


</head>
<body>

	<div class="container">
		<div class="row" id="header">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
				<h1 style="text-align:center;">Demo spring mvc  4, hibernate 4.3.5</h1>
			</div>
		</div>
		<div class="row" id="menu">
			<div class="row">
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " id="lable">
					Name:
				</div>
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " id="inputType">
					<input type="text" id="nameInput" class="input">
				</div>
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " id="lable">
					Country:
				</div>
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " id="inputType">
					 <input type="text" id="countryInput" class="input">
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " id="buttonDiv">
				<input type="button" value="Add" id="buttonSubmit" class="button">
			</div>
			<div id="id" style="display:none;"></div>
			<div id="name" style="display:none;"></div>
			<div id="country" style="display:none;"></div>
			
		</div>
		
		<div class="row"> <p id="message" style="color: red;text-align:center;"></p> </div>
		
		<div class="row" id="table">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
					<div style="overflow: auto;">
					<table class="table table-striped table-bordered " id="dataTable" style="height:400px;">
 
					    <thead>
					        <tr>
					            <th width="80">Person ID</th>
						        <th width="120">Person Name</th>
						        <th width="120">Person Country</th>
						        <th width="60">Edit</th>
						        <th width="60">Delete</th>
					        </tr>
					    </thead>
					   	<tbody>
					   		
					   	</tbody>
					</table>
					</div>
				</div>
			</div>
			<div class="row" id="infor-table">
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 " >
					<div id="infor-data"></div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 " >						
					<div id="page-selection"></div>
				</div>
			</div>
							
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
				<div id="spinner" style="display:none">
					<div id="spinnerContent">
						<img src="resources/img/spinner.gif">
					</div>
					<div id="spinnerExp"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>