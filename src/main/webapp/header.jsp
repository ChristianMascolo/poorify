<%@ page import="profile.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section id="header">

    <% UserBean user = (UserBean) session.getAttribute("Profile"); %>

    <h1 onclick="home()">Poorify</h1>

    <button class="single-button" class="nav-button">
        <img src="images/left-arrow.svg" alt="">
    </button>
    <button class="single-button" class="nav-button">
        <img src="images/right-arrow.svg" alt="">
    </button>

    <div class="search-box">
        <button>
            <img src="images/search.svg" alt="">
        </button>
        <input type="text" placeholder="Search...">
    </div>

    <div class="create-playlist">
        <button>
            <img src="images/add.svg" alt="">
        </button>
        <p>Create playlist</p>
    </div>

    <div id="to-the-right">
        <div class="profile">
            <button>
                <img class="profile-picture" src=<%= "https://poorifystorage.blob.core.windows.net/profile/" + (user != null ? user.getId() : 0) + ".jpg"%>>
            </button>
            <p><%= user != null ? user.getAlias() : "Alias" %></p>
        </div>

        <button class="single-button" class="logout-button" onclick="logout()">
            <img src="images/logout.svg" alt="">
        </button>
    </div>

</section>
