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
                    html += "<td>"+list.userName+"</td>";
                    html += "<td>"+list.sellName+"</td>";
                    html += "<td>";
                    html += "<img src='"+list.img+"' width='100px' height='100px'>";
                    html += "</td>";
                    html += "<td>"+list.sellPrice+"</td>";
                    html += "<td>"+list.colour+"</td>";
                    html += "<td>"+list.projectShow+"</td>";
                    html += list.isDel == 1?"<td>上架</td>":"<td>下架</td>";
                    html += "<td>"+list.count+"</td>";
                    html += "<td>"+((list.count*list.sellPrice).toFixed(2))+"</td>"
                    html += "<td>";
                    <shiro:hasPermission name="sell:th">
                    html += "<button type='button' class='layui-btn layui-btn-xs layui-btn-normal' onclick='updateById("+list.id+","+list.count+","+list.repertory+")'>退货</button>";
                   </shiro:hasPermission>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
        })
    }



    //去修改
    function updateById(sellId, count, repertory){
        $.post("<%=request.getContextPath()%>/sell/updateBySellId",
            {"sellId":sellId, "count":count, "repertory":repertory},
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
            <th style="background: aquamarine;">用户名</th>
            <th style="background: aquamarine;">名称</th>
            <th style="background: aquamarine;">玩具示例</th>
            <th style="background: aquamarine;">单价</th>
            <th style="background: aquamarine;">颜色</th>
            <th style="background: aquamarine;">玩具类型</th>
            <th style="background: aquamarine;">状态</th>
            <th style="background: aquamarine;">数量</th>
            <th style="background: aquamarine;">总价</th>
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
