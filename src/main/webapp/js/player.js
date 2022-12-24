
let slider = document.getElementById("time-slider");

let audio = document.getElementById("audio");
let currentTime = document.getElementById("currentTime");
let totalTime = document.getElementById("totalTime");

setInterval(update, 1000);

function play(trackId) {

    $.post("Play", {trackId: String(trackId)}, function(data){

        //UPDATE ALBUM COVER
        let album = document.getElementById("album-footer");
        album.src = "https://poorifystorage.blob.core.windows.net/album/" + data.albumID[0] + ".jpg";
        album.onclick = function() { navToAlbum(data.albumID[0], true); };

        //UPDATE SONG TITLE
        let title = document.getElementById("title-footer");
        title.innerHTML = data.title[0];
        title.onclick = function() { navToAlbum(data.albumID[0], true); };

        console.log(data.title[0]);

        //UPDATE ARTISTs ALIAS
        let artists = document.getElementById("artists-footer");

        //Main Artist
        let mainArtistSpan = document.createElement("span");
        mainArtistSpan.innerHTML = data.artist[0];
        mainArtistSpan.onclick = function() { navToArtist(data.artistID[0], true); };
        mainArtistSpan.classList.add("artist");

        artists.innerHTML = "";
        artists.append(mainArtistSpan);

        //Featuring Artists
        if(data.artist.length > 1) {
            for(let i = 1; i < data.artist.length; i++) {
                artists.append(document.createTextNode(", "));

                let featuringArtist = document.createElement("span");
                featuringArtist.innerHTML = data.artist[i];
                featuringArtist.onclick = function() { navToArtist(data.artistID[i], true); };
                featuringArtist.classList.add("artist");
                artists.append(featuringArtist)
            }
        }

        //LOAD AUDIO TRACK FROM AZURE
        audio.pause();
        audio.src = "https://poorifystorage.blob.core.windows.net/track/" + data.trackID[0] + ".mp3";
        audio.load();

        //UPDATE SLIDER
        slider.value = 0;
        currentTime.innerHTML = "0:00"

        //UPDATE BUTTON
        document.getElementById("play-pause").onclick = function() { pause(); };
        document.getElementById("play-pause-img").src = "images/pause.svg";

        audio.play();
    });
}

function setMax() {
    slider.max = Math.floor(audio.duration);
    let minutes = Math.floor(audio.duration / 60);
    let seconds = Math.floor(audio.duration % 60);
    totalTime.innerHTML = minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
}

function update() {
    slider.value = audio.currentTime;
    let minutes = Math.floor(audio.currentTime / 60);
    let seconds = Math.floor(audio.currentTime % 60);
    currentTime.innerHTML = minutes + ":" +(seconds < 10 ? "0" + seconds : seconds);
}

function change() {
    audio.currentTime = slider.value;
    update();
}

function restart() {
    audio.currentTime = 0;
    update();
}

function pause() {
    audio.pause();
    document.getElementById("play-pause").onclick = function() { reprise(); };
    document.getElementById("play-pause-img").src = "images/play.svg";
    update();
}

function reprise() {
    audio.play();
    document.getElementById("play-pause").onclick = function() { pause(); };
    document.getElementById("play-pause-img").src = "images/pause.svg";
    update();
}

function volume() {
    let volumeSlider = document.getElementById("volume-slider");
    let value = volumeSlider.value / volumeSlider.max;
    audio.volume = value;
}

function skip() {
    $.post("Skip", {}, function(data) {
        let id = parseInt(data.id[0]);
        if(id > 0)
            play(id);
    });
}

function addToQueue(id) {
    $.post("AddToQueue", {id: String(id)}, function(data) {
        notify("Added to queue");
    });
}

function playAlbum(index) {
    $.post("PlayAlbum", {index: String(index)}, function(data) {
        skip();
    });
}

function playPlaylist(index) {
    $.post("PlayPlaylist", {index: String(index)}, function(data) {
        skip();
    });
}