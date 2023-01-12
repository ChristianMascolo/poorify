<%@ page import="profile.UserBean" %>
<%@ page import="playlist.PlaylistBean" %>
<%@ page import="profile.ArtistBean" %>
<%@ page import="profile.ProfileBean" %>
<section id="user">

    <% boolean overseer = ((ProfileBean) session.getAttribute("Profile")).getRole() == ProfileBean.Role.OVERSEER; %>

    <% UserBean user = (UserBean) session.getAttribute("User"); %>

    <section id="head">
        <div id="user-cover">
            <img src="<%= "files/profile/" + user.getId() + ".jpg" %>" onerror="standby(this)" <% if(user.isPublic()) { %> onclick="follow(<%= user.getId() %>, 'user')" <% } %>>
        </div>
        <div id="info">
            <p id="type-title">
                <span id="type">User</span> <br>
                <span id="title" <% if(overseer) { %> onclick="deleteProfile(<%= user.getId() %>)" <% } %>><%= user.getAlias() %></span>
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
            <img src="<%= "files/playlist/" + p.getId() + ".jpg"%>" onerror="standby(this)" onclick="navToPlaylist(<%= p.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToPlaylist(<%= p.getId() %>, true)"><%= p.getTitle() %></span>
                <br>
                <span class="host" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
            </p>
        </div>
        <% } %>
        <% } %>
    </section>

    <section class="display-sections" id="user-artists">
        <h1>Favorite Artists</h1>
        <% for(ArtistBean a: user.getArtists()) { %>
        <div>
            <img src="<%= "files/profile/" + a.getId() + ".jpg"%>" onerror="standby(this)" onclick="navToArtist(<%= a.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToArtist(<%= a.getId() %>, true)"><%= a.getAlias() %></span>
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
            <img src="<%= "files/profile/" + u.getId() + ".jpg"%>" onerror="standby(this)" onclick="navToUser(<%= u.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToUser(<%= u.getId() %>, true)"><%= u.getAlias() %></span>
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
            <img src="<%= "files/profile/" + u.getId() + ".jpg"%>" onerror="standby(this)" onclick="navToUser(<%= u.getId() %>, true)">
            <p>
                <span class="title" onmouseover="slide(this)" onmouseout="slideBack(this)" onclick="navToUser(<%= u.getId() %>, true)"><%= u.getAlias() %></span>
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