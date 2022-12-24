
function notify(text) {

    let div = document.getElementById("notification");
    let span = document.getElementById("notification-text");
    span.innerHTML = text;
    div.style.display = "flex";

    setTimeout(stopNotify, 3000);
}

function stopNotify() {
    document.getElementById("notification").style.display = "none";
}