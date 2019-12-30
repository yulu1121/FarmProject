package com.anshi.farmproject.entry;

import java.io.Serializable;
import java.util.List;

public class DealTypeEntry implements Serializable{

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":"","createTime":"2019-11-12 16:40:36","updateBy":"","updateTime":"2019-11-12 16:40:38","remark":"2","params":{},"cureId":1,"cureName":"盛世嫡妃","status":"0","delFlag":"0"}]
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

    public static class DataBean implements Serializable {
        /**
         * searchValue : null
         * createBy :
         * createTime : 2019-11-12 16:40:36
         * updateBy :
         * updateTime : 2019-11-12 16:40:38
         * remark : 2
         * params : {}
         * cureId : 1
         * cureName : 盛世嫡妃
         * status : 0
         * delFlag : 0
         */

        private Object searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        private ParamsBean params;
        private int cureId;
        private String cureName;
        private String status;
        private String delFlag;

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

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public int getCureId() {
            return cureId;
        }

        public void setCureId(int cureId) {
            this.cureId = cureId;
        }

        public String getCureName() {
            return cureName;
        }

        public void setCureName(String cureName) {
            this.cureName = cureName;
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

        public static class ParamsBean implements Serializable {
        }
    }
}
