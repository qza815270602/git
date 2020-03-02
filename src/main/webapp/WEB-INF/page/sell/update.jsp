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
    <script src="<%=request.getContextPath()%>/static/validate/jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script>


        //修改
       function update() {
            var formData = new FormData($('#fm')[0]);
            var index = layer.load(1,{shade:0.3});
            $.ajax({
                url:'<%= request.getContextPath() %>/sell/updateImg',
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
    <style>
        .error{
            color: red;
        }
    </style>
</head>
<body>
<form id="fm">
    名称<input type="text" name="sellName" value="${sell.sellName}">  <br>
    玩具示例图<img src='${sell.img}' width='100px' height='100px'> <br>
    更换图片<input type="file" name="file" id="file"/><br>
    <input type="hidden" name="img" value="${sell.img}">
    价格<input type="text" name="sellPrice" value="${sell.sellPrice}"  onkeyup="if(!this.value.match(/^\+?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:\+?\d+(?:\.\d+)?)?$/))this.o_value=this.value" maxlength="8" oninput = "value=value.replace(/[^\d.]/g,'')" width="100px" />
<br>颜色名称<input type="text" name="colour" value="${sell.colour}">  <br>
    维修项目
    <c:forEach items="${basicDataList}" var="b">
        <input type="radio" name="maintainProject" value="${b.id}" <c:if test="${sell.maintainProject == b.id}">checked</c:if> />${b.baseName}
    </c:forEach><br/>
        <input type="hidden" name="id" value="${sell.id}"><br>
    <input type="button" value="修改" onclick="update()"/>
</form>
</body>
</html>
