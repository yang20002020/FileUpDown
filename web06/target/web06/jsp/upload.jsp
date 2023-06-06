
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>文件上传</h1>
<!--文件上传三要素
     * 表单需要是post提交
     *表单中需要文件上传项，必须有name的属性和值
     *表单的enctype 属性必须是multipart/form-data
-->
<form action="${pageContext.request.contextPath}/UploadServlet" method="post" enctype="multipart/form-data">
    文件描述：<input type="text" name="info"><br/>
    文件上传：<input type="file" name="upload"><br/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>
