
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/static/layer/mobile/need/layer.css"  media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js" ></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.min.js" ></script>
</head>
<body align="center">

<div id="demotree" class="ztree"></div>
</body>
<script>

    var setting = {
        data: {
            simpleData: {
                enable: true,	//开启简单数据格式
                //上级Id
                pIdKey: "pid",
                idKey: "id",
                open:true
            },
            key : {
                //名称
                name : "resourceName",
                url: "xUrl"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) { // 匿名函数
                parent.right.location.href = "<%=request.getContextPath()%>" + treeNode.url;
            }
        },

    };

    $(document).ready(function(){
        $.post(
            "<%=request.getContextPath()%>/resource/left",
            {},
            function (data) {
                $.fn.zTree.init($("#demotree"),setting, data.data);
                var treeObj = $.fn.zTree.getZTreeObj("demotree");
                treeObj.expandAll(true);
            }
        )
    });
</script>
</html>