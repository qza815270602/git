<%--
  Created by IntelliJ IDEA.
  User: qinbb
  Date: 2020/1/28
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<html>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.js" charset="utf-8"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<head>
    <title>展示</title>
</head>
<script>
    $(function(){
        show();
    })

    function show(){
        $.post("<%=request.getContextPath()%>/sell/showSellUser",
            $("#fm").serialize(),
            function (data) {
                if (data.code != 200){
                    layer.msg(data.data.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < data.data.length; i++) {
                    var list = data.data[i];
                    html += "<tr>";
                    html += "<td>"+list.sellName+"</td>";
                    html += "<td>";
                    html += "<img src='http://q3ty2qnj5.bkt.clouddn.com/"+list.img+"' width='100px' height='100px'>";
                    html += "</td>";
                    html += "<td>"+list.sellPrice+"</td>";
                    html += "<td>"+list.colour+"</td>";
                    html += "<td>"+list.projectShow+"</td>";
                    html += list.isDel == 1?"<td>上架</td>":"<td>下架</td>"
                    html += "<td>";
                    <shiro:hasPermission name="sell:th">
                    html += list.isDel == 1?"<input type = 'button' value = '退货' onclick = 'updateById("+list.id+")'>":"❤已下架❤";
                   </shiro:hasPermission>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
        })
    }



    //去修改
    function updateById(id){
        $.post("<%=request.getContextPath()%>/sell/updateById?id="+id,
            {},
            function(data){
                if (data.code == -1){
                    layer.msg(data.msg, {icon: 5});
                    return;
                }
                layer.msg("退货成功", {icon: 6});
                show();

            });

    }


</script>
<body>

    <table  class="layui-table">
        <colgroup>
            <col width="100">
            <col width="100">
            <col width="100">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th style="background: aquamarine;">名称</th>
            <th style="background: aquamarine;">玩具示例</th>
            <th style="background: aquamarine;">价格</th>
            <th style="background: aquamarine;">颜色</th>
            <th style="background: aquamarine;">玩具类型</th>
            <th style="background: aquamarine;">状态</th>
            <shiro:hasPermission name="sell:th">
                <th style="background: aquamarine;">操作</th>
           </shiro:hasPermission>
        </tr>
        </thead>
        <tbody id = "tbd">
        </tbody>
    </table>
</body>
</html>
