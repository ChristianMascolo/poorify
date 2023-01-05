<%@ page import="playlist.PlaylistBean" %>
<%@ page import="playlist.AddedBean" %>
<%@ page import="profile.ProfileBean" %>
<%@ page import="profile.UserBean" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="main.DateFormatter" %>
<section id="playlist">

  <% PlaylistBean playlist = (PlaylistBean) session.getAttribute("Playlist"); %>
  <% boolean roleUser = ((ProfileBean) session.getAttribute("Profile")).getRole() == ProfileBean.Role.USER; %>
  <% boolean owned = playlist.getHost().getId() == ((ProfileBean) session.getAttribute("Profile")).getId(); %>
  <% boolean guest = false;
     if(playlist.getGuests() != null)
        for(ProfileBean profile: playlist.getGuests())
            if(profile.getId() == ((ProfileBean) session.getAttribute("Profile")).getId())
                guest = true;
  %>
  <% boolean supervisor = ((ProfileBean) session.getAttribute("Profile")).getRole() == ProfileBean.Role.OVERSEER; %>

    <section id="head">
        <div id="playlist-cover">
            <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + playlist.getId() + ".jpg" %>" onerror="standby(this)" onclick="playPlaylist(0)">
        </div>
        <div id="info">
            <p id="type-title">
                <span id="type">Playlist</span> <br>
                <span id="title"
                        <% if(owned || guest) { %> onclick="showEditMenu(<%= playlist.getId() %>)" <% } %>
                        <% if(!owned && !guest && !supervisor) { %> onclick="likePlaylist(<%= playlist.getId() %>)" <% } %>
                        <% if(supervisor) { %> onclick="showDeleteMenu()" <% } %>
                    >
                    <%= playlist.getTitle()%>
                </span>
            </p>
            <div id="user-line">
                <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + playlist.getHost().getId() + ".jpg" %>" onerror="standby(this)" onclick="navToUser(<%= playlist.getHost().getId() %>, true)">
                <% if(playlist.isCollaborative() && playlist.getGuests() != null) { %>
                <% for(UserBean u: playlist.getGuests()) { %>
                    &nbsp
                <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + u.getId() + ".jpg" %>" onerror="standby(this)" onclick="navToUser(<%= u.getId()%>, true)">
                <% } %>
                <% } %>
                <p id="details">
                    &nbsp
                    <span id="info-part1">
                        <span id="host-alias" onclick="navToUser(<%= playlist.getHost().getId()%>, true)"><%= playlist.getHost().getAlias() %></span>
                        &#183
                        <% if(playlist.isPublic()) { %> <span id="likes-span"><%= playlist.getLikes() %> likes</span> &#183 <% } %>
                        <%= playlist.getTracks()%> tracks,
                        </span>
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
            <div class="title-artists"><span class="order-change"  onclick="changeOrder(<%= playlist.getId() %>, 'title')">Title</span> &nbsp (<span class="order-change"  onclick="changeOrder(<%= playlist.getId() %>, 'artist')">Artist</span>)</div>
            <div class="album"><span class="order-change"  onclick="changeOrder(<%= playlist.getId() %>, 'album')">Album</span></div>
            <% if(playlist.isCollaborative()) { %>
                <div class="added-by"><span>Added By</span></div>
            <% } %>
            <div class="date-added"><span class="order-change" onclick="changeOrder(<%= playlist.getId() %>, 'date')">Date Added</span></div>
            <div class="duration"><img class="order-change"  src="images/duration.svg" onclick="changeOrder(<%= playlist.getId() %>, 'duration')"></div>
            <div class="options"></div>
        </div>

        <hr>

        <% int i = 0; %>
        <% PlaylistBean.Order order = (PlaylistBean.Order) session.getAttribute("Order"); %>
        <% session.removeAttribute("Order"); %>
        <% for(AddedBean added: order == null ? playlist.getTracklist() : playlist.getTracklist(order)) { %>
            <div class="table-line">

                <div class="index">
                    <button onclick="playPlaylist(<%= i %>)">
                        <span><%= i + 1 %></span>
                        <img src="images/play_track.svg" alt="">
                    </button>
                </div>

                <div class="title-artists">
                    <img src="<%= "https://poorifystorage.blob.core.windows.net/album/" + added.getTrack().getAlbum().getId() + ".jpg" %>" onerror="standby(this)" id="album-cover" onclick="navToAlbum(<%= added.getTrack().getAlbum().getId() %>, true)">
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
                        <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + added.getUser().getId() + ".jpg" %>" onerror="standby(this)" onclick="navToUser(<%= added.getUser().getId() %>)">
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
                            <% if(owned || guest) { %>
                                <div onclick="removeTrack(<%= added.getTrack().getId() %>, <%= playlist.getId() %>)"><span>Remove track</span></div>
                            <% } %>
                        </div>
                    </button>
                </div>

            </div>
            <% i++; %>
        <% } %>
    </section>

    <% if(roleUser) { %>
        <jsp:include page="addtoplaylistmenu.jsp"></jsp:include>
    <% } %>

    <% if(owned || guest) { %>
        <jsp:include page="editplaylistmenu.jsp"></jsp:include>
    <% } %>

    <% if(owned) { %>
        <jsp:include page="addguestsmenu.jsp"></jsp:include>
    <% } %>

    <% if(owned || supervisor) { %>
        <jsp:include page="deleteplaylistmenu.jsp"></jsp:include>
    <% } %>

    <script> resizeForCollaborative(<%= playlist.isCollaborative() %>); </script>
    <script> checkLike(<%= playlist.getId() %>)</script>

</section>
