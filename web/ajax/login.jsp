<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="ajax/css.css" type="text/css" />
    </head>


    <header>
           <div class="entete">
                <div class="logo">
                <img src="ajax/ut1.png" alt="logo"></img>
            </div>
    </header>
<body>        
<!-- Where all the magic happens -->
<!-- LOGIN FORM -->
<div class="login">
	<!-- Main Form -->
	<form method="get" action="ServletConnection">
		<table class="login_tab" border="3">
			<tr>
				<th>
					Authentification 
				</th>
			</tr>
			
	        <tr><td>
				<label for="lg_username" >Username</label>
			</td></tr>
	        
	        <tr><td>
	        	<input class="login" type="text"  id="lg_username" name="lg_username" >
	        </td></tr>    
			
	        <tr><td>
					<label for="lg_password" >Password</label>
	        </td></tr>
	       
	        <tr><td>
					<input class="login" type="password"  id="lg_password" name="lg_password" >	
			</td></tr>
			
	        <tr><td>
					<label for="lg_remember">remember me</label>
					<input type="checkbox" id="lg_remember" name="lg_remember">	
			</td></tr>
			
			<tr><td>
	       		 <button class="loginbutton" type="submit"  id="btn_login">login</button>
	        </td></tr>
	         
	         <tr><td>
				<p>	<i >${requestScope.msg_erreur}</i></p>
			</td></tr>
		</table>
</form>
</div>


<!-- FORGOT PASSWORD FORM -->
<!--<div class="text-center" style="padding:50px 0">
	<div class="logo">forgot password</div>
	 Main Form 
	<div class="login-form-1">
		<form id="forgot-password-form" class="text-left">
			<div class="etc-login-form">
				<p>When you fill in your registered email address, you will be sent instructions on how to reset your password.</p>
			</div>
			<div class="login-form-main-message"></div>
			<div class="main-login-form">
				<div class="login-group">
					<div class="form-group">
						<label for="fp_email" class="sr-only">Email address</label>
						<input type="text" class="form-control" id="fp_email" name="fp_email" placeholder="email address">
					</div>
				</div>
				<button type="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
			</div>
			<div class="etc-login-form">
				
			</div>
		</form>
	</div>
	 end:Main Form 
</div>-->

        <script type="text/JavaScript" src="ajax/fctxml.js"></script>
    </body>
</html>
