<!DOCTYPE html>
<html>
  
  <head>
    <title>Error</title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <g:javascript library="application" />
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store">
 		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-store">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  	<META HTTP-EQUIV="Expires" CONTENT="-1">
  </head>
  
  <body class="yui-skin-sam" onunload="">
    
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
    </div>
    
    <div id="header">      
        <div id="biomechsLogo">
          <a href="http://www.uow.edu.au/health/brl/index.html"><img src="${resource(dir:'images',file:'logo_brl.png')}" alt="Biomechanics" border="0" /></a>
          
          <div id="uowLogo">
            Biomechanics Research Laboratory is a part of:<br>
            <img src="${resource(dir:'images',file:'logo_uow.png')}" alt="University of Wollongong" width="188" height="33">
          </div>
        </div>
      </div>

    <div id="bg_gradient" class="clearfix">
      
      <div id="access">
          <sec:ifLoggedIn>
            <ul id="primary_navigation">  
              <li><a class="home button" id="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
              <li><g:link controller="project" class="list button" action="list">Project List</g:link></li>
              <li><g:link elementId="all-projects" controller="project" class="list button" action="listAll">All Projects</g:link></li>
              <li><g:link elementId="system-administration" class="admin button" controller="admin" action="systemAdmin"><g:message code="default.system.admin.label"/></g:link>
              </li>
            </ul>
          </sec:ifLoggedIn>
          
          <div id="profile_options">
            <sec:ifLoggedIn>
  	          Welcome, <sec:username />
  	          <ul>
  	            <li>
  	              <g:link elementId="Logout" controller="logout">Logout</g:link>
  	            </li>
  	          </ul>
            </sec:ifLoggedIn>
          </div>
        </div>

        <div style="margins:20 20 20 20;">
		<strong>An internal error occurred.<strong><br />
		Apologies for the inconvinience. If the error persist please contact us at the Biomechanics Lab at University of Wollongong.
		<hr />
        </div>
        
      </div>
    
      <div id="footer">
        Developed by Intersect Australia Ltd <div style="float:right;"><g:render template="/version"/></div>
      </div>

    </body>
  </html>
</html>