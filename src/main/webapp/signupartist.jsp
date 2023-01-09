<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Poorify | Sign Up</title>
  <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>

<div id="container">
  <img src="images/sfondo_index.jpg">

  <div>
  <section class="form">

    <h3>SIGN UP FOR POORIFY ARTIST</h3>

    <form action="Signup" method="post" enctype="multipart/form-data">
      <input type="hidden" name="type" value="artist">

      <div id="email" class="box">
        <p>Email address</p>
        <input type="email" name="email" placeholder="Email address" required>
      </div>

      <div id="password" class="box">
        <p>Password</p>
        <input type="password" name="password" placeholder="Password" required>
      </div>

      <div id="alias" class="box">
        <p>Alias</p>
        <input type="text" name="alias" placeholder="Alias" required>
      </div>

      <div id="picture" class="box">
        <p>Profile picture</p>
        <input type="file" accept="image/jpeg" name="picture" placeholder="Profile picture" required>
      </div>

      <div id="bio" class="box">
        <p>Biography</p>
        <textarea name="bio" rows="5" cols="50" maxlength="1024" required>Tell us about yourself...</textarea>
      </div>

      <div class="submit">
        <input type="submit" value="Signup">
      </div>

    </form>

    <div class="hr">
      <hr>
    </div>

    <div class="redirect">
      <p>
        <span>Already have an account?</span> <br>
        <a href="index.jsp">LOGIN</a> <br>
      </p>
    </div>

  </section>
  </div>

</div>

<script src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script src="js/profile/signup.js"></script>
</body>
</html>