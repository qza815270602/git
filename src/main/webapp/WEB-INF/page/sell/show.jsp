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
        $.post("<%=request.getContextPath()%>/sell/show",
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
                    html += "<td>";
                    html += "<input type = 'checkbox' name = 'id' value = '"+list.id+"'>";
                    html += "</td>";
                    html += "<td>"+list.id+"</td>";
                    html += "<td>"+list.sellName+"</td>";
                    html += "<td>"+list.img+"</td>";
                    html += "<td>"+list.sellPrice+"</td>";
                    html += "<td>"+list.colour+"</td>";
                    html += "<td>"+list.projectShow+"</td>";
                    html += "<td>";
<%--                    <shiro:hasPermission name="maintain:cz">--%>
                    html += "<input type = 'button' value = '审核' onclick = 'updateById("+list.id+")'>";
<%--                    </shiro:hasPermission>--%>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
        })
    }



    //去修改
    function updateById(id){
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/maintain/toUpdate/'+id
            });

    }




    //删除
    function del(){
        var length = $("input[name='id']:checked").length;

        if(length <= 0){
            layer.msg("至少选择一项", {icon: 5});
            return;
        }
        if(length > 1){
            layer.msg("只能选择一个", {icon: 5});
            return;
        }

        var id = $("input[name='id']:checked").val();
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
            //do something

            layer.close(index);

            $.post("<%=request.getContextPath()%>/maintain/del ",
                {"id":id, "isDel":0},
                function(data){
                    layer.close(index);
                    if (data.code == -1){
                        layer.msg(data.msg, {icon: 5});
                        return;
                    }
                    window.location.href="<%=request.getContextPath()%>/maintain/toShow";
                });
        });
    }



    //去修改
    function add(){
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/sell/toAdd'
            });
    }

</script>
<body>
<form id="fm">
    <div align="center">
<shiro:hasPermission name="sell:find">
    状态<select name="status">
            <option value="0">--请选择--</option>
            <option value="2">已预约</option>
            <option value="3">已审核</option>
            <option value="4">维修完成</option>
        </select><br>
    <input type="button" value="搜索" onclick="show()">
</shiro:hasPermission>
        <shiro:hasPermission name="sell:add">
             <input type="button" value="新增玩具信息" onclick="add()">
        </shiro:hasPermission>
<shiro:hasPermission name="sell:del">
        <input type="button" value="删除" onclick="del()">
</shiro:hasPermission>
    </div>
</form>
    <table  class="layui-table">
        <colgroup>
            <col width="100">
            <col width="100">
            <col width="100">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th style="background: aquamarine;"></th>
            <th style="background: aquamarine;">编号</th>
            <th style="background: aquamarine;">名称</th>
            <th style="background: aquamarine;">玩具示例</th>
            <th style="background: aquamarine;">价格</th>
            <th style="background: aquamarine;">颜色</th>
            <th style="background: aquamarine;">玩具类型</th>
<%--            <shiro:hasPermission name="sell:cz">--%>
                <th style="background: aquamarine;">操作</th>
<%--            </shiro:hasPermission>--%>
        </tr>
        </thead>
        <tbody id = "tbd">
        </tbody>
    </table>
</body>
</html>
