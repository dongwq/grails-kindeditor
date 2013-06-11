<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>

	%{--<ke:resource/>--}%

    <r:require module="kindeditor"/>


    <r:layoutResources/>

	<ke:show id="content1" type="all" formId="form1"/>
	<ke:show id="content2" type="simple" formId="form2"/>
	<ke:show id="content3" type="tiny" formId="form3"/>


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

      <h2>Kind editor type all</h2>
	<g:form name="form1" method="post" action="index">
		<textarea id="content1" name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${htmlData }</textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</g:form>
	
	<hr/>
      <h2>Kind editor type simple</h2>
	<g:form name="form2" method="post" action="index">
		<textarea id="content2" name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${htmlData }</textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</g:form>


      <hr/>
      <h2>Kind editor type tiny</h2>
      <g:form name="form3" method="post" action="index">
          <textarea id="content3" name="content3" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${htmlData }</textarea>
          <br />
          <input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
      </g:form>
  </div>




<r:layoutResources/>
</body>
</html>
