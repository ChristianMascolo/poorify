
let audio = document.getElementById("audio");

function play() {
    $.getJSON("DownloadServlet", {}, function(data){
        audio.pause();
        audio.src = data.path[0];
        audio.play();
    });
}