<%-- 
    Document   : AccountView
    Created on : Jun 5, 2020, 8:09:39 AM
    Author     : DuongLee
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% ArrayList<Account> listacc = (ArrayList<Account>)session.getAttribute("listacc"); %>
        <h1>Hello World!</h1>
        <% if(listacc != null) { %>
            <div><%= listacc.size() %></div>
        <% } %>
    </body>
</html>
