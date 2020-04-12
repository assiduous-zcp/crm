function searchUser() {
    var userName = $("#s_userName").val();
    var phone = $("#s_phone").val();
    var trueName = $("#s_trueName").val();

    $("#dg").datagrid("load",{
        userName:userName,
        phone:phone,
        trueName:trueName
    })

}

function openUserAddDialog() {
    /*$("#dlg").dialog("open").dialog("setTitle","用户添加");*/
    openDialog("dlg","用户添加");
}

function closeUserDialog() {
    /*$("#dlg").dialog("close");*/
    closeDialog("dlg");
}

function clearFormData() {
    $("#userName").val("");
    $("#trueName").val("");
    $("#email").val("");
    $("#phone").val("");
    $("input[name='id']").val("");
}


function saveOrUpdateUser() {
    /*var url=ctx+"/user/save";
    if(!isEmpty($("input[name='id']").val())){
        url=ctx+"/user/update";
    }

    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if (data.code==200){
                closeUserDialog();
                searchUser();
                clearFormData();
            }else{
                $.messager.alert("来自crm",data.msg,"error")
            }
        }
    })*/

    saveOrUpdateRecode(ctx+"/user/save",ctx+"/user/update","fm","dlg",searchUser,clearFormData);

}

function openUserModifyDialog() {
    /*var rows=$("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自crm","请选择待修改的用户数据！","error");
        return;
    }else if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改！","error");
        return;
    }

    $("#fm").form("load",rows[0]);

    $("#dlg").dialog("open").dialog("setTitle","用户更新");*/
    var rows=$("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自crm","请选择待修改的数据！","error");
        return;
    }else if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改！","error");
        return;
    }

    rows[0].roleIds=rows[0].rids.split(",");

    $("#fm").form("load",rows[0]);

    openDialog("dlg","用户更新");
}




function deleteUser() {
    /*var rows=$("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自crm","请选择待删除的用户数据！","error");
        return;
    }


    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量删除！","error");
        return;
    }

    $.messager.confirm("来自crm","确定删除选择的记录？",function (r) {
        if (r){

            $.ajax({
                type:"post",
                url:ctx+"/user/delete",
                data:{
                    userId:rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        searchUser();
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })*/
    deleteRecode("dg",ctx+"/user/delete",searchUser);
}

