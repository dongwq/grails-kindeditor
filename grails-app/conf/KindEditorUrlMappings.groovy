class KindEditorUrlMappings {

	static mappings = {
		
		"/keDemo"(controller:"kindeditor", action: 'keDemo')
		"/kindeditor"(controller:"kindeditor")

		"/kindeditor/upload"(controller:"kindeditor", action:"upload")
		"/kindeditor/fileManager"(controller:"kindeditor", action:"fileManager")
		
		
		"500"(view:'/error')
	}
}
