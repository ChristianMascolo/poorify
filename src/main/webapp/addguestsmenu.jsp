<%@ page import="playlist.PlaylistBean" %>
<%@ page import="profile.ProfileBean" %>

<% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

<div id="add-guests-menu">

  <input type="text" placeholder="Alias here..." onblur="searchGuests(this, <%= playlist.getId() %>)">

  <div id="guests-results">

  </div>

</div>