function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#name").val(),
        cusNo:$("#khno").val(),
        level:$("#level").combobox("getValue"),
        myd:$("#myd").combobox("getValue")
    })
}


function openCustomerAddDialog() {
    openDialog("dlg","客户添加")
}

function closeCustomerDialog() {
    closeDialog("dlg")
}


function clearFormData() {

}

function saveOrUpdateCustomer() {
    saveOrUpdateRecode(ctx+"/customer/save",ctx+"/customer/update","fm","dlg",searchCustomersByParams,clearFormData)
}



function deleteCustomer() {
    deleteRecode("dg",ctx+"/customer/delete",searchCustomersByParams);
}


function openCustomerModifyDialog() {
    openModifyDialog("dg","fm","dlg","客户修改")
}