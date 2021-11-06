<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>
        //ajax模板
        $.ajax({
            url : "",
            data : {

            },
            type : "",
            dataType : "",
            success : function (data) {

            }
        })
</body>
</html>
