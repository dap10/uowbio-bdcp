package au.org.intersect.bdcp


class TraversalTagLib {

	def traversalTag = {attrs ->	
	    def file = attrs.file
		def sessionRoot = attrs.sessionRoot
	    def sessionInstance = attrs.session
		def status = attrs.status
		def parent = attrs.parent
		def position = attrs.position
		def statusValues = status.split("-")
		def incrementedValue = statusValues[1].toInteger() + 1
		def outputStatus = "${statusValues[0]}-${incrementedValue}"
		def type = file?.isDirectory() ? "folder" : "file"
		def reference = parent + '_' + position
		def relativePath = file.getAbsolutePath().substring(sessionRoot.getAbsolutePath().length()+1)
		def relativePathHTML = relativePath.replace("'","&#39")

		
		if (type == "file")
		{
			out << "<li reference='${reference}' parentDir='${parent}'><span class='${type}'>"
			out << "${file?.getName()}"
	 		out << "<input type='checkbox' class='fileSelect' name='files' parentDir='${parent}' reference='${reference}' value='F/${sessionInstance.id}/${relativePathHTML}'>"
			out << "</span>"
			out << "</li>\n"
		}
		else
		{
			File[] children = file.listFiles()
			out << "<li reference='${reference}'  parentDir='${parent}'><span class='${type}'>${file?.getName()} "
	        out << " <a href='${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:"browseFiles", params:['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':relativePath])}' id=\"upload[${status}]\"><img src=\"${resource(dir:'images/icons',file:'attach.png')}\"  alt=\"Upload Files\" title=\"Upload Files\"/></a>"
	        out << " <a href='${createLink(mapping:"sessionFileDetails", controller:"sessionFile", action:"createDirectory", params:['studyId': params.studyId,'sessionId': sessionInstance.id, 'directory':relativePath])}' id=\"createDirectory[${status}]\"><img src=\"${resource(dir:'images/icons',file:'folder_add.png')}\"  alt=\"Add Directory\" title=\"Add Directory\"/></a>"
	        out << " <input type='checkbox' class='directorySelect' name='files' parentDir='${parent}' reference='${reference}' value='D/${sessionInstance.id}/${relativePathHTML}'>"
	        out << "</span>"
			if (children?.size() > 0)
			{
				out << "\n<ul>\n"
				children.eachWithIndex() {File child, pos ->
					out << traversalTag('file': child, session: sessionInstance, status: outputStatus, 'sessionRoot': sessionRoot, parent: reference, position: pos)
				}
				out << "\n</ul>\n"
			}
			out << "</li>\n"
		}
	}

}
