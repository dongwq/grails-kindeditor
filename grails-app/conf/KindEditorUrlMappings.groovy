class KindEditorUrlMappings {

	static mappings = {
		
		"/kindeditor"(controller:"kindeditor")
		
		"/kindeditor/upload"(controller:"kindeditor", action:"upload")
		"/kindeditor/fileManager"(controller:"kindeditor", action:"fileManager")
		
		
		"500"(view:'/error')
	}
}
