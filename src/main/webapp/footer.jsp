<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="footer">

    <div class="album">
        <img src="images/profile.jpg" alt="" id="album" onclick="">
        <div>
            <p>
                <span class="track" id="title">Track title</span> <br>
                <span class="artists" id="artists">Artist alias</span>
            </p>
        </div>
    </div>

    <div class="player">

        <div class="controls">
            <button class="restart">
                <img src="images/replay.svg" alt="">
            </button>
            <button id="play-pause">
                <img src="images/pause.svg" alt="">
            </button>
            <button class="next">
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
        <img src="images/left-arrow.svg" alt="">
        <input type="range" min="0" value="0" id="volume-slider">
    </div>

    <audio src="" preload="metadata" onloadedmetadata="setMax()" id="audio"></audio>

</section>