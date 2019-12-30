package com.anshi.farmproject.entry;

import java.util.List;

public class CureTimeEntry {

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"branchId":423,"amounts":8,"cureTime":"2019-12-01"},{"branchId":423,"amounts":47,"cureTime":"2019-12-02"},{"branchId":423,"amounts":66,"cureTime":"2019-12-03"},{"branchId":423,"amounts":81,"cureTime":"2019-12-04"},{"branchId":423,"amounts":75,"cureTime":"2019-12-05"},{"branchId":423,"amounts":69,"cureTime":"2019-12-07"},{"branchId":423,"amounts":73,"cureTime":"2019-12-08"},{"branchId":423,"amounts":82,"cureTime":"2019-12-09"},{"branchId":423,"amounts":76,"cureTime":"2019-12-10"},{"branchId":423,"amounts":50,"cureTime":"2019-12-11"},{"branchId":423,"amounts":70,"cureTime":"2019-12-12"},{"branchId":423,"amounts":70,"cureTime":"2019-12-13"},{"branchId":423,"amounts":71,"cureTime":"2019-12-14"},{"branchId":423,"amounts":73,"cureTime":"2019-12-15"},{"branchId":423,"amounts":82,"cureTime":"2019-12-16"}]
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
         * branchId : 423
         * amounts : 8
         * cureTime : 2019-12-01
         */

        private int branchId;
        private int amounts;
        private String cureTime;

        public int getBranchId() {
            return branchId;
        }

        public void setBranchId(int branchId) {
            this.branchId = branchId;
        }

        public int getAmounts() {
            return amounts;
        }

        public void setAmounts(int amounts) {
            this.amounts = amounts;
        }

        public String getCureTime() {
            return cureTime;
        }

        public void setCureTime(String cureTime) {
            this.cureTime = cureTime;
        }
    }
}
