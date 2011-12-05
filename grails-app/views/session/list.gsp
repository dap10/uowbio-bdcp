
<%@ page import="au.org.intersect.bdcp.Session" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'session.label', default: 'Session')}" />
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
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'session.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'session.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'session.description.label', default: 'Description')}" />
                        
                            <th><g:message code="session.component.label" default="Component" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sessionInstanceList}" status="i" var="sessionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sessionInstance.id}">${fieldValue(bean: sessionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sessionInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: sessionInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: sessionInstance, field: "component")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sessionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
