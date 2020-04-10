package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SuppressWarnings("all")
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 参数校验
         *      saleChanceId    营销机会id 非空判断 记录必须存在
         *      planItem        计划项内容   非空判断
         *      planDate        计划项时间   非空判断
         * 参数默认值设置
         *      is_valid    createDate  updateDate
         *
         * 执行添加 判断结果
         */


        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());

        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());

        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项记录添加失败！");

    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 参数校验
         *      id  非空  记录必须存在
         *      saleChanceId    营销机会id 非空判断 记录必须存在
         *      planItem        计划项内容   非空判断
         *      planDate        计划项时间   非空判断
         * 参数默认值设置
         *      pdateDate
         *
         * 执行更新 判断结果
         */

        AssertUtil.isTrue(null==cusDevPlan.getId() || null==selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在！");
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setUpdateDate(new Date());

        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录更新失败！");
    }



    public void deleteCusDevPlan(Integer id){
        CusDevPlan cusDevPlan=selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id || null==cusDevPlan,"待删除记录不存在！");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录删除失败！");
    }

    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(null==saleChanceId || null==saleChanceMapper.selectByPrimaryKey(saleChanceId),"请设置营销机会id！");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容！");
        AssertUtil.isTrue(null==planDate,"请指定计划项日期！");

    }

}
