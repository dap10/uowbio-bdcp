class UrlMappings
{

	static mappings =
	{
		name participantDetails: "/study/$studyId/participant/$action?/$id?" {
			controller = 'participant'
	     }
		
        name studyDeviceDetails: "/study/$studyId/studyDevice/$action?/$id?" {
            controller = 'studyDevice'
        }
        
        name studyDeviceFieldDetails: "/study/$studyId/studyDeviceField/$deviceId/$action?" {
            controller = 'studyDeviceField'
        }
		
		name studyDeviceManuals: "/study/$studyId/device/$deviceId/$controller/$action?" {
            controller = 'studyDevice'
        }
        
        name deviceDetails: "/deviceGroup/$deviceGroupId/device/$action?/$id?" {
            controller = 'device'
        }
		
		name deviceManuals: "/deviceGroup/$deviceGroupId/device/$deviceId/deviceManualForm/$action?/$id?" {
            controller = 'deviceManualForm'
        }
		
		name deviceManualFormDetails: "/deviceGroup/$deviceGroupId/device/$deviceId/$controller/$action?/$id?" {
			controller = 'deviceManualForm'
		}
		
		name studyAnalysedData: "/studyAnalysedData/$studyId/$action?" {
			controller = 'studyAnalysedData'
	    }
		
		name studyCollaborators: "/study/$studyId/$action?/$id?" {
			controller = 'study'
	    }
		
		name addCollaborator: "/study/$studyId/$action?/$id?" {
			controller = 'study'
		}
		
		name deleteCollaborator: "/study/$studyId/$action?/$id?" {
			controller = 'study'
		}
		
		name collaboratorDetails: "/study/$studyId/$action?/$id?" {
			controller = 'study'
		 }
        
        name deviceFieldDetails: "/deviceGroup/$deviceGroupId/device/$deviceId/$controller/$action?/$id?" {
            controller = 'deviceField'
        }
        
		name componentDetails: "/study/$studyId/component/$action?/$id?" {
			controller = 'component'
		 }
		
		name sessionDetails: "/study/$studyId/component/$componentId/session/$action?/$id?" {
			controller = 'session'
		 }
		
		name sessionFileDetails: "/study/$studyId/session/$sessionId?/sessionFile/$action?/$id?" {
			controller = 'sessionFile'
		 }
		
		name sessionFileList: "/study/$studyId/sessionFile/$action?/$sessionId?/$id?" {
			controller = 'sessionFile'
		 }
		
		name studyDetails: "/project/$projectId/study/$action?/$id?" {
			controller = 'study'
		 }
		
		name participantFormDetails: "/study/$studyId/participant/$participantId/$controller/$action?/$id?" {
			controller = 'participantForm'
		 }
		
		"/$controller/$action?/$id?"
		{ constraints {
				// apply constraints here
			} }
		"/"(controller:"project", action:"list")
		"404"(view:'/notfound')
		"500"(view:'/error')
		
	}
}
