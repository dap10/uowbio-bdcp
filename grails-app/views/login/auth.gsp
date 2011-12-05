<head>
<meta name='layout' content='main' />
<title>Login</title>
</head>

<body>
	<div id='login'>
		<div class='inner'>
			<h1>Biomechanics</h1>
			<p>Please enter your userid and password to login</p>
			<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
				<div>
					<label for='username'>User ID</label>
					<input type='text' class='text_' name='j_username' id='username' />
				</div>
				<div>
					<label for='password'>Password</label>
					<input type='password' class='text_' name='j_password' id='password' />
				</div>
        <div id="buttons">
  				<input id="Login" class="button right" type='submit' value='Login' />
					<input id="Clear" class="button" type="reset" value="Clear" />
		   </div>
		 </form>
			<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
			</g:if>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>
</body>
