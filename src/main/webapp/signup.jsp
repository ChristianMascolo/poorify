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

    <h3>SIGN UP</h3>

    <form action="Signup" method="post" enctype="multipart/form-data" onsubmit="return signup('user')">
      <input type="hidden" name="type" value="user">

      <div id="email" class="box">
        <input type="email" name="email" placeholder="Email address" required onblur="validateEmail(this)">
        <p class="error"></p>
      </div>

      <div id="password" class="box">
        <input type="password" name="password" placeholder="Password" required onblur="validatePassword(this)">
        <p class="error"></p>
      </div>

      <div id="alias" class="box">
        <input type="text" name="alias" placeholder="Alias" required onblur="validateAlias(this)">
        <p class="error"></p>
      </div>

      <div id="picture" class="box">
        <input id="picture-input" type="file" accept="image/jpeg" name="picture" onchange="validatePicture(this)">
        <label for="picture-input"><span>Upload profile picture</span></label>
        <p class="error"></p>
      </div>

      <div id="birthdate" class="box">
        <input type="date" name="birthdate" required onblur="validateBirthdate(this)">
        <p class="error"></p>
      </div>

      <div id="nation" class="box">
        <select name="nation" required>
          <!-- FILLED BY JAVASCRIPT -->
        </select>
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
