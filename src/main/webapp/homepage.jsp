<%@ page import="profile.*" %>
<%@ page import="playlist.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="album.AlbumBean" %>

<section id="homepage">

    <% ProfileBean profile = (ProfileBean) session.getAttribute("Profile"); %>
    <% UserBean user = profile.getRole() == ProfileBean.Role.USER ? (UserBean) profile : null; %>
    <% ArtistBean artist = profile.getRole() == ProfileBean.Role.ARTIST ? (ArtistBean) profile : null; %>
    <% OverseerBean overseer = profile.getRole() == ProfileBean.Role.OVERSEER ? (OverseerBean) profile : null; %>

    <%  String greeting = null;
        int hours = (Calendar.getInstance()).get(Calendar.HOUR_OF_DAY);
        if (hours >= 5 && hours < 12) greeting = "Good Morning";
        else if (hours >= 12 && hours < 17) greeting = "Good Afternoon";
        else if (hours >= 17 && hours < 21) greeting = "Good Evening";
        else greeting = "Good Night";
    %>
    <h1><%= greeting %></h1>

    <% if(user != null) { %>

    <section class="display-sections" id="playlist">
        <h1>Your Playlists</h1>
        <% for(PlaylistBean p: user.getPlaylists()) { %>
            <div>
                <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToPlaylist(<%= p.getId() %>, true)">
                <p>
                    <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToPlaylist(<%= p.getId() %>, true)"><%= p.getTitle() %></span>
                    <br>
                    <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
                </p>
            </div>
        <% } %>
    </section>

    <section class="display-sections" id="playlist-liked">
        <h1>Playlists You Liked</h1>
        <% for(PlaylistBean p: user.getLikedPlaylists()) { %>
        <div>
            <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToPlaylist(<%= p.getId() %>, true)">
            <p>
                <span onmouseover="slide(this)" onmouseout="slideBack(this)" class="title" onclick="navToPlaylist(<%= p.getId() %>, true)"><%= p.getTitle() %></span>
                <br>
                <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
            </p>
        </div>
        <% } %>
    </section>

    <section class="display-sections" id="artists-following">
        <h1>Your Favorite Artists</h1>
        <% for(ArtistBean a: user.getArtists()) { %>
        <div>
            <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + a.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToArtist(<%= a.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToArtist(<%= a.getId() %>, true)"><%= a.getAlias() %></span>
                <br>
                <span class="host"></span>
            </p>
        </div>
        <% } %>
    </section>

    <section class="display-sections" id="user-following">
        <h1>Users You're Following</h1>
        <% for(UserBean u: user.getFollowing()) { %>
        <div>
            <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + u.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToUser(<%= u.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToUser(<%= u.getId() %>, true)"><%= u.getAlias() %></span>
                <br>
                <span class="host"></span>
            </p>
        </div>
        <% } %>
    </section>

    <section class="display-sections" id="user-followers">
        <h1>Your Followers</h1>
        <% for(UserBean u: user.getFollowers()) { %>
        <div>
            <img src="<%= "https://poorifystorage.blob.core.windows.net/profile/" + u.getId() + ".jpg"%>"  onerror="standby(this)" onclick="navToUser(<%= u.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToUser(<%= u.getId() %>, true)"><%= u.getAlias() %></span>
                <br>
                <span class="host"></span>
            </p>
        </div>
        <% } %>
    </section>

    <% } else if (artist != null) { %>

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

    <% } %>
</section>
