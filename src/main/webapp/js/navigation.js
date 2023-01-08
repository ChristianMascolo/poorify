
function navigate(id, type) {

    switch (type) {
        case "HOME":
            home();
            break;
        case "PLAYLIST":
            navToPlaylist(id, false);
            break;
        case "ALBUM":
            navToAlbum(id, false);
            break;
        case "ARTIST":
            navToArtist(id, false);
            break;
        case "USER":
            navToUser(id, false);
            break;
    }
}

function prev() {
    $.post("PrevPage", {}, function(data){
        let id = data.id[0];
        let type = data.type[0];
        if(type == null) return;
        navigate(id, type);
    });
}

function next() {
    $.post("NextPage", {}, function(data){
        let id = data.id[0];
        let type = data.type[0];
        if(type == null) return;
        navigate(id, type);
    });
}

function home() {
    $("#center").load("homepage.jsp");
}

function navToUser(id, new_page) {
    $("#center").load("loading.jsp");
    $.post("GetUser", {id: String(id), new_page: String(new_page)}, function(data){
        document.getElementById("style-to-change").setAttribute("href", "css/user.css");
        $("#center").load("user.jsp");
    });
}

function navToPlaylist(id, new_page) {
    $("#center").load("loading.jsp");
    $.post("GetPlaylist", {id: String(id), new_page: String(new_page)}, function(data){
        document.getElementById("style-to-change").setAttribute("href", "css/playlist.css");
        $("#center").load("playlist.jsp");
    });
}


function navToFeed() {
    $("#center").load("loading.jsp");
    document.getElementById("style-to-change").setAttribute("href", "css/playlist.css");
    $("#center").load("playlist.jsp");
}

function navToAlbum(id, new_page) {
    $("#center").load("loading.jsp");
    $.post("GetAlbum", {id: String(id), new_page: String(new_page)}, function(data){
        document.getElementById("style-to-change").setAttribute("href", "css/album.css");
        $("#center").load("album.jsp");
    });
}

function navToArtist(id, new_page) {
    $("#center").load("loading.jsp");
    $.post("GetArtist", {id: String(id), new_page: String(new_page)}, function(data){
        document.getElementById("style-to-change").setAttribute("href", "css/artist.css");
        $("#center").load("artist.jsp");
    });
}

function createPlaylist() {
    $("#center").load("loading.jsp");
    $.post("CreatePlaylist", {}, function(data){
        let outcome = data.outcome[0];
        if(outcome) {
            let id = data.id[0];
            navToPlaylist(id, true);
        }
        else {
            home();
        }
    });
}

function deletePlaylist(id) {
    $("#center").load("loading.jsp");
    $.post("DeletePlaylist", {id: String(id)}, function(data){
        home();
        notify("Playlist Removed");
    });
}

function search(input) {
    $("#center").load("loading.jsp");
    let search = input.value;
    $.post("Search", {search: search}, function(data){
        document.getElementById("style-to-change").setAttribute("href", "css/search.css");
        $("#center").load("search.jsp");
    });
}

function standby(img) {
    img.src = "https://poorifystorage.blob.core.windows.net/profile/0.jpg";
}