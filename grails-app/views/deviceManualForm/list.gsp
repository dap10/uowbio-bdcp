
<%@ page import="au.org.intersect.bdcp.DeviceManualForm" %>
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
        <g:set var="entityName" value="${message(code: 'deviceManualForm.label', default: 'DeviceManualForm')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
            
        <div class="body">

            <h1>Device ${deviceInstance} Manuals</h1>
            <h2>Setup Information</h2>

            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
                       
            <g:if test="${deviceManualFormInstanceList.size() > 0}">
            <div class="list">
            	<table>
                    <thead>
                        <tr>
                            <th>${message(code: 'deviceManualForm.formName.label', default: 'Manual Name')}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceManualFormInstanceList}" status="i" var="deviceManualFormInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                            <div class="columnLeft">
                            	<g:if test="${deviceManualFormInstanceList}">
                            		<g:link action="downloadFile" controller="deviceManualForm" mapping="deviceManualFormDetails" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: deviceManualFormInstance.id]">${fieldValue(bean: deviceManualFormInstance, field: "formName")}</g:link>
                            	</g:if>
                            </div>
                            <div class="columnRight">
                            	<g:link mapping="deviceManualFormDetails" elementId="delete" controller="deviceManualForm" action="delete" method="post" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId, id: deviceManualFormInstance.id]" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete</g:link>
                            </div>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            
            <h2>Add Manual</h2>
            <g:if test="${flash.error}">
            	<div class="errors"><ul><li>${flash.error}</li></ul></div>
            </g:if>
           
            <g:render template="create" model= ['body': body()]] />
            
        </div>
    </body>
</html>
