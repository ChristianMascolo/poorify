<%@ page import="navigation.ResultsContainer" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="profile.UserBean" %>
<%@ page import="playlist.PlaylistBean" %>
<section id="searchpage">

  <% ResultsContainer results = (ResultsContainer) session.getAttribute("Results"); %>

  <% if(!results.isEmpty()) { %>

    <section class="display-sections" id="artists-results">
      <h1>Artists</h1>
      <% for(ArtistBean a: results.getArtists()) { %>
      <div>
        <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + a.getId() + ".jpg"%>"  alt="" onclick="navToArtist(<%= a.getId() %>, true)">
        <p>
          <span class="title" onclick="navToArtist(<%= a.getId() %>, true)"><%= a.getAlias() %></span>
          <br>
          <span class="host"></span>
        </p>
      </div>
      <% } %>
    </section>

    <section class="display-sections" id="user-results">
      <h1>Users</h1>
      <% for(UserBean u: results.getUsers()) { %>
      <div>
        <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + u.getId() + ".jpg"%>"  alt="" onclick="navToUser(<%= u.getId() %>, true)">
        <p>
          <span class="title" onclick="navToUser(<%= u.getId() %>, true)"><%= u.getAlias() %></span>
          <br>
          <span class="host"></span>
        </p>
      </div>
      <% } %>
    </section>

    <section class="display-sections" id="playlist-results">
      <h1>Playlists</h1>
      <% for(PlaylistBean p: results.getPlaylists()) { %>
      <div>
        <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  alt="" onclick="navToPlaylist(<%= p.getId() %>, true)">
        <p>
          <span class="title" onclick="navToPlaylist(<%= p.getId() %>, true)"><%= p.getTitle() %></span>
          <br>
          <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
        </p>
      </div>
      <% } %>
    </section>

  
  <% } %>


</section>