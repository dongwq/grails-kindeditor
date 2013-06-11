<%@ page contentType="text/html;charset=UTF-8" %>

<r:script>
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
        },
        items : [
                    'undo','redo','cut','copy','paste','|','fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist','indent', 'outdent', '|','advtable', 'hr', 'emoticons', 'image', 'link']
    });

</r:script>