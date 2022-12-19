<%@ page import="profile.*" %>
<%@ page import="playlist.*" %>
<%@ page import="java.util.Calendar" %>

<section id="homepage">

    <% UserBean user = (UserBean) session.getAttribute("Profile"); %>

    <%  String greeting = null;
        int hours = (Calendar.getInstance()).get(Calendar.HOUR_OF_DAY);
        if (hours >= 5 && hours < 12) greeting = "Good Morning";
        else if (hours >= 12 && hours < 17) greeting = "Good Afternoon";
        else if (hours >= 17 && hours < 21) greeting = "Good Evening";
        else greeting = "Good Night";
    %>
    <h1><%= greeting %></h1>


    <input type="button" value="COLDPLAY" onclick="play(<%= user.getId() %>, 5)">
    <input type="button" value="JAMES BLUNT" onclick="play(<%= user.getId() %>, 11)">

    <section class="display-sections" id="playlist">
        <h1>Your Playlists</h1>
        <% if(user != null) { %>
            <% for(PlaylistBean p: user.getPlaylists()) { %>
                <div>
                    <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  alt="" onclick="navToPlaylist(<%= p.getId() %>)">
                    <p>
                        <span class="title" onclick="navToPlaylist(<%= p.getId() %>)"><%= p.getTitle() %></span>
                        <br>
                        <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
                    </p>
                </div>
            <% } %>
        <% } %>
    </section>

    <section class="display-sections" id="playlist-liked">
        <h1>Playlists You Liked</h1>
        <% if(user != null) { %>
        <% for(PlaylistBean p: user.getLikedPlaylists()) { %>
        <div onclick="navToPlaylist(<%= p.getId() %>)">
            <img src="<%= "https://poorifystorage.blob.core.windows.net/playlist/" + p.getId() + ".jpg"%>"  alt="">
            <p>
                <span class="title"><%= p.getTitle() %></span>
                <br>
                <span class="host" onclick="navToUser(<%= p.getHost().getId() %>)"><%= p.getHost().getAlias() %></span>
            </p>
        </div>
        <% } %>
        <% } %>
    </section>

</section>
