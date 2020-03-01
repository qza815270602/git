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
        $.post("<%=request.getContextPath()%>/maintain/show",
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
                    html += "<td>"+list.maintainTime+"</td>";
                    html += "<td>"+list.maintainId+"</td>";
                    html += "<td>"+list.projectShow+"</td>";
                    html += "<td>"+list.statusShow+"</td>";
                    html += "<td>";
                    <shiro:hasPermission name="maintain:cz">
                    html += "<input type = 'button' value = '审核' onclick = 'updateById("+list.id+")'>";
                    </shiro:hasPermission>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
        })
    }



    //去修改
    function updateById(){
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
        layer.confirm('确定修改吗?', {icon: 3, title:'提示'}, function(index){
            //do something

            layer.close(index);

            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/maintain/toUpdate/'+id
            });
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
                content: '<%=request.getContextPath()%>/maintain/toAdd'
            });
    }

</script>
<body>
<form id="fm">
    <div align="center">
<shiro:hasPermission name="maintain:find">
    状态<select name="status">
            <option value="0">--请选择--</option>
            <option value="2">已预约</option>
            <option value="3">已审核</option>
            <option value="4">维修完成</option>
        </select><br>
    <input type="button" value="搜索" onclick="show()">
</shiro:hasPermission>
        <input type="button" value="填写维修单" onclick="add()">
    </div>
</form>
<shiro:hasPermission name="maintain:update">
    <input type="button" value="修改" onclick="updateById()">&nbsp;
</shiro:hasPermission>
<shiro:hasPermission name="maintain:del">
    <input type="button" value="删除" onclick = 'delByIds()'>&nbsp;
</shiro:hasPermission>
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
            <th style="background: aquamarine;">预约编号</th>
            <th style="background: aquamarine;">预约时间</th>
            <th style="background: aquamarine;">预约单号</th>
            <th style="background: aquamarine;">维修项目</th>
            <th style="background: aquamarine;">状态</th>
            <shiro:hasPermission name="maintain:cz">
                <th style="background: aquamarine;">操作</th>
            </shiro:hasPermission>
        </tr>
        </thead>
        <tbody id = "tbd">
        </tbody>
    </table>
</body>
</html>