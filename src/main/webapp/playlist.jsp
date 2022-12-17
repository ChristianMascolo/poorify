<%@ page import="playlist.PlaylistBean" %>
<%@ page import="playlist.AddedBean" %>
<section id="playlist">

  <% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

  <% if(playlist != null) { %>
    <h1><%= playlist.getTitle() %></h1>
    <% for(AddedBean added: playlist.getTracklist()) { %>
      <h2><%= added.getTrack().getTitle() %></h2>
    <% } %>
  <% } %>


</section>
