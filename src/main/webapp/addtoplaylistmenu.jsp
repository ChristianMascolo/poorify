<%@ page import="java.util.Collection" %>
<%@ page import="playlist.PlaylistBean" %>
<%@ page import="profile.UserBean" %>

<% Collection<PlaylistBean> playlists = ((UserBean) session.getAttribute("Profile")).getPlaylists(); %>

<div id="add-to-playlist-menu" onclick="">
    <div id="list">
        <% for(PlaylistBean p: playlists) { %>
            <div class="playlist" onclick="addTrackToPlaylist(<%= p.getId() %>)">
                <div>
                    <img src="<%= "files/playlist/" + p.getId() + ".jpg"%>" onerror="standby(this)">
                    <span><%= p.getTitle() %></span>
                </div>
            </div>
            <br>
        <% } %>
    </div>
    <div id="buttons">
        <button onclick="hideAddTrackMenu()"><span>Cancel</span></button>
    </div>
</div>