<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Poorify | Sign Up</title>
</head>
<body>
  <section class="form">

    <h2>SIGN UP</h2>

    <form action="Signup" method="post" enctype="multipart/form-data" onsubmit="return signup('user')">
      <input type="hidden" name="type" value="user">

      <div id="email" class="box">
        <p>Email address</p>
        <input type="email" name="email" placeholder="Email address" required onblur="validateEmail(this)">
        <p class="error"></p>
      </div>
      <div id="password" class="box">
        <p>Password</p>
        <input type="password" name="password" placeholder="Password" required onblur="validatePassword(this)">
        <p class="error"></p>
      </div>
      <div id="alias" class="box">
        <p>Alias</p>
        <input type="text" name="alias" placeholder="Alias" required onblur="validateAlias(this)">
        <p class="error"></p>
      </div>
      <div id="picture" class="box">
        <p>Profile picture</p>
        <input id="picture-input" type="file" accept="image/jpeg" name="picture" onchange="validatePicture(this)">
        <label for="picture-input"><span>Profile picture</span></label>
        <p class="error"></p>
      </div>

      <div id="birthdate" class="box">
        <p>Birthdate</p>
        <input type="date" name="birthdate" placeholder="Birthdate" required onblur="validateBirthdate(this)">
        <p class="error"></p>
      </div>

      <div id="nation" class="box">
        <p>Nation</p>
          <select name="nation" required>
            <!-- FILLED BY JAVASCRIPT -->
          </select>
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
      <p>Already have an account?</p>
      <a href="index.jsp">LOGIN</a> <br>
    </div>

  </section>

<script src="webjars/jquery/3.5.1/dist/jquery.js"></script>
<script src="js/profile/signup.js"></script>
</body>
</html>
