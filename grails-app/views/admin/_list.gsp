            
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
                             <td><g:link elementId="select[${i}]" controller="admin" action="addRole" params="[username: matchInstance.username.toArray()[1], givenName: matchInstance.givenName, sn: matchInstance.sn]">Select</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons" >
                <g:paginate total="${matches}"/>
            </div>
           </g:if>
           