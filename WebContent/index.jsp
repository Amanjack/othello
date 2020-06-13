<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.Reversi" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>オセロ</title>
		<link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
	    <header>
	        <h1>オセロ</h1>
	        <p>はじめて開発したゲーム</p>
	        <h2>${ reversi.msg }</h2>
		</header>
		<main>
			<form action="/othello/Servlet" method="get">
		        <table>
		        	<tr>
		        		<th></th>
		        		<th>1</th>
		        		<th>2</th>
		        		<th>3</th>
		        		<th>4</th>
		        		<th>5</th>
		        		<th>6</th>
		        		<th>7</th>
		        		<th>8</th>
		        		<th></th>
		        	</tr>
		        	<%
		    		int i = 0;
		    		int j = 0;
		    		%>
					<c:forEach var="sarr" items="${ reversi.board }">
			        	<tr>
		        		<th><%= i+1 %></th>
		        		<c:forEach var="s" items="${ sarr }">
		        			<% String set = i+"_"+j; %>
			        		<td><input type="submit" value="${ s }" name="<%= set %>" class="set"></td>
			        		<% j++; %>
			        	</c:forEach>
			        	</tr>
			        	<%
			        	i++;
			        	j = 0;
			        	%>
			        </c:forEach>
		        </table>
				<p>BLACK : ${ reversi.cnt_black }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WHITE : ${ reversi.cnt_white }</p>
				<div>
					<input type="submit" value="待った" name="stop" class="stop"/>
				</div>
				<div>
					<input type="submit" value="パス" name="pass" class="pass"/>
				</div>
				<div class="">
					<button type="submit" value="最初から" name="reset" class="reset">最初<br>から</button>
				</div>
			</form>
        </main>
        <footer>
			<p>by Eisuke Hishikawa</p>
		</footer>
    </body>
</html>