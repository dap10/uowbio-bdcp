
<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title>${deviceGroupInstance?.groupingName}</title>
    </head>
    <body>
      <div class="body">
        <h1>${deviceGroupInstance?.groupingName}</h1>
        <br />
            <g:link elementId="Add new device" mapping="deviceDetails" controller="device" class="button" action="create" params="[deviceGroupId: params.deviceGroupId]">Add new device</g:link>
            <br />
            <br />
            <g:if test="${deviceInstanceList.size() > 0}">
            <h2>Devices</h2>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>${message(code: 'device.name.label', default: 'Name')}</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceInstanceList}" status="i" var="deviceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link elementId="${deviceInstance.name}" mapping="deviceDetails" controller="device" action="show" id="${deviceInstance.id}" params="[deviceGroupId: params.deviceGroupId]">${fieldValue(bean: deviceInstance, field: "name")}</g:link></td>
                            <td><g:link elementId="edit[${i}]" class="button" mapping="deviceDetails" controller="device" action="edit" id="${deviceInstance.id}" params="[deviceGroupId: params.deviceGroupId]">Edit</g:link>                       
                            <g:link elementId="device-details[${i}]" mapping="deviceFieldDetails" class="list" class="button" action="list" controller = "deviceField" params="[deviceGroupId: params.deviceGroupId, deviceId: deviceInstance.id]">Device Details</g:link>
                            <g:link elementId="forms[${i}]" mapping="deviceManuals" controller="deviceManualForm" class="list" class="button" action="list" params="[deviceGroupId: params.deviceGroupId, deviceId: deviceInstance.id]">Manuals</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
        <div class="buttons">
          <span class="menuButton">
            <g:link elementId="Back" controller="deviceGroup" class="list" action="list">Back</g:link>
          </span>
        </div>
      </div>
    </body>
</html>
