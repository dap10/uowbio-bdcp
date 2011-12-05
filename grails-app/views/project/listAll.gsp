
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title>All Projects</title>
    </head>
    
    <body>
    
        <div class="body">
            <h1>All Projects</h1>
            
            <g:link class="create button" controller="project" action="searchUsers">+ Add Project</g:link>
            
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <g:if test="${ allProjectInstanceList?.size() > 0}">
				<div class="list">
	                <table id="searchTable">
	                    <thead>
	                        <tr>                        
	                            <th>Researcher Name</th>
	                        	<th>Project Name</th>
	                        </tr>
	                    </thead>
	                    <tbody>
						<g:each in="${allProjectInstanceList}" status="i" var="projectInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>${projectInstance.owner.firstName} ${projectInstance.owner.surname}</td>
								<td><g:link id="${projectInstance.id}" url="${createLink(controller:'project', action:'displayUser', params:['id': projectInstance.id, firstName: projectInstance.owner.firstName, surname: projectInstance.owner.surname])}"> ${projectInstance.projectTitle}</g:link></td>
	                        </tr>
	                    </g:each>
	                    </tbody>
	                </table>
	            </div>
	            <div class="paginateButtons">
					<g:paginate total="${ allProjectInstanceListTot }" />
				</div>
	            
            </g:if>
            
				<div class="buttons"><span class="menuButton"><g:link elementId="Back" controller="project" class="list" action="list">Back</g:link></span></div>
            
        </div>
    </body>
</html>

