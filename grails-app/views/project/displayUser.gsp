
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
            
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
			<h1>Project Details</h1>
			
			<g:if test="${ projectInstance }">
				<div class="projects">

					<p class="project_title">
						<g:link action="show" id="${projectInstance.id}" class="project_title"> ${fieldValue(bean: projectInstance, field: "projectTitle")} owned by ${firstName} ${surname}</g:link>
					</p>
	            		
            		<ul>
            		    <g:each in="${projectInstance.studies}" status="n" var="studyInstance">
                       		<li><g:link mapping="studyDetails" controller="study" action="show" id="${studyInstance.id}" class="project_study" params="[projectId: projectInstance.id]">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></li>
            		    </g:each>
            		    
            		    <li><g:link id="addStudy" mapping="studyDetails" class="create" controller="study" action="create" params="[projectId: projectInstance.id]">+ Add Study</g:link></li>
            		</ul>
	            		
	            	<br />
	            		
				</div>
			</g:if>
                        
			<div class="rowBottom">
				<div class="buttons"><span class="menuButton"><g:link elementId="Back" controller="project" class="list" action="listAll">Back</g:link></span></div>
			</div>
			
        </div>

    </body>
</html>
