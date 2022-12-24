<%@ page import="playlist.PlaylistBean" %>
<%@ page import="playlist.AddedBean" %>
<%@ page import="profile.ProfileBean" %>
<%@ page import="profile.UserBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="main.DateFormatter" %>
<section id="playlist">

  <% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>

    <section id="head">
        <div id="playlist-cover">
            <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + playlist.getId() + ".jpg" %>" onclick="playPlaylist(0)">
        </div>
        <div id="info">
            <p id="type-title">
                <span id="type">Playlist</span> <br>
                <span id="title"><%= playlist.getTitle()%></span>
            </p>
            <div id="user-line">
                <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + playlist.getHost().getId() + ".jpg" %>" onclick="navToUser(<%= playlist.getHost().getId() %>, true)">
                <% if(playlist.isCollaborative() && playlist.getGuests() != null) { %>
                <% for(UserBean u: playlist.getGuests()) { %>
                    &nbsp
                <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + u.getId() + ".jpg" %>" onclick="navToUser(<%= u.getId()%>, true)">
                <% } %>
                <% } %>
                <p id="details">
                    &nbsp
                    <span id="info-part1"><span id="host-alias" onclick="navToUser(<%= playlist.getHost().getId()%>, true)"><%= playlist.getHost().getAlias() %></span> &#183 <% if(playlist.isPublic()) { %> <%= playlist.getLikes() %> likes &#183<% } %> <%= playlist.getTracks()%> tracks, </span>
                    <span id="info-part2">
            <%  int hours = playlist.getDuration() / 3600;
                int minutes = (playlist.getDuration() % 3600) / 60;
                int seconds = (playlist.getDuration() % 3600) % 60;
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
            <div class="album"><span>Album</span></div>
            <% if(playlist.isCollaborative()) { %>
                <div class="added-by"><span>Added By</span></div>
            <% } %>
            <div class="date-added"><span>Date Added</span></div>
            <div class="duration"><img src="images/duration.svg"></div>
            <div class="options"></div>
        </div>

        <hr>

        <% int i = 0; %>
        <% for(AddedBean added: playlist.getTracklist()) { %>
            <div class="table-line">

                <div class="index">
                    <button onclick="playPlaylist(<%= i %>)">
                        <span><%= i + 1 %></span>
                        <img src="images/play_track.svg" alt="">
                    </button>
                </div>

                <div class="title-artists">
                    <img src="<%= "https://poorifystorage.blob.core.windows.net/album/" + added.getTrack().getAlbum().getId() + ".jpg" %>" alt="" id="album-cover" onclick="navToAlbum(<%= added.getTrack().getAlbum().getId() %>, true)">
                    <p>
                        <span class="track-title"><%= added.getTrack().getTitle() %></span>
                        <br>
                        <span class="track-artists">
                        <span class="track-artist" onclick="navToArtist(<%= added.getTrack().getAlbum().getArtist().getId() %>, true)"><%= added.getTrack().getAlbum().getArtist().getAlias() %></span>
                        <% for(ArtistBean a: added.getTrack().getFeaturing() ) { %>
                          , <span class="track-artist" onclick="navToArtist(<%= a.getId() %>, true)"><%= a.getAlias() %></span>
                        <% } %>
                        </span>
                    </p>
                </div>

                <div class="album">
                    <span class="album-title" onclick="navToAlbum(<%= added.getTrack().getAlbum().getId() %>)"><%= added.getTrack().getAlbum().getTitle() %></span>
                </div>

                <% if(playlist.isCollaborative()) { %>
                    <div class="added-by">
                        <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + added.getUser().getId() + ".jpg" %>" onclick="navToUser(<%= added.getUser().getId() %>)">
                        <span class="added-by-alias" onclick="<%= added.getUser().getId() %>"><%= added.getUser().getAlias() %></span>
                    </div>
                <% } %>

                <div class="date-added">
                    <span><%= (new DateFormatter()).format(added.getDate(), DateFormatter.AZURE_SQL_FORMAT, DateFormatter.PLAYLIST_DISPLAY_FORMAT) %></span>
                </div>

                <div class="duration">
                    <%
                        int min = added.getTrack().getDuration() / 60;
                        int sec = added.getTrack().getDuration() % 60;
                    %>
                    <span><%= min %>:<%= sec < 10 ? "0" + sec : sec%></span>
                </div>

                <div class="options">
                    <button>
                        <img src="images/options.svg" alt="">
                        <div class="dropdown">
                            <div onclick="addToQueue(<%= added.getTrack().getId() %>)"><span>Add to queue</span></div>
                            <div onclick="showAddTrackMenu(<%= added.getTrack().getId() %>)"><span>Add to playlist</span></div>
                        </div>
                    </button>
                </div>

            </div>
        <% i++; %>
        <% } %>
    </section>

    <% if(playlist.getHost().getId() == ((ProfileBean) session.getAttribute("Profile")).getId()) { %>
    <button onclick="deletePlaylist(<%= playlist.getId() %>)">Delete</button>
    <% } %>

    <jsp:include page="addtoplaylistmenu.jsp"></jsp:include>

    <script> resizeForCollaborative(<%= playlist.isCollaborative() %>); </script>

</section>
