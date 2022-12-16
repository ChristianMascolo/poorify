
let audio = document.getElementById("audio");

function play() {
    $.post("DownloadServlet", {}, function(data){
        audio.pause();
        audio.src = data.path[0];
        audio.play();
    });
}