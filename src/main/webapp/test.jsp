<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
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


        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        //日历组件
        $(".time").datetimepicker({
        minView: "month",
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
        });
</body>
</html>
