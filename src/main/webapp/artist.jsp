<%@ page import="track.TrackBean" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="album.AlbumBean" %>
<%@ page import="profile.ProfileBean" %>

<section id="artist">

  <% boolean overseer = ((ProfileBean) session.getAttribute("Profile")).getRole() == ProfileBean.Role.OVERSEER; %>

  <% ArtistBean artist = (ArtistBean) session.getAttribute("Artist"); %>

  <section id="head">
    <div id="artist-cover">
      <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + artist.getId() + ".jpg" %>" onerror="standby(this)" onclick="follow(<%= artist.getId() %>, 'artist')">
    </div>
    <div id="info">
      <p id="type-title">
        <span id="type">Artist</span> <br>
        <span id="title" <% if(overseer) { %> onclick="deleteProfile(<%= artist.getId() %>)" <% } %>><%= artist.getAlias() %></span>
      </p>
      <div id="following-line">
        <span id="following-span"></span>
      </div>
    </div>
  </section>

  <section id="tracks">
    <h1>Popular</h1>
      <% int i = 0; %>
      <% for(TrackBean track: artist.getTopTracks()) { %>

        <div class="table-line">

          <div class="index">
            <button onclick="play(<%= track.getId() %>)">
              <span><%= i + 1 %></span>
              <img src="images/play_track.svg" alt="">
            </button>
          </div>

          <div class="title-artists">
            <img src="<%= "https://poorifystorage.blob.core.windows.net/album/" + track.getAlbum().getId() + ".jpg" %>" onerror="standby(this)" id="album-cover" onclick="navToAlbum(<%= track.getAlbum().getId() %>, true)">
            <p>
              <span class="track-title"><%= track.getTitle() %></span>
              <br>
              <span class="track-artists">
                        <span class="track-artist" onclick="navToArtist(<%= track.getAlbum().getArtist().getId() %>, true)"><%= track.getAlbum().getArtist().getAlias() %></span>
                        <% for(ArtistBean a: track.getFeaturing() ) { %>
                          , <span class="track-artist" onclick="navToArtist(<%= a.getId() %>, true)"><%= a.getAlias() %></span>
                        <% } %>
                        </span>
            </p>
          </div>

          <div class="plays">
            <span><%= track.getPlays() %></span>
          </div>

          <div class="duration">
            <%
              int min = track.getDuration() / 60;
              int sec = track.getDuration() % 60;
            %>
            <span><%= min %>:<%= sec < 10 ? "0" + sec : sec%></span>
          </div>

          <div class="options">
            <button>
              <img src="images/options.svg" alt="">
              <div class="dropdown">
                <div onclick="addToQueue(<%= track.getId() %>)"><span>Add to queue</span></div>
                <div onclick="showAddTrackMenu(<%= track.getId() %>)"><span>Add to playlist</span></div>
              </div>
            </button>
          </div>

        </div>
        <% i++; %>
      <% } %>
  </section>

  <section class="display-sections" id="albums">
    <h1>Discography</h1>
    <% for(AlbumBean a: artist.getAlbums()) { %>
    <div>
      <img src="<%= "https://poorifystorage.blob.core.windows.net/album/" + a.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToAlbum(<%= a.getId() %>, true)">
      <p>
        <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToAlbum(<%= a.getId() %>, true)"><%= a.getTitle() %></span>
        <br>
        <span class="info"><%= a.getYear() %> &#183 <%= a.getType() %></span>
      </p>
    </div>
    <% } %>
  </section>

  <% if(((ProfileBean) session.getAttribute("Profile")).getRole() == ProfileBean.Role.USER) { %>
    <jsp:include page="addtoplaylistmenu.jsp"></jsp:include>
  <% } %>

  <script>checkFollowing(<%= artist.getId() %>, "artist")</script>

</section>