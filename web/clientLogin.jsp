<%-- 
    Document   : login
    Created on : Jun 7, 2021, 2:41:00 PM
    Author     : Ugochukwu123
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>GBook Here! | </title>

    <!-- Bootstrap -->
    <link href="vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://colorlib.com/polygon/gentelella/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
              <form action="Router" method="post">
              <h1>Login Form</h1>
              <div>
                  <input type="text" class="form-control" placeholder="Email" required="" name="email" />
              </div>
              <div>
                  <input type="password" class="form-control" placeholder="Password" required="" name="pwd" />
              </div>
              <input name="origin" value="client_login" hidden/>
              <div>
                  <button class="btn btn-default submit" type="submit">Login</button>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">New to site?
                  <a href="index.html" class="to_register"> Create Account </a>
                </p>

                <div class="clearfix"></div>
                <br />
          <h1><i class="fa fa-paw"></i> BOOK HERE!</h1>
                  <p>©2021 All Rights Reserved. UmehTech</p>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
