<%--
  Created by IntelliJ IDEA.
  User: Android
  Date: 27.09.2016
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <script>
    var a = new XMLHttpRequest();
    a.open('POST', 'http://localhost:8080/login', true);
    a.send("Hello, World");
  </script>
  </body>
</html>
