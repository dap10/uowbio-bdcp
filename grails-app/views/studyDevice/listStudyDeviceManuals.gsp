
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

            <h1>Device manuals for ${deviceInstance}</h1>

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
                            		<g:link elementId="manualForm[${i}]" action="downloadFile" controller="studyDevice" mapping="studyDeviceManuals" params="[studyId: params.studyId, deviceId: params.deviceId, id: deviceManualFormInstance.id]">${fieldValue(bean: deviceManualFormInstance, field: "formName")}</g:link>
                            	</g:if>
                            </div>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            <g:elseif test="${deviceManualFormInstanceList.size() == 0}">
            	<div class="message">There are no device manuals for this device</div>
            </g:elseif>
            <div class="rowBottom"><g:link elementId="return" class="forms" mapping="studyDeviceManuals" controller="studyDevice" action="list" params="[studyId: params.studyId, deviceId: params.deviceId]">Return to Study Devices</g:link></div>
        </div>
        
    </body>
</html>
