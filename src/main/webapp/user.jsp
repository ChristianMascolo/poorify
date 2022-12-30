<%@ page import="profile.UserBean" %>
<%@ page import="playlist.PlaylistBean" %>
<%@ page import="profile.ArtistBean" %>
<section id="user">

    <% UserBean user = (UserBean) session.getAttribute("User"); %>

    <section id="head">
        <div id="user-cover">
            <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + user.getId() + ".jpg" %>" <% if(user.isPublic()) { %> onclick="follow(<%= user.getId() %>, 'user')" <% } %>>
        </div>
        <div id="info">
            <p id="type-title">
                <span id="type">User</span> <br>
                <span id="title"><%= user.getAlias() %></span>
            </p>
            <div id="following-line">
                <span id="following-span"></span>
            </div>
        </div>
    </section>

    <% if(user.isPublic())  { %>

    <section class="display-sections" id="user-playlists">
        <h1>Public Playlists</h1>
        <% for(PlaylistBean p: user.getPlaylists()) { %>
        <% if(p.isPublic()) { %>
        <div>
            <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  alt="" onclick="navToPlaylist(<%= p.getId() %>, true)">
            <p>
                <span class="title" onclick="navToPlaylist(<%= p.getId() %>, true)"><%= p.getTitle() %></span>
                <br>
                <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
            </p>
        </div>
        <% } %>
        <% } %>
    </section>

    <section class="display-sections" id="user-artists">
        <h1>Favorite Artists</h1>
        <% for(ArtistBean a: user.getArtists()) { %>
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

    <section class="display-sections" id="user-followers">
        <h1>Followers</h1>
        <% for(UserBean u: user.getFollowers()) { %>
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

    <section class="display-sections" id="user-following">
        <h1>Following</h1>
        <% for(UserBean u: user.getFollowing()) { %>
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

    <script>checkFollowing(<%= user.getId() %>, "user")</script>

    <% } else { %>

    <h1>Private Profile</h1>

    <% } %>

</section>