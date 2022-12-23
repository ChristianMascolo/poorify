
let track = 0;

function showAddTrackMenu(id) {
    hideAddTrackMenu();
    let menu = document.getElementById("add-to-playlist-menu");
    menu.style.display = "block";
    track = id;
}

function hideAddTrackMenu() {
    let menu = document.getElementById("add-to-playlist-menu");
    menu.style.display = "none";
}

function addTrackToPlaylist(playlist) {
    $.post("AddTrack", {track: String(track), playlist: String(playlist)}, function(data){
        hideAddTrackMenu();
        notify("Added To Playlist");
    });
}