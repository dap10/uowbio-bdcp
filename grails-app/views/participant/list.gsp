
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <meta name="layout" content="main" />
    <g:javascript library="application" />
    <g:javascript library="jquery" plugin="jquery"/>
 		<jqui:resources />
    <g:set var="entityName" value="${message(code: 'participant.label', default: 'Participant')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>  
  </head>
  
  <body>
     <div class="body" id="tab2">
          <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
      <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
          </g:if>
      <div id="participant">
      
      <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab2']}" />

      <g:render template="participants" />
      </div>
</div>
  </body>
</html>