<%@ page import="profile.ProfileBean" %>
<%@ page import="profile.UserBean" %>
<%@ page import="profile.ArtistBean" %>

<% ProfileBean profile = (ProfileBean) session.getAttribute("Profile"); %>
<% UserBean user = profile.getRole() == ProfileBean.Role.USER ? (UserBean) profile : null; %>
<% ArtistBean artist = profile.getRole() == ProfileBean.Role.ARTIST ? (ArtistBean) profile : null; %>

<div id="edit-profile-menu">

  <form id="edit-profile-form" action="EditProfile" method="post" enctype="multipart/form-data">

    <h3>Edit Profile</h3>

      <input id="edit-alias" name="alias" type="text" value="<%= user != null ? user.getAlias() : artist.getAlias() %>" placeholder="Choose an alias">

      <input id="edit-password" name="password" type="text" value="" placeholder="Change your password">

      <input id="edit-cover" name="cover" type="file" accept="image/jpeg">
      <label for="edit-cover">Select a cover</label>

    <% if(user != null) { %>
      <input id="edit-isPublic" name="isPublic" type="checkbox">
      <label for="edit-isPublic"><%= user.isPublic() ? "Public" : "Private" %></label>
    <% } else { %>
      <textarea id="edit-bio" name="bio" rows="5" cols="50" maxlength="1024" required>Update your bio...</textarea>
    <% } %>

    <div id="edit-controls">
      <input type="submit" value="Save">
    </div>
  </form>

  <button onclick="deleteProfile()"><span>Delete Profile</span></button>

  <button onclick="hideProfileMenu()"><span>Cancel</span></button>

</div>