<%@ page import="album.AlbumBean" %>
<%@ page import="track.TrackBean" %>
<%@ page import="profile.ArtistBean" %>

<section id="artist">

  <% ArtistBean artist = (ArtistBean) session.getAttribute("Artist"); %>

  <% if(artist != null) { %>
  <h1><%= artist.getAlias() %></h1>
    <% for(AlbumBean album: artist.getAlbums()) { %>
      <h2><%= album.getTitle() %></h2>
    <% } %>
    <% for(TrackBean track: artist.getTopTracks()) { %>
      <h2><%= track.getTitle() %></h2>
    <% } %>
  <% } %>


</section>