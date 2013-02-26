<%@ page contentType="text/html;charset=UTF-8" %>
	
	<script type="text/javascript">
		KE.show({
			id: "${keConfig.id}",
			imageUploadJson : "${keConfig.uploadPath}",
			fileManagerJson : "${keConfig.fileManagerPath}",
			allowFileManager : true,
			afterCreate : function(id) {
				KE.event.ctrl(document, 13, function() {
					KE.util.setData(id);
					document.forms["${keConfig.formId}"].submit();
				});
				KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
					KE.util.setData(id);
					document.forms["${keConfig.formId}"].submit();
				});
			}
		});

	</script>