<%--
  Created by IntelliJ IDEA.
  User: qinbb
  Date: 2020/1/29
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Titl</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/My97DatePicker/WdatePicker.js"></script>
    <script>

<%--            //注册--%>
<%--        function add() {--%>
<%--                    var index = layer.load(1, {shade: 0.3}, {shadeClose: true}); //解决网络延迟的加载重复操作问题--%>
<%--                    layer.msg('请稍等', {--%>
<%--                        icon: 1,--%>
<%--                        time: 2000, //2秒关闭（如果不配置，默认是3秒）--%>
<%--                        shade: [0.8, '#393D49']--%>
<%--                    }, function () {--%>
<%--                        $.post("<%=request.getContextPath()%>/sell/add ",--%>
<%--                            $("#fm").serialize(),--%>
<%--                            function (data) {--%>
<%--                                layer.close(index);--%>
<%--                                if (data.code == -1) {--%>
<%--                                    layer.msg(data.msg, {icon: 5});--%>
<%--                                    return;--%>
<%--                                }--%>
<%--                                parent.window.location.href = "<%=request.getContextPath()%>/sell/toShow";--%>
<%--                            });--%>
<%--                    });--%>

<%--                }--%>


            function add() {
                var file = $('#file')[0].files[0];
                var formData = new FormData();
                formData.append("file", $("#file").val());
                formData.append("sellName", $("#sellName").val());
                formData.append("sellPrice", $("#sellPrice").val());
                formData.append("colour", $("#colour").val());
                formData.append("maintainProject", $("#maintainProject").val());
                formData.append("isDel", $("#isDel").val());
                var index = layer.load(1,{shade:0.3});
                $.ajax({
                    url:'<%= request.getContextPath() %>/sell/updateImg',
                    dataType:'json',
                    type:'POST',
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    success: function(data){
                        if (data.code != 200) {
                            layer.msg(data.msg);
                            layer.close(index);
                            return;
                        }
                        layer.msg(data.msg,function () {
                            layer.close(index);
                            layer.msg(data.msg);
                            parent.window.location.href = "<%=request.getContextPath()%>/sell/toShow";
                        });
                    }
                });

            }

    </script>

</head>
<body>
<form id="fm">
    名称<input type="text" name="sellName" id="sellName"><br>
    玩具图片示例
    <input type="file" name="file" accept="image/*" id="file"><br>
<%--    <input type="button" value="上传" onclick="updateImg()"><br>--%>
    价格<input type="text" name="sellPrice" id="sellPrice"/>
    <br>
    颜色<input type="text" name="colour" id="colour"><br>
    维修项目
                <select name="maintainProject" id="maintainProject">
                    <c:forEach var="b" items="${basicDataList}">
                         <option value="${b.id}">${b.baseName}</option>
                    </c:forEach><br>
                </select>
        <input type="hidden" name="isDel" id="isDel" value="1"><br>
    <input type="button" value="注册" onclick="add()"/>
</form>
</body>
</html>
