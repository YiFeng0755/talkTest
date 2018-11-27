package com.youme.talktest;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author LiuYang
 */
public class ExcelPropertyIndexModel extends BaseRowModel {

    @ExcelProperty(value = "测试项" ,index = 0)
    private String testItem;

    @ExcelProperty(value = "测试项",index = 1)
    private String testItem1;

    @ExcelProperty(value = "测试点",index = 2)
    private String testPoint;

    @ExcelProperty(value = "测试步骤",index = 3)
    private String testStep;

    @ExcelProperty(value = "期望结果",index = 4)
    private String expectResult;

    @ExcelProperty(value = "测试结果(opensls=1)",index = 5)
    private String result;

    @ExcelProperty(value = "备注",index = 6)
    private String remark;

    public String getTestItem() {
        return testItem;
    }

    public void setTestItem(String testItem) {
        this.testItem = testItem;
    }

    public String getTestItem1() {
        return testItem1;
    }

    public void setTestItem1(String testItem1) {
        this.testItem1 = testItem1;
    }

    public String getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(String testPoint) {
        this.testPoint = testPoint;
    }

    public String getTestStep() {
        return testStep;
    }

    public void setTestStep(String testStep) {
        this.testStep = testStep;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
