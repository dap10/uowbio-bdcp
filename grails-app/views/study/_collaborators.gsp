            
<g:link elementId="Add Collaborator" controller="study" class="create button" action="searchCollaborators" params="[studyId: studyInstance.id]">+ Add Collaborator</g:link>

<g:if test="${ collaboratorInstanceTotal > 0}">
  <div class="list">
    <table>
      <thead>
        <tr>
          <th>${message(code: 'collaborator.identifier.label', default: 'Collaborator ID')}</th>
          <th>${message(code: 'collaborator.givenname.label', default: 'Given Name')}</th>
          <th>${message(code: 'collaborator.surname.label', default: 'Surname')}</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${collaboratorInstanceList}" status="i" var="collaboratorInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><div class="columnLeft">${fieldValue(bean: collaboratorInstance, field: "uid")}</div></td>
            <td><div class="columnLeft">${fieldValue(bean: collaboratorInstance, field: "givenName")}</div></td>
            <td><div class="columnLeft">${fieldValue(bean: collaboratorInstance, field: "sn")}</div></td>
            <td>
              <g:link class="myDelete" elementId="delete[${i}]" mapping='deleteCollaborator' controller="study" action="deleteCollaborator" params="[collaboratorUid: collaboratorInstance.uid, studyId: studyInstance.id]">Delete</g:link>
            </td>
          </tr>
        </g:each>
      </tbody>
    </table>
  </div>
</g:if>
