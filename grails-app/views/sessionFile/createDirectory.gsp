
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <title>Create Directory</title>
    </head>
    <body>
        <div class="body">
            <h1>Add New Directory</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${directoryCommand}">
            <div class="errors">
                <g:renderErrors bean="${directoryCommand}" as="list" />
            </div>
            </g:hasErrors>
            <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
            </g:if>
            <g:form mapping="sessionFileDetails" controller="sessionFile" params="[studyId: params.studyId, sessionId: params.sessionId, directory: params.directory]" action="saveDirectory">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="directoryCommand.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: directoryCommand, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${directoryCommand?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="path">
                                    <label for="path"><g:message code="directoryCommand.path.label" default="Path" /></label>
                                </td>
                                <td valign="top" class="value">
                                     ${component.name}/${sessionObj.name}/${directory}
                                </td>
                            </tr>
                             <g:hiddenField name="path" value="${directory}" />
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" id="save" class="save right list" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" mapping="sessionFileDetails" controller="sessionFile" action="fileList" id="${params.studyId}" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
