<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%  // response.setHeader( "Pragma", "no-cache" );   response.setHeader( "Cache-Control", "no-cache" ); response.setDateHeader( "Expires", 0 );%>
<%@ page import="com.salesforce.SalesForceEnvironment, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
try{
    
%>
<!DOCTYPE html>
<html dir="ltr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicon.png">
    <title>SaleForce Application</title>
    <!-- Custom CSS -->
    <link href="dist/css/style.min.css" rel="stylesheet">
    <link href="lib/parsleyjs/parsley.css" rel="stylesheet">
    <link href="assets/libs/sweetalert2/dist/sweetalert2.min.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <div class="main-wrapper">
        <!-- ============================================================== -->
        <!-- Preloader - style you can find in spinners.css -->
        <!-- ============================================================== -->
        <div class="preloader">
            <div class="lds-ripple">
                <div class="lds-pos"></div>
                <div class="lds-pos"></div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Preloader - style you can find in spinners.css -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->
        <div class="auth-wrapper d-flex no-block justify-content-center align-items-center" style="background:url(assets/images/big/auth-bg.jpg) no-repeat center center;">
            <div class="auth-box">
                <div id="loginform">
                    <div class="logo">
                        <span class="db"><img src="assets/images/logo-icon.png" alt="logo" /></span>
                        <h5 class="font-medium m-b-20">Sign In to Admin</h5>
                    </div>
                    <!-- Form -->
                    <div class="row">
                        <div class="col-12">
                            <form class="form-horizontal m-t-20" id="login-form" data-parsley-validate="">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon1"><i class="ti-user"></i></span>
                                    </div>
                                    <input type="email" class="form-control form-control-lg" placeholder="Username(Email Address)" id="useremail" name="useremail" data-parsley-required="true" data-parsley-type="email" data-parsley-trigger="keyup"  aria-label="Username" aria-describedby="basic-addon1">

                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon2"><i class="ti-pencil"></i></span>
                                    </div>
                                   
                                    <input type="text" class="form-control form-control-lg" placeholder="Password"  id="password" name="password" aria-label="Password" data-parsley-minlength="4" data-parsley-required="true" data-parsley-trigger="keyup" aria-describedby="basic-addon1">
                            
                                </div>
                                <div class="form-group row">
                                    <div class="col-md-12">
                                        <div class="custom-control custom-checkbox">
                                            
                                            <a href="javascript:void(0)" id="to-recover" class="text-dark float-right"><i class="fa fa-lock m-r-5"></i> Forgot pwd?</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group text-center">
                                    <div class="col-xs-12 p-b-20">
                                        <button class="btn btn-block btn-lg btn-info" type="submit" onclick="javascript:fnLogin();">Log In</button>
                                    </div>
                                </div>
                                
                                
                                <input type="hidden" id="qs" name="qs">
                                <input type="hidden" id="rules" name="rules">
                                <input type="hidden" id="hdnuseremail" name="hdnuseremail">
                            </form>
                        </div>
                    </div>
                </div>
                <div id="recoverform">
                    <div class="logo">
                        <span class="db"><img src="assets/images/logo-icon.png" alt="logo" /></span>
                        <h5 class="font-medium m-b-20">Recover Password</h5>
                        <span>Enter your Email and instructions will be sent to you!</span>
                    </div>
                    <div class="row m-t-20">
                        <!-- Form -->
                        <form class="col-12" action="index.html">
                            <!-- email -->
                            <div class="form-group row">
                                <div class="col-12">
                                    <input class="form-control form-control-lg" type="email" required="" placeholder="Username">
                                </div>
                            </div>
                            <!-- pwd -->
                            <div class="row m-t-20">
                                <div class="col-12">
                                    <button class="btn btn-block btn-lg btn-danger" type="submit" name="action">Reset</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Page wrapper scss in scafholding.scss -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Page wrapper scss in scafholding.scss -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Right Sidebar -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Right Sidebar -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- All Required js -->
    <!-- ============================================================== -->
    <script src="assets/libs/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
    <script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/libs/sweetalert2/dist/sweetalert2.all.min.js"></script>
    <script src="lib/parsleyjs/parsley.js"></script>
    <!-- ============================================================== -->
    <!-- This page plugin js -->
    <!-- ============================================================== -->
    <script>
    $('[data-toggle="tooltip"]').tooltip();
    $(".preloader").fadeOut();
    // ============================================================== 
    // Login and Recover Password 
    // ============================================================== 
    $('#to-recover').on("click", function() {
        $("#loginform").slideUp();
        $("#recoverform").fadeIn();
    });
    
    $(document).ready(function(){
        $('#login-form').parsley();
     });
    function fnLogin(){
    	
		 	var useremail=$("#useremail").val();   	
		 	var password=$("#password").val();   	
		 	var pubkey='<%=SalesForceEnvironment.getAPIKeyPublic()%>';
		 	var pvtkey='<%=SalesForceEnvironment.getAPIKeyPrivate()%>';
		 	var url='<%=SalesForceEnvironment.getJSONServletPath()%>';
		    var qslogin="lgn";
		    var loginrule="jloginvalidate"; 
		 	// Parsley Validation 
		 	var isValid = true;
	          $('#login-form').each( function() {
	            if ($(this).parsley().validate() !== true) isValid = false;
	          });
	          
	          if (isValid===false) {
	          return;
	          }
	          var jvariables = JSON.stringify({ qs: qslogin, rules:loginrule, useremail: useremail, password:password, apikey:pubkey, pvtkey:pvtkey});
    	
	          $.ajax({
	                beforeSend: function(xhr){  xhr.overrideMimeType( "text/plain; charset=x-user-defined" );},// Include this line to specify what Mime type the xhr response is going to be
	                url: url,  type: "POST", dataType: "json",  data:{ objarray : jvariables },
	                success: function (result) {
	                	
	                	if (result) {
	                		if(result['error']=='false'){
	                			 var verifieduseremail=result.useremail;
	                		       Swal.fire({
	                                   	icon: 'success',
	                                    text: 'Login Successful',
	                                    confirmButtonText: 'Ok',
	                                    showConfirmButton: true,
	                                    timer: 2000,
	                              }).then(function() {
	                            	// Call Dashboard Page
	                            	 	$('#login-form').attr('action', '<%=SalesForceEnvironment.getServletPath()%>');
	                            	    $('input[name="qs"]').val('dash');
	                            	    $('input[name="rules"]').val('Dashboard');
	                            	    $('input[name="hdnuseremail"]').val(verifieduseremail);
	                            	    $("#login-form").submit();	
	                              }); 
	                			
	                			
	                		}else if(result['error']=='incorrect') {
	                		
	                			
	                		      swal.fire({   
                                      title:'Incorrect Login Credentials!',
                                      text: "You have entered wrong Login Credentials",
                                      icon: "error",
                                      })
	                		
	                		}else if(result['error']=='true') {
	                		      swal.fire({   
                                    title:'Login Failed!',
                                    text: "There is a problem with signing in. Please Contact your System Administrator",
                                    icon: "error",
                                    })
	                		
	                		}
	                		
	                	}
	                }
	            }); 
	          
    	
    }
    
    </script>
</body>

</html>

<% 
}catch(Exception e){
	
}
finally{
	
}

%>