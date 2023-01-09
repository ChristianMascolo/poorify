<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Poorify | Your Music, Your Mood</title>
  <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>

<div id="container">
  <img src="images/sfondo_index.jpg">

  <div>
  <section class="form">

    <h2>Poorify</h2>

    <form id="login-form" action="Login" method="post">

      <div id="email" class="box">
        <input type="email" name="email" placeholder="Email address">
      </div>

      <div id="password" class="box">
        <input type="password" name="password" placeholder="Password">
      </div>

      <div class="box" class="submit">
        <input type="button" value="Login" onclick="login()">
      </div>

    </form>

    <div class="error">
      <p id="error-message"></p>
      <hr>
    </div>

    <div class="redirect">
      <p>
        <span>Don't have an account?</span> <br>
        <a href="signup.jsp">SIGN UP</a> <br>
        <a href="signupartist.jsp">SIGN UP FOR POORIFY ARTIST</a>
      </p>
    </div>

  </section>
  </div>

</div>

<script src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script src="js/profile/login.js"></script>
</body>
</html>