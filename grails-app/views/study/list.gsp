
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
        
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">
            
                <table>
                    <thead>
                        <tr class="tablename">
                        
                            <g:sortableColumn property="studyTitle" title="${message(code: 'study.studyTitle.label', default: 'Study Title')}" />
                        
                            <g:sortableColumn property="uowEthicsNumber" title="${message(code: 'study.uowEthicsNumber.label', default: 'UOW Ethics Number')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'study.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="industryPartners" title="${message(code: 'study.industryPartners.label', default: 'Industry Partners')}" />
                        
                            <g:sortableColumn property="collaborators" title="${message(code: 'study.collaborators.label', default: 'Collaborators')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${studyInstanceList}" status="i" var="studyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${studyInstance.id}">${fieldValue(bean: studyInstance, field: "studyTitle")}</g:link></td>
                        
                            <td>${fieldValue(bean: studyInstance, field: "uowEthicsNumber")}</td>
                        
                            <td>${fieldValue(bean: studyInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: studyInstance, field: "industryPartners")}</td>
                        
                            <td>${fieldValue(bean: studyInstance, field: "collaborators")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${studyInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
