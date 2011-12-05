

<%@ page import="au.org.intersect.bdcp.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <g:set var="ownerName" value="${nameCreateProjFor}" />
        <title><g:message code="default.create.label.for.user" args="[entityName,ownerName]" /></title>
    </head>
    <body>
    <div class="body">
        
            <h1><g:message code="default.create.label.for.user" args="[entityName,ownerName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${projectInstance}">
            <div class="errors">
                <g:renderErrors bean="${projectInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="saveUserProject" >
            	<g:hiddenField name="projectOwnerName" value="${ownerName}"></g:hiddenField>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="projectTitle"><g:message code="project.projectTitle.label" default="Project Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
                                    <g:textField name="projectTitle" value="${username}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="researcherName"><g:message code="project.researcherName.label" default="Researcher Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'researcherName', 'errors')}">
                                    <g:textField name="researcherName" value="${projectInstance?.researcherName}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="studentNumber"><g:message code="project.studentNumber.label" default="Student Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'studentNumber', 'errors')}">
                                    <g:textField name="studentNumber" value="${projectInstance?.studentNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="degree"><g:message code="project.degree.label" default="Degree" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'degree', 'errors')}">
                                    <g:textField name="degree" value="${projectInstance?.degree}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="startDate"><g:message code="project.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'startDate', 'errors')}">
                                    <g:datePicker name="startDate" precision="month" value="${projectInstance?.startDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endDate"><g:message code="project.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'endDate', 'errors')}">
                                    <g:datePicker name="endDate" precision="month" value="${projectInstance?.endDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="project.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${projectInstance?.description}" rows="5" cols="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="supervisors"><g:message code="project.supervisors.label" default="Supervisor(s)" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'supervisors', 'errors')}">
                                    <g:textArea name="supervisors" value="${projectInstance?.supervisors}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save list right" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                	<span class="button"><g:link elementId="cancel" class="list" controller="project" action="listAll">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
