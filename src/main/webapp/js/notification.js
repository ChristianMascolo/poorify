
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

function slide(field) {
    let maxscroll = $(field).width();
    let speed = maxscroll * 15;
    $(field).animate({
        scrollLeft: maxscroll
    }, speed, "linear");
}

function slideBack(field) {
    $(field).stop();
    $(field).animate({
       scrollLeft: 0
    }, 'slow');
}

function scrollOnHover() {
    $(".scroll-on-hover").mouseover(function() {
        var maxscroll = $(this).width();
        var speed = maxscroll * 15;
        $(this).animate({
            scrollLeft: maxscroll
        }, speed, "linear");
    });

    $(".scroll-on-hover").mouseout(function() {
        $(this).stop();
        $(this).animate({
            scrollLeft: 0
        }, 'slow');
    });
}