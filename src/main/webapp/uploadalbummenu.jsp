<%@ page import="profile.ArtistBean" %>

<% ArtistBean artist = (ArtistBean) session.getAttribute("Profile"); %>

<div id="upload-album-menu">

    <form id="upload-form" action="UploadAlbum" method="post" enctype="multipart/form-data">

        <h3>Upload Album</h3>

        <input id="edit-title" name="title" type="text" value="" placeholder="Album title" required>

        <input id="edit-duration" name="duration" type="text" value="" placeholder="Album duration" required>


        <input id="edit-cover" name="cover" type="file" accept="image/jpeg" required>
        <label for="edit-cover">Select a cover</label>

        <select name="edit-type" required>
            <option value="Album">Album</option>
            <option value="Single">Single</option>
            <option value="Ep">Ep</option>
        </select>

        <input id="edit-year" name="year" type="number" min="1900" value="2023" placeholder="Release year" required>

        <input id="edit-tracks" type="number" min="1" value="1" name="tracks" onchange="updateTracks(this)" required>

        <hr>

        <div id="edit-tracks-upload">

        </div>

        <hr>

        <div id="edit-controls">
            <input type="submit" value="Upload">
        </div>
    </form>

    <button onclick="hideUploadAlbumMenu()"><span>Cancel</span></button>

</div>