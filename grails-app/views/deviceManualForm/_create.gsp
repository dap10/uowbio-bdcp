<div class="create">
	
	<g:each in="${deviceManualForms}" status="i" var="deviceManualFormsInstance">
		<g:hasErrors bean="${deviceManualFormsInstance}">
			<div class="errors">
			    <g:renderErrors bean="${deviceManualFormsInstance}" as="list" />
			</div>
		</g:hasErrors>
	</g:each>
	
    <g:uploadForm controller="deviceManualForm" action="upload" mapping="deviceManualFormDetails" params="[deviceId: params.deviceId, deviceGroupId: params.deviceGroupId]" method="post" >
        <div class="list">
            <table>
	            <thead>
	                <tr>
	                    <th>${message(code: 'deviceManualForm.formName.label', default: 'Manual Name')}</th>
	                    <th>Manual</th>
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
            <span class="button"><g:submitButton name="create" id="upload" class="save" value="Upload" /></span>
            </div>
            <div class="rowTop"><g:link elementId="return" class="forms" mapping="deviceDetails" controller="device" action="list" params="[deviceGroupId: params.deviceGroupId]">Return to Device Manuals</g:link></div>
    </g:uploadForm>
</div>