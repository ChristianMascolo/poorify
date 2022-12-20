
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

function navToUser(id) {
    console.log(id);
}

function navToPlaylist(id, new_page) {
    $.post("GetPlaylist", {id: String(id), new_page: String(new_page)}, function(data){
        $("#center").load("playlist.jsp");
    });
}


function navToAlbum(id, new_page) {
    $.post("GetAlbum", {id: String(id), new_page: String(new_page)}, function(data){
        $("#center").load("album.jsp");
    });
}

function navToArtist(id, new_page) {
    $.post("GetArtist", {id: String(id), new_page: String(new_page)}, function(data){
        $("#center").load("artist.jsp");
    });
}