<%@ page import="playlist.PlaylistBean" %>

<% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

<div id="add-guests-menu">

  <input type="text" placeholder="Alias here..." onblur="searchGuests(this, <%= playlist.getId() %>)">

  <div id="guests-results">

  </div>

  <button onclick="hideGuestsMenu()">Cancel</button>

</div>