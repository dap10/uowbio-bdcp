

<%@ page import="au.org.intersect.bdcp.StudyDevice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDevice.label', default: 'StudyDevice')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="body">
        <h1>Add Device to ${studyInstance?.studyTitle}</h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${studyDeviceInstance}">
        
          <div class="errors">
              <g:renderErrors bean="${studyDeviceInstance}" as="list" />
          </div>
          
          </g:hasErrors>
            <div class="projects">
              <g:each in="${deviceGroupList}" status="i" var="deviceGroupInstance">
              
                <h2>${deviceGroupInstance.groupingName}</h2>
                <ul>
                  <g:each in="${deviceGroupInstance.getDevicesList()}" status="j" var="deviceInstance">
                    <g:if test="${au.org.intersect.bdcp.StudyDevice?.findByStudyAndDevice(au.org.intersect.bdcp.Study?.findById(params.studyId), deviceInstance) == null}">
                      <li>${deviceInstance.name} <g:link elementId="select_${j}" mapping="studyDeviceFieldDetails" class="button" action="edit" params="['deviceId':deviceInstance.id,'studyId':params.studyId]">Select</g:link></li>
                    </g:if>
                    <g:else>
                     <li>${deviceInstance.name} ... already selected</li>
                    </g:else>
                  </g:each>
                </ul>
              </g:each>
            </div>
            <div class="buttons">
                <span class="button"><g:link elementId="cancel" class="list" mapping="studyDeviceDetails" controller="studyDevice" action="list" params="[studyId: params.studyId]">Cancel</g:link></span>
        </div>
      </div>
    </body>
</html>
