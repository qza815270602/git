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

            function add() {
                var formData = new FormData($('#fm')[0]);
                var index = layer.load(1,{shade:0.3});
                $.ajax({
                    url:'<%= request.getContextPath() %>/sell/addImg',
                    dataType:'json',
                    type:'POST',
                    data: formData,
                    cache:false,
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
