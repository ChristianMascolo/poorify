<%@ page import="profile.ProfileBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="footer">

    <% ProfileBean profile = (ProfileBean) session.getAttribute("Profile"); %>

    <% if(profile.getRole() != ProfileBean.Role.ARTIST) { %>
    <div class="album">
        <img src="images/trasparent.png" id="album-footer" onclick="">
        <div>
            <p>
                <span class="track" id="title-footer"></span> <br>
                <span class="artists" id="artists-footer"></span>
            </p>
        </div>
    </div>

    <div class="player">

        <div class="controls">
            <button class="restart" onclick="restart()" data-title="Restart">
                <img src="images/replay.svg" alt="">
            </button>
            <button id="play-pause" onclick="pause()">
                <img id="play-pause-img" src="images/play.svg" alt="">
            </button>
            <button class="next" onclick="skip()">
                <img src="images/forward.svg" alt="">
            </button>
        </div>

        <div class="slider">
            <span id="currentTime">0:00</span>
            <input type="range" min="1" max="100" value="25" oninput="change()" onchange="change()" onclick="change()" id="time-slider">
            <span id="totalTime">0:00</span>
        </div>

    </div>

    <div class="volume">
        <button>
            <img src="images/volume.svg" alt="">
        </button>
        <input type="range" min="0" max="100" value="100" oninput="volume()" onchange="volume()" onclick="volume()" id="volume-slider">
    </div>

    <audio src="" preload="metadata" onloadedmetadata="setMax()" id="audio"></audio>

    <% } else { %>
        <h1>POORIFY FOR ARTISTS</h1>
    <% } %>
</section>