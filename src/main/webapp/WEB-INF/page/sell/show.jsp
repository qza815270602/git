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
                    html += "<input type = 'hidden' id = '"+list.id+"' value = '"+list.isDel+"'/>"
                    html += "</td>";
                    html += "<td>"+list.id+"</td>";
                    html += "<td>"+list.sellName+"</td>";
                    html += "<td>";
                    html += "<img src='"+list.img+"' width='100px' height='100px'>";
                    html += "</td>";
                    html += "<td>"+list.sellPrice+"</td>";
                    html += "<td>"+list.colour+"</td>";
                    html += "<td>"+list.projectShow+"</td>";
                    html += list.isDel == 1?"<td>上架</td>":"<td>下架</td>"
                    html += "<td>";
                    <shiro:hasPermission name="sell:gm">
                    html += list.isDel == 1?"<input type = 'button' value = '购买' onclick = 'addById("+list.id+")'>":"❤已下架❤";
                   </shiro:hasPermission>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
        })
    }

    //上/下架
    function updateStatus(){
        var len = $("input[name='id']:checked").length;
        if(len <= 0){
            layer.msg("请选择一项", {icon: 0});
            return;
        }
        if(len > 1){
            layer.msg("只能选择一项", {icon: 0});
            return;
        }
        //id
        var id = "";
        $("input[name='id']:checked").each(function (index, item) {
            if ($("input[name='id']:checked").length-1==index) {
                id += $(this).val();
            }
        });
        //上线时间
        var data = $("#"+id+"s").val();
        //状态 = # + 状态ID.value
        var sta = $("#"+id).val();
        //设置全局变量,往后台传值1/2
        var statu;
        var msg1 = "您确定要设置为";
        if(sta == 1){
            msg1 += "下架吗？";
            statu = 0;
        } else {
            msg1 += "上架吗？";
            statu = 1;
        }
        layer.confirm(msg1, {icon: 3, title:'提示'}, function(index){
            //do something
            $.post("<%=request.getContextPath()%>/sell/updateStatus",
                {"id":id,"isDel":statu},
                function(data){
                    if (data.code == -1){
                        layer.msg(data.msg, {icon: 5});
                        return;
                    }
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        show();
                    });
                });

            layer.close(index);
        });
    }



    //去修改
    function addById(id){
        $.post("<%=request.getContextPath()%>/sell/addById?id="+id,
            {},
            function(data){
                if (data.code == -1){
                    layer.msg(data.msg, {icon: 5});
                    return;
                }
                layer.msg("购买成功", {icon: 6});
                show();

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

    状态<select name="isDel">
            <option value="2">--请选择--</option>
            <option value="1">已上架</option>
            <option value="0">已下架</option>
        </select><br>
    <input type="button" value="搜索" onclick="show()">

        <shiro:hasPermission name="sell:add">
             <input type="button" value="新增玩具信息" onclick="add()">
        </shiro:hasPermission>
<shiro:hasPermission name="sell:del">
    <input type="button" value="上/下架" onclick="updateStatus()" />
</shiro:hasPermission>
        <shiro:hasPermission name="sell:update">
    <input type="button" value="修改" onclick="update()" />
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
            <th style="background: aquamarine;">状态</th>
            <shiro:hasPermission name="sell:gm">
                <th style="background: aquamarine;">操作</th>
           </shiro:hasPermission>
        </tr>
        </thead>
        <tbody id = "tbd">
        </tbody>
    </table>
</body>
</html>
