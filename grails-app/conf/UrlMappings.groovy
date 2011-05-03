class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/kindeditor/upload"(controller:"kindeditor", action:"upload")
		"/kindeditor/fileManager"(controller:"kindeditor", action:"fileManager")
		
		"/"(controller:"kindeditor")
		
		"500"(view:'/error')
	}
}
