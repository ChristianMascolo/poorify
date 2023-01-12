<%@ page import="album.AlbumBean" %>
<%@ page import="track.TrackBean" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="profile.ProfileBean" %>

<section id="album">

  <% ProfileBean profile = (ProfileBean) session.getAttribute("Profile"); %>
  <% AlbumBean album = (AlbumBean) session.getAttribute("Album"); %>

  <section id="head">
    <div id="album-cover">
      <img src="<%="files/album/" + album.getId() + ".jpg"%>" onerror="standby(this)" onclick="playAlbum(1)">
    </div>
    <div id="info">
      <p id="type-title">
        <span id="type"><%= album.getType() %></span> <br>
        <span id="title" <% if(profile.getRole() != ProfileBean.Role.USER) { %> onclick="deleteAlbum(<%= album.getId() %>)" <% } %> ><%= album.getTitle()%></span>
      </p>
      <div id="artist-line">
        <img src="<%="files/profile/" + album.getArtist().getId() + ".jpg"%>" onerror="standby(this)" <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %> onclick="navToArtist(<%= album.getArtist().getId()%>, true)" <% } %>>
        <p id="details">
          &nbsp
          <span id="info-part1"><span id="artist-alias" <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %> onclick="navToArtist(<%= album.getArtist().getId()%>, true)" <% } %> ><%= album.getArtist().getAlias() %></span> &#183 <%= album.getYear()%> &#183 <%= album.getTracks()%> tracks, </span>
          <span id="info-part2">
            <%  int hours = album.getDuration() / 3600;
                int minutes = (album.getDuration() % 3600) / 60;
                int seconds = (album.getDuration() % 3600) % 60;
            %>
            <% if(hours >= 1) { %>
              <%= hours %> <%= hours > 1 ? "hours" : "hour" %> <%= minutes %> min
            <% } else { %>
              <%= minutes %> min <%= seconds %> sec
            <% } %>
          </span>
        </p>
      </div>
    </div>
  </section>

  <section id="tracks">
    <div id="table-header">
      <div class="index"><span>#</span></div>
      <div class="title-artists"><span>Title</span></div>
      <div class="duration"><img src="images/duration.svg"></div>
      <div class="options"></div>
    </div>

    <hr>

    <% for(TrackBean track: album.getTracklist()) { %>
        <div class="table-line">

          <div class="index">
            <button <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %> onclick="playAlbum(<%= track.getIndex() %>)" <% } %>>
              <span><%= track.getIndex() %></span>
              <img src="images/play_track.svg" alt="">
            </button>
          </div>

          <div class="title-artists">
            <p>
              <span class="track-title"><%= track.getTitle() %></span>
              <br>
              <span class="track-artists">
                <span class="track-artist" <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %> onclick="navToArtist(<%= track.getAlbum().getArtist().getId() %>, true)" <% } %> ><%= track.getAlbum().getArtist().getAlias() %></span>
                <% for(ArtistBean a: track.getFeaturing() ) { %>
                  , <span class="track-artist" <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %> onclick="navToArtist(<%= a.getId() %>, true)" <% } %>><%= a.getAlias() %></span>
                <% } %>
              </span>
            </p>
          </div>

          <div class="duration">
            <%
              int min = track.getDuration() / 60;
              int sec = track.getDuration() % 60;
            %>
            <span><%= min %>:<%= sec < 10 ? "0" + sec : sec%></span>
          </div>

          <div class="options">
            <% if(profile.getRole() == ProfileBean.Role.USER) { %>
              <button>
                <img src="images/options.svg" alt="">
                <div class="dropdown">
                  <div onclick="addToQueue(<%= track.getId() %>)"><span>Add to queue</span></div>
                  <div onclick="showAddTrackMenu(<%= track.getId() %>)"><span>Add to playlist</span></div>
                </div>
              </button>
            <% } %>
          </div>


        </div>
    <% } %>
  </section>

  <% if(profile.getRole() == ProfileBean.Role.USER) { %>
    <jsp:include page="addtoplaylistmenu.jsp"></jsp:include>
  <% } %>

</section>