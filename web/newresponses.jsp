<%-- 
    Document   : newproduct
    Created on : Jul 18, 2021, 3:15:15 PM
    Author     : user
--%>

<%@page import="usermodel.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Book Here! | </title>

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
  
 
  <%
    User user = (User) session.getAttribute("loggedUser");
    if(user==null){
        response.sendRedirect("clientLogin.jsp");
    }
  %>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form method="POST" action="Router">
              <h1>Add a New Responses</h1>
              <div>
                <label>First Response</label>
                <input type="text" class="form-control" name="name" placeholder="Product Name" required>
                <label></label>
                <input type="text" class="form-control" name="description" placeholder="Description" required>
                <label>Price</label>
                <input type="number" class="form-control" name="price" placeholder="Price" required>
                <input name="origin" value="add_new_product" hidden/>
              <div>
                  <button class="btn btn-default submit" type="submit">Add Product</button>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
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
