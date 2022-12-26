
let track = 0;

function showEditMenu(id) {
    hideEditMenu();

    let menu = document.getElementById("edit-playlist-menu");
    menu.style.display = "block";

    document.getElementById("edit-id").value = id;

    $("#edit-form").submit(function(event) {
        hideEditMenu();
        $("#center").load("loading.jsp");
        $.ajax({
            url: "EditPlaylist",
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (result) {
                notify("Changes saved!");
                navToPlaylist(id, false);
            }
        });
        event.preventDefault();
    });
}

function hideEditMenu() {
    let menu = document.getElementById("edit-playlist-menu");
    menu.style.display = "none";
}

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

function checkLike(id) {
    $.post("CheckLike", {id: String(id)}, function(data){
        let outcome = data.outcome[0];
        if(outcome) {
            let span = document.getElementById("likes-span");
            span.innerHTML = span.innerHTML + " (liked)";
        }
    });
}

function likePlaylist(id) {
    $.post("CheckLike", {id: String(id)}, function(data){
        let outcome = data.outcome[0];
        if(outcome) {
            $.post("UnlikePublicPlaylist", {id: String(id)}, function(data){
                notify("Playlist unliked");
            });
        }
        else {
            $.post("LikePublicPlaylist", {id: String(id)}, function (data) {
                notify("Playlist liked");
            });
        }
        navToPlaylist(id, false);
    });
}

function removeTrack(track, playlist) {
    $.post("RemoveTrack", {track: String(track), playlist: String(playlist)}, function(data){
        navToPlaylist(playlist);
        notify("Track removed");
    });
}

function changeOrder(playlist, order) {
    $.post("ChangeOrder", {order: order}, function(data) {
        navToPlaylist(playlist);
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

function showGuestsMenu() {
    hideEditMenu();
    hideGuestsMenu();
    let menu = document.getElementById("add-guests-menu");
    menu.style.display = "block";
}

function hideGuestsMenu() {
    let menu = document.getElementById("add-guests-menu");
    menu.style.display = "none";
}

function searchGuests(input, playlist) {
    let search = input.value;
    $('#guests-results').empty();
    $.post("SearchForGuests", {search: search}, function(data){
        for(let i = 0; i < data.id.length; i++) {
            let line = document.createElement("div");

            let img = document.createElement("img");
            img.src = "https://poorifystorage.blob.core.windows.net/profile/" + String(data.id[i]) + ".jpg";
            img.alt = "";
            img.addEventListener("click", function() { addGuest(data.id[i], playlist); });
            line.append(img);

            let span = document.createElement("span");
            span.innerHTML = data.alias[i] + " (" + data.email[i] + ")";
            span.addEventListener("click", function() { addGuest(data.id[i], playlist); });
            line.append(span);

            line.addEventListener("click", function() { addGuest(data.id[i], playlist); });
            $('#guests-results').append(line);
        }
    });
}

function addGuest(guest, playlist) {
    $.post("AddGuest", {guest: String(guest), playlist: String(playlist)}, function(data) {
        let outcome = data.outcome[0];
        if(outcome)
            notify("Guest added");
        else
            notify("User already guest");
        navToPlaylist(playlist);
    });
}

function showDeleteMenu() {
    hideEditMenu();
    hideDeleteMenu();
    let menu = document.getElementById("delete-playlist-menu");
    menu.style.display = "block";
}

function hideDeleteMenu() {
    let menu = document.getElementById("delete-playlist-menu");
    menu.style.display = "none";
}