
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
        let outcome = data.outcome[0];
        notify(outcome ? "Track Added To Playlist" : "Track Already In Playlist");
    });
}

function resizeForCollaborative(isCollaborative) {
    let title_artists_width = isCollaborative ? "37%" : "47%";
    let album_width = isCollaborative ? "20%" : "30%";

    document.querySelectorAll('section#tracks div div.title-artists').forEach(div => {
        div.style.maxWidth = title_artists_width;
        div.style.width = title_artists_width;
    });
    document.querySelectorAll('section#tracks div div.album').forEach(div => {
        div.style.maxWidth = album_width;
        div.style.width = album_width;
    });

}