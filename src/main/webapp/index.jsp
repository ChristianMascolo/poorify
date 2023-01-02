<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Poorify | Your Music, Your Mood</title>
</head>
<body>

  <section class="form">

    <h2>LOGIN</h2>

    <form id="login-form" action="Login" method="post">
      <div id="email" class="box">
        <p>Email address</p>
        <input type="email" name="email" placeholder="Email address">
      </div>
      <div id="password" class="box">
        <p>Password</p>
        <input type="password" name="password" placeholder="Password">
      </div>
      <div class="submit">
        <input type="button" value="Login" onclick="login()">
      </div>
    </form>

    <div class="error">
      <p id="error-message"></p>
    </div>

    <div class="hr">
      <hr>
    </div>

    <div class="redirect">
      <p>Don't have an account?</p>
      <a href="signup.jsp">SIGN UP</a> <br>
      <a href="signupartist.jsp">SIGN UP FOR POORIFY ARTIST</a>
    </div>

  </section>

<script src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script src="js/profile/login.js"></script>
</body>
</html>
