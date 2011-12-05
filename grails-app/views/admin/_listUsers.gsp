            
           <g:if test="${ matches.size() > 0}">
           <div class="list">
                <table id="listTable">
                    <thead>
                        <tr>
                        
                            <th>Surname</th>
                        	<th>Given Name</th>
                        	<th>User ID</th>
                        	<th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${matches}" status="i" var="matchInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td> ${matchInstance.sn }</td>
                            <td> ${matchInstance.givenName }</td>
                            <td>${matchInstance.username.toArray()[1]}</td>
                            <td><g:link elementId="edit[${i}]" class="button list right" controller="admin" action="edit" params="[username: matchInstance.username.toArray()[1], hideUsers: hideUsers]">Edit</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons" >
                <g:paginate total="${matches}"/>
            </div>
           </g:if>
           