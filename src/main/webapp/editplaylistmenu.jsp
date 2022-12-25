<%@ page import="playlist.PlaylistBean" %>
<%@ page import="profile.ProfileBean" %>

<% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>
<% boolean owned = playlist.getHost().getId() == ((ProfileBean) session.getAttribute("Profile")).getId(); %>

<div id="edit-playlist-menu">

  <form id="edit-form" action="EditPlaylist" method="post" enctype="multipart/form-data">

    <h3>Edit Playlist</h3>
    <input id="edit-id" type="hidden" name="id" value="0">

    <input id="edit-title" name="title" type="text" value="<%= playlist.getTitle() %>" placeholder="Add a title">

    <input id="edit-cover" name="cover" type="file" accept="image/jpeg">
    <label for="edit-cover">Select a cover</label>

    <input id="edit-isPublic" name="isPublic" type="checkbox">
    <label for="edit-isPublic"><%= playlist.isPublic() ? "Public" : "Private" %></label>

    <input id="edit-isCollaborative" name="isCollaborative" type="checkbox">
    <label for="edit-isCollaborative"><%= playlist.isCollaborative() ? "Collaborative" : "Single" %></label>

    <div id="edit-controls">
      <input type="submit" value="Save">
    </div>
  </form>

  <% if(owned && playlist.isCollaborative()) { %>
    <button onclick=""><span>Add guest</span></button>
  <% } %>

  <% if(owned) { %>
    <button onclick="deletePlaylist(<%= playlist.getId() %>)"><span>Delete playlist</span></button>
  <% } %>

  <button onclick="hideEditMenu()"><span>Cancel</span></button>

</div>