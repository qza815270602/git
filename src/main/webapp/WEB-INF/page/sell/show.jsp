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
    var totalNum = 0;
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
                totalNum = data.data.totalNum;
                var html = "";
                for (var i = 0; i < data.data.sellList.length; i++) {
                    var list = data.data.sellList[i];
                    $("#repertory").val(list.repertory);
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
                    html += list.isDel == 1?"<td>上架</td>":"<td>下架</td>";
                    html += list.repertory == 0?"<td>已售完😱</td>":"<td>"+list.repertory+"</td>";
                    html += "<td>";
                        <shiro:hasPermission name="sell:gm">
                        html += list.isDel == 1?"<button type='button' class='layui-btn layui-btn-xs layui-btn-normal' onclick='addById("+list.id+","+list.repertory+")'>购买</button>":"❤已下架❤";
                        </shiro:hasPermission>
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
                var pageNo = $("#pageNo").val();
                var pageHtml = "<button type='button' class='layui-btn layui-btn-xs layui-btn-normal' onclick='page("+(parseInt(pageNo) - 1)+")'>上一页</button>";
                pageHtml += "<button type='button' class='layui-btn layui-btn-xs layui-btn-normal' onclick='page("+(parseInt(pageNo) + 1)+")')'>下一页</button>";
                $("#pageInfo").html(pageHtml);
        })
    }

    function page(page) {

        if (page < 1) {
            layer.msg('首页啦!', {icon:0});
            return;
        }
        if (page > totalNum) {
            layer.msg('已经到尾页啦!!', {icon:0});
            return;
        }
        $("#pageNo").val(page);
        show();

    }
    function find(){
        $("#pageNo").val(1);
        show();
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
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: '<%=request.getContextPath()%>/sell/toUpdate/'+id
        });

    }




    function addById(id, repertory){
        $.post("<%=request.getContextPath()%>/sell/addById?id="+id,
            {"repertory":repertory},
            function(data){
                if (data.code == -1){
                    layer.msg(data.msg, {icon: 5});
                    return;
                }
                layer.msg("购买成功", {icon: 6});
                show();

            });

    }


    function add(){
            layer.open({
                type: 2,
                title: '页面',
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
        <input type="hidden" value="1" id="pageNo" name="pageNo">
    状态<select name="isDel">
            <option value="2">--请选择--</option>
            <option value="1">已上架</option>
            <option value="0">已下架</option>
        </select><br>
        名称<input type="text" name="sellName">
        <button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="find()">搜索</button><br>

        <shiro:hasPermission name="sell:add">
            <button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="add()">新增玩具信息</button>

        </shiro:hasPermission>
<shiro:hasPermission name="sell:del">
    <button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="updateStatus()">上/下架</button>
</shiro:hasPermission>
        <shiro:hasPermission name="sell:update">
            <button type="button" class="layui-btn layui-btn-xs layui-btn-normal" onclick="updateById()">修改</button>
</shiro:hasPermission>
    </div>
</form>
<input type="hidden" id="repertory" name="repertory">
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
            <th style="background: aquamarine;">库存</th>
            <shiro:hasPermission name="sell:gm">
                <th style="background: aquamarine;">操作</th>
           </shiro:hasPermission>
        </tr>
        </thead>
        <tbody id = "tbd">
        </tbody>
    </table>
<div id="pageInfo" align="center">

</div>
</body>
</html>
