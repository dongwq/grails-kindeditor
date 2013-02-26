package com.dongwq

import grails.util.Environment;

class KindeditorTagLib {
	static namespace = "ke"
	
	static writeAttrs( attrs, output) {
		// Output any remaining user-specified attributes
		attrs.each { k, v ->
			output << k
			output << '="'
			output << v.encodeAsHTML()
			output << '" '
		}
	}

	static LINK_WRITERS = [
		js: { url, constants, attrs ->
			def o = new StringBuilder()
			o << "<script src=\"${url.encodeAsHTML()}\" "

			// Output info from the mappings
			writeAttrs(constants, o)
			writeAttrs(attrs, o)

			o << '></script>'
			return o
		},

		link: { url, constants, attrs ->
			def o = new StringBuilder()
			o << "<link href=\"${url.encodeAsHTML()}\" "

			// Output info from the mappings
			writeAttrs(constants, o)
			writeAttrs(attrs, o)

			o << '/>'
			return o
		}
	]
	
	def resource = {
		
		def jsFile = "kindeditor.js";
		if( Environment.current == Environment.PRODUCTION )
		{
			jsFile = "kindeditor-min.js"
		}
		
		def jsPath = g.resource(plugin:"kindeditor", dir:'ke-zh', file:jsFile)
		
		out <<"""<script type="text/javascript" src="${jsPath}" /></script>"""
	}

	def types = ["tiny", 'simple', 'all']
		
	def show = { attrs, body->
		
		def type = attrs.remove("type")
		//if type is not a exist one.
		if( !types.contains(type)) type = 'simple'
		
		def id = attrs.remove("id")
		def formId = attrs.remove("formId")
		
		def upload = "${request.contextPath}/kindeditor/upload"
		def fileManager = "${request.contextPath}/kindeditor/fileManager"
		
		def keConfig = new KEConfig(id:id, type:type,formId:formId, uploadPath:upload, fileManagerPath:fileManager);
		
		out<< render(template:type,model:[keConfig:keConfig] )
	}
	
	def doShow(def out){
		
		
	}
}
