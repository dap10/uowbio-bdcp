

<%@ page import="au.org.intersect.bdcp.Session" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'session.label', default: 'Session')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sessionInstance}">
            <div class="errors">
                <g:renderErrors bean="${sessionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="sessionDetails" controller="session" params="[studyId: params.studyId, componentId: params.componentId]" action="update" method="post">
                <g:hiddenField name="id" value="${sessionInstance?.id}" />
                <g:hiddenField name="version" value="${sessionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="session.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${sessionInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="session.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sessionInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${sessionInstance?.description}" />
                                </td>
                            </tr>
                        
                        </tbody>
                        <g:hiddenField name="component.id" value="${params.componentId}" />
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="save" class="save right list" action="update" value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" mapping="componentDetails" controller="component" action="list" id="${params.studyId}" params="[studyId: params.studyId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
