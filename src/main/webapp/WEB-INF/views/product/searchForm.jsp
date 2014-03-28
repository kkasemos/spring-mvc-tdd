<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Product Search</title>
<!-- jquery 2.0.3 produce an error when running in the HtmlUnitDriver -->
<script language="JavaScript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-2.0.3.min.js"></script>
 
</head>
<body>
	<form id="searchForm">
		<input id="q" type="text"/>
		<input id="searchButton" value="search" type="button">
	</form>
	<div id="searchResults">
	</div>
	<script language="JavaScript">
		
		$(document).ready(
			function() {
				$('#searchButton').click(sendSearchRequest);
			}		
		);		
		function sendSearchRequest() {
			$.get(
				'${pageContext.servletContext.contextPath}/product/search',
				{ q: $('#q').val() },
				function(data) {
					var searchResultsElem = $('#searchResults');

					$.each(data.products, function(index, product) {
				     	searchResultsElem.append('<li>' + product.name + '</li>');
					});
				},
				"json" // returned data type
			);
		}
		
	</script>
</body>
</html>