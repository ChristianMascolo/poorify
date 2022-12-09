<%@ page import="profile.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Poorify | Home</title>
</head>
<body>
    <% UserBean profile = (UserBean) (ProfileBean) session.getAttribute("Profile"); %>
    <h2><%= profile.getAlias()%></h2>
    <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + profile.getId() + ".jpg" %>" width="1280">
</body>
</html>
