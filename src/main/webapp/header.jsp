<%@ page import="profile.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section id="header">

    <% ProfileBean profile = (ProfileBean) session.getAttribute("Profile"); %>
    <% UserBean user = profile.getRole() == ProfileBean.Role.USER ? (UserBean) profile : null; %>
    <% ArtistBean artist = profile.getRole() == ProfileBean.Role.ARTIST ? (ArtistBean) profile : null; %>
    <% OverseerBean overseer = profile.getRole() == ProfileBean.Role.OVERSEER ? (OverseerBean) profile : null; %>

    <h1 onclick="home()">Poorify</h1>

    <button class="single-button" class="nav-button" onclick="prev()">
        <img src="images/left-arrow.svg" alt="">
    </button>
    <button class="single-button" class="nav-button" onclick="next()">
        <img src="images/right-arrow.svg" alt="">
    </button>

    <% if(artist == null) { %>
    <div class="search-box">
        <button>
            <img src="images/search.svg" alt="">
        </button>
        <input type="text" placeholder="Search..." onblur="search(this)">
    </div>
    <% } %>

    <% if(user != null) { %>
    <div class="create-playlist" onclick="createPlaylist()">
        <button onclick="createPlaylist()">
            <img src="images/add.svg" alt="">
        </button>
        <p>Create playlist</p>
    </div>
    <% } else if(artist != null) { %>
    <div class="create-playlist" onclick="showUploadAlbumMenu()">
        <button onclick="showUploadAlbumMenu()">
            <img src="images/add.svg" alt="">
        </button>
        <p>Upload album</p>
    </div>
    <% } %>

    <div id="to-the-right">

        <% if(user != null) { %>
            <div class="profile" onclick="showProfileMenu()">
                <button>
                    <img class="profile-picture" src=<%= "https://poorifystorage.blob.core.windows.net/profile/" + user.getId() + ".jpg"%> onerror="standby(this)">
                </button>
                <p><%= user.getAlias() %></p>
            </div>
        <% } else if(artist != null) { %>
            <div class="profile" onclick="showProfileMenu()">
                <button>
                    <img class="profile-picture" src=<%= "https://poorifystorage.blob.core.windows.net/profile/" + artist.getId() + ".jpg"%> onerror="standby(this)">
                </button>
                <p><%= artist.getAlias() %></p>
            </div>
        <% } %>

        <form action="Logout" method="post">
        <button class="single-button" class="logout-button" onclick="logout()">
            <img src="images/logout.svg" alt="" onclick="logout()">
        </button>
        </form>
    </div>

</section>
