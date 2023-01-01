

function showUploadAlbumMenu() {
    hideUploadAlbumMenu();
    let menu = document.getElementById("upload-album-menu");
    menu.style.display = "block";

    $("#upload-form").submit(function(event) {
        hideUploadAlbumMenu();
        $("#center").load("loading.jsp");
        $.ajax({
            url: "UploadAlbum",
            type: "POST",
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (result) {
                notify("Album uploaded!");
                home();
            }
        });
        event.preventDefault();
    });
}

function hideUploadAlbumMenu() {
    let menu = document.getElementById("upload-album-menu");
    menu.style.display = "none";
}

function updateTracks(input) {

    let num = input.value;
    $('#edit-tracks-upload').empty();

    for(let i = 0; i < num; i++) {

        let div = document.createElement("div");

        let h2 = document.createElement("h2");
        h2.innerHTML = "Track #" + (i + 1);
        div.append(h2);

        let input_title = document.createElement("input");
        input_title.type = "text";
        input_title.name = "title-" + (i + 1);
        input_title.placeholder = "Track #" + (i + 1) + " Title";
        input_title.required = true;
        div.append(input_title)

        let input_duration = document.createElement("input");
        input_duration.type = "text";
        input_duration.name = "duration-" + (i + 1);
        input_duration.placeholder = "Track #" + (i + 1) + " Duration";
        input_duration.required = true;
        div.append(input_duration);

        let input_audio = document.createElement("input");
        input_audio.type = "file";
        input_audio.id = "edit-audio-track-" + (i + 1);
        input_audio.accept = "audio/mpeg";
        input_audio.name = "audio-" + (i + 1);
        input_audio.required = true;
        div.append(input_audio);

        let label_audio = document.createElement("label");
        label_audio.for = "edit-audio-track-" + (i + 1);
        label_audio.innerHTML = "Upload track";

        let select_genre = document.createElement("select");
        select_genre.name = "genre-" + (i + 1);
        select_genre.required = true;
        let option = document.createElement("option");
        option.value = "1";
        option.innerHTML = "Pop";
        select_genre.append(option);
        div.append(select_genre);

        let input_featuring = document.createElement("input");
        input_featuring.type = "text";
        input_featuring.placeholder = "Add a featuring artist";
        div.append(input_featuring);

        let div_featuring = document.createElement("div");
        div_featuring.id = "featuring-results-" + (i + 1);
        div.append(div_featuring);

        document.getElementById("edit-tracks-upload").append(div);
    }
}

function deleteAlbum(id) {
    $("#center").load("loading.jsp");
    $.post("DeleteAlbum", {id: String(id)}, function(data){
        home();
        notify("Album deleted");
    });
}