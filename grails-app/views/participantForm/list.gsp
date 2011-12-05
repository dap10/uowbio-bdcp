
<%@ page import="au.org.intersect.bdcp.ParticipantForm" %>
<html>
    <head>
        <script type="text/JavaScript">
        function CopyMe(oFileInput, sTargetID) {
            var arrTemp = oFileInput.value.split('\\');
            document.getElementById(sTargetID).value = arrTemp[arrTemp.length - 1];
        }
		</script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'participantForm.label', default: 'ParticipantForm')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
            
        <div class="body">

            <h1>Participant ${participantInstance}</h1>
            <h2>Forms</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
           
            <g:if test="${participantFormInstanceList.size() > 0}">
            <div class="list">
             <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'participantForm.formName.label', default: 'Form Name')}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${participantFormInstanceList}" status="i" var="participantFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            
                            <td><div class="columnLeft"><g:if test="${participantFormInstanceList}" ><g:link action="downloadFile" mapping="participantFormDetails" params="[studyId: params.studyId, participantId: params.participantId]" id="${participantFormInstance.id}" >${fieldValue(bean: participantFormInstance, field: "formName")}</g:link></g:if></div>
                            <div class="columnRight">
                            <g:link mapping="participantFormDetails" elementId="delete" controller="participantForm" action="delete" method="post" params="[studyId: params.studyId, participantId: params.participantId]" id="${participantFormInstance.id}"  onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete</g:link></div></td>
                        
                        </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            <h2>Add Forms</h2>
            <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
            </g:if>
           <g:render template="create" model= ['body': body()]] />
        </div>
    </body>
</html>
