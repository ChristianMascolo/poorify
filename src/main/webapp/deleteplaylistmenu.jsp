<%@ page import="playlist.PlaylistBean" %>

<% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

<div id="delete-playlist-menu">

  <p>Are you sure you want to delete this playlist? (THE DATA )</p>

  <button onclick="deletePlaylist(<%= playlist.getId() %>)">Yes, proceed</button>

  <button onclick="hideDeleteMenu()">Cancel</button>

</div>