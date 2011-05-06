<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>
	<ke:resource></ke:resource>
	 
	
	<script type="text/javascript">
		KE.show({
			id : 'content1',
			imageUploadJson : '${request.contextPath}/kindeditor/upload',
			fileManagerJson : '${request.contextPath}/kindeditor/fileManager',
			allowFileManager : true,
			afterCreate : function(id) {
				KE.event.ctrl(document, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
				KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
			}
		});
	</script>
</head>
<body>
  <div class="body">
       <h2>Kind editor show</h2>
       <g:if test="${htmlData}">
          <hr/>
          <h3>编辑后内容如下：</h3>
          <div>
          	${htmlData}
          </div>
       </g:if>
       
	<g:form name="example" method="post" action="index">
		<textarea id="content1" name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${htmlData }</textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</g:form>
  </div>
</body>
</html>
