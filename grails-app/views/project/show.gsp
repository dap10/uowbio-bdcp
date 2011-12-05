
<%@ page import="au.org.intersect.bdcp.Project" %>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.showTitle.label" args="[projectInstance.projectTitle]" /></title>
    </head>
    
    <body>
            
      <div class="body">
      
      	<h1><g:message code="default.showTitle.label" args="[projectInstance.projectTitle]" /></h1>
          
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        
        <div class="dialog">
        
            <table id="projectTable">
              <tbody>
                <tr class="prop">
                  <td valign="top" class="name"><g:message code="project.projectTitle.label" default="Project Title" /></td>                         
                  <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "projectTitle")}</td>
                </tr>
            
                <tr class="prop">
                  <td valign="top" class="name"><g:message code="project.researcherName.label" default="Researcher Name" /></td>
                  <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "researcherName")}</td> 
                </tr>
                
        				<tr class="prop">
                    <td valign="top" class="name"><g:message code="project.studentNumber.label" default="Student Number" /></td>
                    <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "studentNumber")}</td>
                </tr>
            
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="project.degree.label" default="Degree" /></td>
                    <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "degree")}</td>
                </tr>
            
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="project.startDate.label" default="Start Date" /></td>
                    <td valign="top" class="value"><g:formatDate format="MM/yyyy" date="${projectInstance?.startDate}" /></td>
                </tr>
            
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="project.endDate.label" default="End Date" /></td>
                    <td valign="top" class="value"><g:formatDate format="MM/yyyy" date="${projectInstance?.endDate}" /></td>
                </tr>
            
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="project.description.label" default="Description" /></td>
                    <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "description")}</td>
                </tr>
            
                <tr class="prop">
                    <td valign="top" class="name"><g:message code="project.supervisors.label" default="Supervisor(s)" /></td>
                    <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "supervisors")}</td>
                </tr>
              
              </tbody>
            </table>
            
        </div>
        <div class="buttons">
          <g:form class="clearfix">
              <g:hiddenField name="id" value="${projectInstance?.id}" />
              <span class="button"><g:actionSubmit class="edit right list" id="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
          	<span class="button"><g:link class="list" action="list">Back</g:link></span>
          </g:form>
        </div>
      </div>
    </body>
</html>
