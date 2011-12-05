<head>
<meta name='layout' content='main' />
<title>Denied</title>
</head>

<body>
<div class='body'>
	<div class='errors'>Invalid request</div>
    <g:if test="${flash.message}">
    <div class="message"><strong>Details</strong><br/> ${flash.message}</div>
    </g:if>
</div>
</body>
