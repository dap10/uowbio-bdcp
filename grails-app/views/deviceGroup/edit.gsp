

<%@ page import="au.org.intersect.bdcp.DeviceGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceGroup.label', default: 'DeviceGroup')}" />
        <title>Edit Device Grouping</title>
    </head>
    <body>
        <div class="body">
            <h1>Edit Device Grouping</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceGroupInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceGroupInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${deviceGroupInstance?.id}" />
                <g:hiddenField name="version" value="${deviceGroupInstance?.version}" />
                <g:render template="modifyDialog" model= ['body': body()]] />
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="save" class="save list" action="update" value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" action="list">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
