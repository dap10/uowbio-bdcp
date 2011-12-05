           <g:if test="${ matches.size() > 0}">
           <h2>Results</h2>
           <div class="list">
                <table id="searchTable">
                    <thead>
                        <tr>
                        
                            <th>User ID</th>
                        	<th>Given Name</th>
                        	<th>Surname</th>
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${matches}" status="i" var="matchInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${matchInstance.username.toArray()[1]}</td>
                             <td> ${matchInstance.givenName }</td>
                             <td> ${matchInstance.sn }</td>
                             <td><g:link elementId="select[${i}]" class="button right list" url="${createLink(mapping: 'addCollaborator', controller:'study', action:'addCollaborator', params:['studyId': studyInstance.id, 'username': matchInstance.username.toArray()[1]])}">Select</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons" >
                <g:paginate total="${matches}"/>
            </div>
           </g:if>
           