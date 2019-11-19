package com.anshi.farmproject.entry;

public class ImageEntry {

    /**
     * msg : 操作成功
     * code : 0
     * data : {"savePath":"/profile/img/20191119/20191119112039.jpg"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * savePath : /profile/img/20191119/20191119112039.jpg
         */

        private String savePath;

        public String getSavePath() {
            return savePath;
        }

        public void setSavePath(String savePath) {
            this.savePath = savePath;
        }
    }
}
