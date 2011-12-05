        	     <div id="appletDiv">
			        <script type="text/javascript">

			            function getAuthToken() {
				            return 'TOKEN';
				        }

				        function appletReady() {
					        // nothing
					    }
			        
			            function startingWork() {
			                $('#appletStatus').html('Started');
			        	}
			        	 
			        	function finishedWork() {
			                $('#appletStatus').html('Finished, redirecting...');
			                setTimeout(function(){
				                	window.location.replace('${redirUrl}')
				                }, 2000);
			        	}
			        	 
			        	function unexpectedError(exceptionMessage) {
			                $('#appletStatus').html('Unexpected error, transfer canceled');
			                $('#appletError').html(exceptionMessage);
			        	}
			        	 
			        	function uploadStarting(fileAbsolutePath) {
			                $('#appletStatus').html('Uploading ' + fileAbsolutePath);
			        	}
			        	 
			        	function uploadSucceeded(fileAbsolutePath, responseBody) {
			                $('#appletStatus').html('Uploading ' + fileAbsolutePath + ' (done)');
			        	}
			        	 
			        	function uploadFailed(fileAbsolutePath, responseCode) {
			                $('#appletStatus').html('Uploading ' + fileAbsolutePath + ' (failed)');
			        	}
			        	 
			        	// The below are only relevant if you have the "sendVerificationRequestFirst" feature turned on
			        	 
			        	function handleAbortVerificationResult(fileAbsolutePath, message) {
			        	  //called when the response to the verification request has instructed that the given file be aborted
			        	  //here you probably want to show an error message against the file
			        	}
			        	 
			        	function handlePromptVerificationResult(fileAbsolutePath, message) {
			        	  //called when the response to the verification request has instructed that the user be prompted about the given file
			        	  //here you should prompt the user, and return a boolean where true=proceed with upload, false=abort upload
			        	  //MUST return a boolean response
			        	}
			        	 
			        	function verificationFailed(fileAbsolutePath, responseCode) {
			        	  // triggered when the call to the verify URL returns something other than 200
			        	  // you might want to do special handing based on the response code, e.g. 401 should indicate that the user is not authenticated
			        	}
		        	</script>
                    <applet codebase="${request.contextPath}/applets"
                            ARCHIVE="uploader.jar, lib/commons-codec-1.4.jar, lib/commons-httpclient-3.1.jar, lib/commons-logging-1.1.1.jar, lib/log4j-1.2.15.jar, lib/plugin.jar"
                            code="au.org.intersect.uploader.main.UploadApplet"
                            width="95" height="30" MAYSCRIPT>
                            <param name="mayscript" value="true" />
                            <param name="uploadURL" value="${uploadUrl}" />
                            <param name="destDir" value = "${destDir}"/>
                            <param name="allowMultiSelect" value="true" />
                            <param name="fileSelectionMode" value="files_and_folders" />
                            <param name="sendVerifyRequestFirst" value="false" />
                       Your browser does not have Java enabled.
                    </applet>
                    <div id="appletStatus"><!-- FF3 --></div>
                    <div id="appletError" style="display:none"><!-- FF3 --></div>
                 </div>   
