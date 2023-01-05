

function showProfileMenu() {
    hideProfileMenu();
    let menu = document.getElementById("edit-profile-menu");
    menu.style.display = "block";

    $("#edit-profile-form").submit(function(event) {
        hideProfileMenu();
        $("#center").load("loading.jsp");
        $.ajax({
            url: "EditProfile",
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (result) {
                notify("Changes saved!");
                home();
            }
        });
        event.preventDefault();
    });
}

function hideProfileMenu() {
    let menu = document.getElementById("edit-profile-menu");
    menu.style.display = "none";
}

function deleteProfile(id) {
    $.post("DeleteProfile", {id: String(id)}, function(data) {

    });
}