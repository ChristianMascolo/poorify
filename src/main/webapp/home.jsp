<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Poorify | Your Music, Your Mood</title>
    <link rel="stylesheet" href="css/main.css" type="text/css">
    <link rel="stylesheet" href="css/header.css" type="text/css">
    <link rel="stylesheet" href="css/footer.css" type="text/css">
    <link rel="stylesheet" href="css/homepage.css" type="text/css">
    <link rel="stylesheet" href="css/loading.css" type="text/css">
    <link rel="stylesheet" href="css/album.css" type="text/css">
    <link rel="stylesheet" href="css/addtoplaylistmenu.css" type="text/css">
    <link rel="stylesheet" href="css/notification.css" type="text/css">
<body>
    <jsp:include page="header.jsp"></jsp:include>

    <section id="center">

        <jsp:include page="homepage.jsp"></jsp:include>

    </section>

    <jsp:include page="footer.jsp"></jsp:include>

    <jsp:include page="notification.jsp"></jsp:include>

<script src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script src="js/navigation.js"></script>
<script src="js/player.js"></script>
<script src="js/profile/login.js"></script>
<script src="js/playlist.js"></script>
<script src="js/notification.js"></script>
</body>
</html>
