<%@ page import="playlist.PlaylistBean" %>
<%@ page import="playlist.AddedBean" %>
<%@ page import="profile.ProfileBean" %>
<section id="playlist">

  <% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

  <% if(playlist != null) { %>
    <h1><%= playlist.getTitle() %></h1>
    <% for(AddedBean added: playlist.getTracklist()) { %>
      <h2><%= added.getTrack().getTitle() %></h2>
    <% } %>

    <% if(playlist.getHost().getId() == ((ProfileBean) session.getAttribute("Profile")).getId()) { %>
      <button onclick="deletePlaylist(<%= playlist.getId() %>)">Delete</button>
    <% } %>

  <% } %>


</section>
