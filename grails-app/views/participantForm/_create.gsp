<div class="create">
            <g:each in="${participantForms}" status="i" var="participantFormsInstance">
            <g:hasErrors bean="${participantFormsInstance}">
            <div class="errors">
                <g:renderErrors bean="${participantFormsInstance}" as="list" />
            </div>
            </g:hasErrors>
            
            </g:each>
            <g:uploadForm action="upload" mapping="participantFormDetails" params="[studyId: params.studyId, participantId: params.participantId]" method="post" >
                <div class="list">
                    <table>
                        <thead>
                        <tr>
                            <th>${message(code: 'participantForm.formName.label', default: 'Form Name')}</th>
                            <th>Form</th> 
                        	
                        </tr>
                    </thead>
                        <tbody>
                           <g:render template="form" model = "['i':0, 'body':body()]"/>
                            <g:render template="form" model = "['i':1, 'body':body()]"/>
                            <g:render template="form" model = "['i':2, 'body':body()]"/>
                            <g:render template="form" model = "['i':3, 'body':body()]"/>
                            <g:render template="form" model = "['i':4, 'body':body()]"/>
                            <g:render template="form" model = "['i':5, 'body':body()]"/>
                            <g:render template="form" model = "['i':6, 'body':body()]"/>
                            <g:render template="form" model = "['i':7, 'body':body()]"/>
                            <g:render template="form" model = "['i':8, 'body':body()]"/>
                            <g:render template="form" model = "['i':9, 'body':body()]"/>
                            
                        </tbody>
                    </table>
                </div>
                    <div class="buttons">
                    <span class="button"><g:submitButton name="create" id="upload" class="save list right" value="Upload" /></span>
                    <span class="button"><g:link elementId="back" class="list" mapping="participantDetails" controller="participant" action="list" id="${params.studyId}" params="[studyId: params.studyId]">Back</g:link></span>
                    </div>
            </g:uploadForm>
        </div>