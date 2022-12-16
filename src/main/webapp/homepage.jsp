<%@ page import="profile.*" %>
<%@ page import="playlist.*" %>

<h1>HOMEPAGE</h1>
<input type="button" value="COLDPLAY" onclick="play(5)">
<input type="button" value="JAMES BLUNT" onclick="play(11)">

<% UserBean user = (UserBean) session.getAttribute("Profile"); %>
<% if(user != null) { %>
    <% for(PlaylistBean p: user.getPlaylists()) { %>
        <h2><%= p.getTitle()%></h2>
        <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  alt="">
    <% } %>
<% } %>