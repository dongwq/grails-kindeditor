package com.itgongfu

import grails.web.JSONBuilder

import java.text.SimpleDateFormat

import org.springframework.web.multipart.MultipartFile


class KindeditorController {

	def keDemo(){
		return [htmlData:params.content1]
	}

	def upload(){
		
		def name = "imgFile"   /*由kindeditor内部使用这个名称*/
		//
		Long maxSize = 1000000
		def types = [
			'gif',
			'jpg',
			'jpeg',
			'png',
			'bmp'
		]
		def uploadDir =  "/attached"

		def isAbsolute = false;
		def absolutePath = /D:\workspace\kindeditor\web-app/
		def staticResoureUrl = ""
		
		def pathPrefix = isAbsolute?absolutePath:servletContext.getRealPath("/")

		MultipartFile file = request.getFile(name)

		if( !file )
		{
			render text:outError("file name cant get")
			return
		}
		if( file.isEmpty() ) {
			render text:outError("请选择文件")
			return
		}

		//	render text:outError("this is ok")
		
		def uploadPath =  pathPrefix + uploadDir
		File dir = new File(uploadPath)

		println "path is " + dir.path

		if( !dir.exists())
		{
			render text:outError("upload dir not exist!")
			return
		}

		if( !dir.canWrite()) {
			render text:outError("上传目录没有写权限。")
			return
		}

		def todayDir = String.format("/%tY%<tm%<td", new Date())

		def savePath = uploadPath + todayDir
		def saveUrl = request.contextPath + uploadDir + todayDir
		println "savePath = $savePath"

		def saveDir = new File(savePath)
		if( !saveDir.exists())
			saveDir.mkdir();

		if(file.size > maxSize)
		{
			render text:outError("上传文件大小超过限制。")
			return
		}
		def filenameExt = getFilenameExtention(file.originalFilename)
		if(! types.contains ( filenameExt ) )
		{
			render text:outError("上传文件扩展名是不允许的扩展名。")
			return
		}
		
		def newFilename = String.format("/%tY%<tm%<td%<tH%<tM%<tS", new Date()) +"_" + (new Random().nextInt(1000)) + "." + filenameExt
		
		saveUrl += newFilename
		
		try{
			file.transferTo(new File(savePath + newFilename ))
			render text:outSuccess(saveUrl)
			return
		}catch(IOException){
			render text:outError("上传文件写入失败")
			return
		}
		println saveUrl
		render text:outError("last is ok")
	}

	def getFilenameExtention(String filename)
	{
		return filename.substring( filename.lastIndexOf(".") + 1).toLowerCase()
	}
	
	def outError(def msg){
		return """{"error":1, "message":"$msg"}"""
	}

	def outSuccess(def path){
		return """{"error":0, "url":"$path"}"""
	}
	
	def fileManager(){
		
		def rootPath = servletContext.getRealPath("/") + "attached/"
		def rootUrl = request.contextPath + "/attached/"
		
		def types = [
			'gif',
			'jpg',
			'jpeg',
			'png',
			'bmp'
		]
		
		def path = params.path?:""
		
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		
		if(path)
		{
			def str = currentDirPath.substring(0, currentDirPath.length()-1)
			moveupDirPath = str.lastIndexOf("/") >=0 ? str.substring(0, str.lastIndexOf("/")+1 ):""
		}
		
		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";
		
		def out = response.outputStream
		
		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			out.println("Access is not allowed.");
			return;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			out.println("Parameter is not valid.");
			return;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			out.println("Directory does not exist.");
			return;
		}
		
		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", types.contains(fileExt) );
					//hash.put("is_photo", Arrays.<String>asList(types).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		
		def builder = new JSONBuilder()
		def result = builder.build {
			moveup_dir_path = moveupDirPath
			current_dir_path = currentDirPath
			current_url = currentUrl
			total_count = fileList.size()
			file_list = fileList
		}// prints the JSON text
		
		render text:result.toString()
		
		//这样直接输出的格式不正确，深层对象外面包了一层""
		/*
		render(contentType:"application/json"){
			"moveup_dir_path" moveupDirPath
			"current_dir_path" currentDirPath
			"current_url" currentUrl
			"total_count" fileList.size()
			"file_list" fileList
		}
		*/
	}
}

public class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
		}
	}
}

public class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
				return 1;
			} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
public class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
		}
	}
}
