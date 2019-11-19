package com.anshi.farmproject.entry;

import java.util.List;

public class ZhiWuEntry {

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":"","createTime":"2019-11-12 16:40:44","updateBy":"","updateTime":"2019-11-12 16:40:48","remark":"打工","params":{},"botanyId":1,"botanyName":"喇叭花","status":"0","delFlag":"0"}]
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
         * createBy :
         * createTime : 2019-11-12 16:40:44
         * updateBy :
         * updateTime : 2019-11-12 16:40:48
         * remark : 打工
         * params : {}
         * botanyId : 1
         * botanyName : 喇叭花
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
        private int botanyId;
        private String botanyName;
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

        public int getBotanyId() {
            return botanyId;
        }

        public void setBotanyId(int botanyId) {
            this.botanyId = botanyId;
        }

        public String getBotanyName() {
            return botanyName;
        }

        public void setBotanyName(String botanyName) {
            this.botanyName = botanyName;
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

        public static class ParamsBean {
        }
    }
}
