
<%@ page import="au.org.intersect.bdcp.DeviceGroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceGroup.label', default: 'Device Administration')}" />
        <title>Device Administration</title>
    </head>
    <body>
        <div class="body">
            <h1>Device Administration</h1>
            <g:link elementId="createGrouping" class="button" action="create">+ Create Grouping</g:link>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test ="${deviceGroupInstanceList.size() > 0}" >
            
            <h2>Existing Device Groupings</h2>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th class="tablename">${message(code: 'deviceGroup.groupingName.label', default: 'Grouping Name')}</th>
                            <th class="tablebuttons"></th>
                        </tr>
                    </thead>
                    
                    <tbody>
	                    <g:each in="${deviceGroupInstanceList}" status="i" var="deviceGroupInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td class="tablename"><g:link elementId="${deviceGroupInstance.groupingName}" mapping="deviceDetails" controller="device" action="list" params="[deviceGroupId: deviceGroupInstance.id]">${fieldValue(bean: deviceGroupInstance, field: "groupingName")}</g:link></td>
                            <td class="tablebuttons"><g:link elementId="edit-name[${i}]" class="button" action="edit" id="${deviceGroupInstance.id}">Edit name</g:link></td>
                        </tr>
	                    </g:each>
                    </tbody>
               </table>
               
            </div>
            </g:if>
            
            <div class="buttons">
                <span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="systemAdmin">Back</g:link></span>
            </div>
        </div>
    </body>
</html>
