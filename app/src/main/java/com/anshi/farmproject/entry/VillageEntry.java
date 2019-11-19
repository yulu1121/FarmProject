package com.anshi.farmproject.entry;

import java.util.List;

public class VillageEntry {

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:52:24","updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":112,"parentId":101,"ancestors":"0,100,101","deptName":"赵集村","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":null,"childDepts":null},{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":105,"parentId":101,"ancestors":"0,100,101","deptName":"白集村","orderNum":"3","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":null,"childDepts":null}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * searchValue : null
         * createBy : admin
         * createTime : 2019-11-13 10:52:24
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * deptId : 112
         * parentId : 101
         * ancestors : 0,100,101
         * deptName : 赵集村
         * orderNum : 1
         * leader : null
         * phone : null
         * email : null
         * status : 0
         * delFlag : 0
         * parentName : null
         * amount : null
         * childDepts : null
         */

        private Object searchValue;
        private String createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private int deptId;
        private int parentId;
        private String ancestors;
        private String deptName;
        private String orderNum;
        private Object leader;
        private Object phone;
        private Object email;
        private String status;
        private String delFlag;
        private Object parentName;
        private Object amount;
        private Object childDepts;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public Object getLeader() {
            return leader;
        }

        public void setLeader(Object leader) {
            this.leader = leader;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public Object getChildDepts() {
            return childDepts;
        }

        public void setChildDepts(Object childDepts) {
            this.childDepts = childDepts;
        }

        public static class ParamsBean {
        }
    }
}
