<%@ page contentType="text/html;charset=UTF-8" %>
	
	<script type="text/javascript">
		KE.show({
			id : "${ke.id}",
			imageUploadJson : "${ke.upload}"
			fileManagerJson : "${ke.fileManager}",
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