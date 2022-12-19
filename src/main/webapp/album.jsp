<%@ page import="album.AlbumBean" %>
<%@ page import="track.TrackBean" %>
<section id="playlist">

  <% AlbumBean album = (AlbumBean) session.getAttribute("Album"); %>

  <% if(album != null) { %>
  <h1><%= album.getTitle() %></h1>
  <% for(TrackBean track: album.getTracklist()) { %>
  <h2><%= track.getTitle() %></h2>
  <% } %>
  <% } %>


</section>