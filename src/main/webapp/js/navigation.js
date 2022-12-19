
function home() {
    $("#center").load("homepage.jsp");
}

function navToUser(id) {
    console.log(id);
}

function navToPlaylist(id) {
    $.post("GetPlaylist", {id: String(id)}, function(data){
        $("#center").load("playlist.jsp");
    });
}


function navToAlbum(id) {
    $.post("GetAlbum", {id: String(id)}, function(data){
        $("#center").load("album.jsp");
    });
}

function navToArtist(id) {
    console.log(id);
}