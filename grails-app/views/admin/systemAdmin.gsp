<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>System Administration</title>
  </head>
  
  <body>
    <div class="body">
    
      <h1>System Administration</h1>
      
      <div class="rowTop">
        <g:link elementId="account-administration" class="button" controller="admin" action="accountAdmin">Account Administration</g:link> 
        <g:link elementId="device-administration" class="button" controller="deviceGroup" action="list">Device Administration</g:link>
        <g:link elementId="results-administration" class="button" controller="admin" action="resultsAdmin">Results Administration</g:link>
      </div>
      
      <div class="rowBottom">
        <div class="buttons">
          <span class="menuButton">
            <g:link elementId="Back" controller="project" class="list" action="list">Back</g:link>
          </span>
        </div>
      </div>
    
    </div>
  </body>
</html>