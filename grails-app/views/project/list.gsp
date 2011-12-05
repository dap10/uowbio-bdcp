
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title>Welcome Researcher</title>
    </head>
    
    <body>
    
        <div class="body">
          <h1>Welcome Researcher</h1>
          
          <div class="container_project"><g:link class="create button" controller="project" action="create">+ Add Project</g:link></div>
            
          <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
          </g:if>
          
          <g:if test="${ myProjectInstanceList?.size() > 0}">
            <div class="projects">
              <g:each in="${myProjectInstanceList}" status="i" var="projectInstance">
            	<div class="container_project">
            		<g:link action="show" id="${projectInstance.id}" class="project_title"> ${fieldValue(bean: projectInstance, field: "projectTitle")}</g:link>
            		<ul>
          		    <g:each in="${projectInstance.studies}" status="n" var="studyInstance">
                		<li><g:link mapping="studyDetails" controller="study" action="show" id="${studyInstance.id}" class="project_study" params="[projectId: projectInstance.id]">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></li>
            		  </g:each>
                  <li><g:link id="addStudy" mapping="studyDetails" class="create button" controller="study" action="create" params="[projectId: projectInstance.id]">+ Add Study</g:link></li>
            		</ul>
              	</div>
          	  </g:each>
          </div>
          <div class="paginateButtons">
			<g:paginate total="${myProjectInstanceListTotal}" />
  		  </div>
            
        </g:if>
			
			<h2>Collaborating Projects</h2>
			
			<g:if test="${ collaboratorProjectInstanceList?.size() > 0}">
				<div class="projects">
	            	<g:each in="${collaboratorProjectInstanceList}" status="i" var="projectCollInstance">
	            	
						 <p class="project_title">${fieldValue(bean: projectCollInstance, field: "projectTitle")} owned by ${fieldValue(bean: projectCollInstance, field: "owner")}</p>
	            		
	            		<ul>
	            		    <g:each in="${projectCollInstance.collaboratorStudies}" status="n" var="studyInstance">
	                       		<li><g:link mapping="studyDetails" controller="study" action="show" id="${studyInstance.id}" class="project_study" params="[projectId: projectCollInstance.id]">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></li>
	            		    </g:each>
	            		</ul>
	            		
	            	</g:each>
				</div>
			</g:if>
            
        </div>
    </body>
</html>
