<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Poorify | Sign Up</title>
  <link rel="stylesheet" href="css/index.css" type="text/css">
</head>
<body>

<div id="container">
  <img src="images/sfondoduotone.png">

  <div>
  <section class="form">

    <h3>SIGN UP FOR POORIFY ARTIST</h3>

    <form action="Signup" method="post" enctype="multipart/form-data" onsubmit="return signup('artist')">
      <input type="hidden" name="type" value="artist">

      <div id="email" class="box">
        <input type="email" name="email" placeholder="Email address" onblur="validateEmail(this)">
        <p class="error"></p>
      </div>

      <div id="password" class="box">
        <input type="password" name="password" placeholder="Password" onblur="validatePassword(this)">
        <p class="error"></p>
      </div>

      <div id="alias" class="box">
        <input type="text" name="alias" placeholder="Alias" onblur="validateAlias(this)">
        <p class="error"></p>
      </div>

      <div id="picture" class="box">
        <input id="picture-input" type="file" accept="image/jpeg" name="picture" onchange="validatePicture(this)">
        <label for="picture-input"><span>Upload profile picture</span></label>
        <p class="error"></p>
      </div>

      <div id="bio" class="box">
        <textarea name="bio" rows="7" cols="50" maxlength="1024">Tell us about yourself...</textarea>
        <p class="error"></p>
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