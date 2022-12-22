<%@ page import="album.AlbumBean" %>
<%@ page import="track.TrackBean" %>

<section id="album">

  <% AlbumBean album = (AlbumBean) session.getAttribute("Album"); %>

  <section id="head">
    <div id="album-cover">
      <img src="<%= "https://poorifystorage.blob.core.windows.net/album/" + album.getId() + ".jpg" %>">
    </div>
    <div id="info">
      <p id="type-title">
        <span id="type"><%= album.getType() %></span> <br>
        <span id="title"><%= album.getTitle()%></span>
      </p>
      <div id="artist-line">
        <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + album.getArtist().getId() + ".jpg" %>">
        <p id="details">
          &nbsp
          <span><%= album.getArtist().getAlias() %></span> -
          <span><%= album.getYear()%></span> -
          <span><%= album.getTracks()%></span> -
          <span><%= album.getDuration()%></span>
        </p>
      </div>
    </div>
  </section>

  <section id="tracks">
    <div id="table-header">
      <div class="index"><span>#</span></div>
      <div class="title"><span>Title</span></div>
      <div class="duration"><span>Duration</span></div>
      <div class="queue"><span>Queue</span></div>
    </div>

    <hr>

    <% for(TrackBean track: album.getTracklist()) { %>
        <div class="table-line">
          <div class="index">
            <button onclick="playAlbum(<%= track.getIndex() %>)">
              <span><%= track.getIndex() %></span>
              <img src="images/play.svg" alt="">
            </button>
          </div>
          <div class="title"><span><%= track.getTitle() %></span></div>
          <div class="duration"><span><%= track.getDuration() %></span></div>
          <div class="queue"><span>Queue</span></div>
        </div>
    <% } %>
  </section>

</section>