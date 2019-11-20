package com.anshi.farmproject.entry;

import java.util.List;

public class TreeCountEntry {


    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":100,"parentId":0,"ancestors":"0","deptName":"林水局","orderNum":"0","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":null,"parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":101,"parentId":100,"ancestors":"0,100","deptName":"龙王镇","orderNum":"1","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:51:00","remark":null,"params":{},"deptId":105,"parentId":101,"ancestors":"0,100,101","deptName":"白集村","orderNum":"3","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:16","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":116,"parentId":105,"ancestors":"0,100,101,105","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":null,"parentId":null,"ancestors":null,"deptName":null,"orderNum":null,"leader":null,"phone":null,"email":null,"status":null,"delFlag":null,"parentName":null,"amount":null,"childDepts":null,"amounts":6,"branchId":2,"chainsaw":"若依"}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:52:24","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":112,"parentId":101,"ancestors":"0,100,101","deptName":"赵集村","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:53:47","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":113,"parentId":112,"ancestors":"0,100,101,112","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:28","remark":null,"params":{},"deptId":102,"parentId":100,"ancestors":"0,100","deptName":"黄龙镇","orderNum":"2","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:19","remark":null,"params":{},"deptId":108,"parentId":102,"ancestors":"0,100,102","deptName":"天云村","orderNum":"1","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:45","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":119,"parentId":108,"ancestors":"0,100,102,108","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:28","remark":null,"params":{},"deptId":109,"parentId":102,"ancestors":"0,100,102","deptName":"休闲村","orderNum":"2","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:52:08","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":122,"parentId":109,"ancestors":"0,100,102,109","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}]
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
         * createBy : null
         * createTime : null
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * deptId : 100
         * parentId : 0
         * ancestors : 0
         * deptName : 林水局
         * orderNum : 0
         * leader : 若依
         * phone : 15888888888
         * email : ry@qq.com
         * status : 0
         * delFlag : null
         * parentName : null
         * amount : 6
         * childDepts : [{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":101,"parentId":100,"ancestors":"0,100","deptName":"龙王镇","orderNum":"1","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:51:00","remark":null,"params":{},"deptId":105,"parentId":101,"ancestors":"0,100,101","deptName":"白集村","orderNum":"3","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:16","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":116,"parentId":105,"ancestors":"0,100,101,105","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":null,"parentId":null,"ancestors":null,"deptName":null,"orderNum":null,"leader":null,"phone":null,"email":null,"status":null,"delFlag":null,"parentName":null,"amount":null,"childDepts":null,"amounts":6,"branchId":2,"chainsaw":"若依"}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:52:24","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":112,"parentId":101,"ancestors":"0,100,101","deptName":"赵集村","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:53:47","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":113,"parentId":112,"ancestors":"0,100,101,112","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:28","remark":null,"params":{},"deptId":102,"parentId":100,"ancestors":"0,100","deptName":"黄龙镇","orderNum":"2","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:19","remark":null,"params":{},"deptId":108,"parentId":102,"ancestors":"0,100,102","deptName":"天云村","orderNum":"1","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:45","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":119,"parentId":108,"ancestors":"0,100,102,108","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:50:28","remark":null,"params":{},"deptId":109,"parentId":102,"ancestors":"0,100,102","deptName":"休闲村","orderNum":"2","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:52:08","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":122,"parentId":109,"ancestors":"0,100,102,109","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}]
         * amounts : null
         * branchId : null
         * chainsaw : null
         */

        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private int deptId;
        private int parentId;
        private String ancestors;
        private String deptName;
        private String orderNum;
        private String leader;
        private String phone;
        private String email;
        private String status;
        private Object delFlag;
        private Object parentName;
        private int amount;
        private Object amounts;
        private Object branchId;
        private Object chainsaw;
        private List<ChildDeptsBeanXXX> childDepts;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Object getAmounts() {
            return amounts;
        }

        public void setAmounts(Object amounts) {
            this.amounts = amounts;
        }

        public Object getBranchId() {
            return branchId;
        }

        public void setBranchId(Object branchId) {
            this.branchId = branchId;
        }

        public Object getChainsaw() {
            return chainsaw;
        }

        public void setChainsaw(Object chainsaw) {
            this.chainsaw = chainsaw;
        }

        public List<ChildDeptsBeanXXX> getChildDepts() {
            return childDepts;
        }

        public void setChildDepts(List<ChildDeptsBeanXXX> childDepts) {
            this.childDepts = childDepts;
        }

        public static class ParamsBean {
        }

        public static class ChildDeptsBeanXXX {
            /**
             * searchValue : null
             * createBy : admin
             * createTime : 2018-03-16 11:33:00
             * updateBy : admin
             * updateTime : 2019-11-15 13:46:12
             * remark : null
             * params : {}
             * deptId : 101
             * parentId : 100
             * ancestors : 0,100
             * deptName : 龙王镇
             * orderNum : 1
             * leader : 若依
             * phone : 15888888888
             * email : ry@qq.com
             * status : 0
             * delFlag : 0
             * parentName : null
             * amount : 6
             * childDepts : [{"searchValue":null,"createBy":"admin","createTime":"2018-03-16 11:33:00","updateBy":"admin","updateTime":"2019-11-14 14:51:00","remark":null,"params":{},"deptId":105,"parentId":101,"ancestors":"0,100,101","deptName":"白集村","orderNum":"3","leader":"若依","phone":"15888888888","email":"ry@qq.com","status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:16","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":116,"parentId":105,"ancestors":"0,100,101,105","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":null,"parentId":null,"ancestors":null,"deptName":null,"orderNum":null,"leader":null,"phone":null,"email":null,"status":null,"delFlag":null,"parentName":null,"amount":null,"childDepts":null,"amounts":6,"branchId":2,"chainsaw":"若依"}],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null},{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:52:24","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":112,"parentId":101,"ancestors":"0,100,101","deptName":"赵集村","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[{"searchValue":null,"createBy":"admin","createTime":"2019-11-13 10:53:47","updateBy":"admin","updateTime":"2019-11-15 13:46:12","remark":null,"params":{},"deptId":113,"parentId":112,"ancestors":"0,100,101,112","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":0,"childDepts":[],"amounts":null,"branchId":null,"chainsaw":null}],"amounts":null,"branchId":null,"chainsaw":null}]
             * amounts : null
             * branchId : null
             * chainsaw : null
             */

            private Object searchValue;
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private Object remark;
            private ParamsBeanX params;
            private int deptId;
            private int parentId;
            private String ancestors;
            private String deptName;
            private String orderNum;
            private String leader;
            private String phone;
            private String email;
            private String status;
            private String delFlag;
            private Object parentName;
            private int amount;
            private Object amounts;
            private Object branchId;
            private Object chainsaw;
            private List<ChildDeptsBeanXX> childDepts;

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

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
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

            public String getLeader() {
                return leader;
            }

            public void setLeader(String leader) {
                this.leader = leader;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
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

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public Object getAmounts() {
                return amounts;
            }

            public void setAmounts(Object amounts) {
                this.amounts = amounts;
            }

            public Object getBranchId() {
                return branchId;
            }

            public void setBranchId(Object branchId) {
                this.branchId = branchId;
            }

            public Object getChainsaw() {
                return chainsaw;
            }

            public void setChainsaw(Object chainsaw) {
                this.chainsaw = chainsaw;
            }

            public List<ChildDeptsBeanXX> getChildDepts() {
                return childDepts;
            }

            public void setChildDepts(List<ChildDeptsBeanXX> childDepts) {
                this.childDepts = childDepts;
            }

            public static class ParamsBeanX {
            }

            public static class ChildDeptsBeanXX {
                /**
                 * searchValue : null
                 * createBy : admin
                 * createTime : 2018-03-16 11:33:00
                 * updateBy : admin
                 * updateTime : 2019-11-14 14:51:00
                 * remark : null
                 * params : {}
                 * deptId : 105
                 * parentId : 101
                 * ancestors : 0,100,101
                 * deptName : 白集村
                 * orderNum : 3
                 * leader : 若依
                 * phone : 15888888888
                 * email : ry@qq.com
                 * status : 0
                 * delFlag : 0
                 * parentName : null
                 * amount : 6
                 * childDepts : [{"searchValue":null,"createBy":"admin","createTime":"2019-11-14 14:51:16","updateBy":"","updateTime":null,"remark":null,"params":{},"deptId":116,"parentId":105,"ancestors":"0,100,101,105","deptName":"除治队","orderNum":"1","leader":null,"phone":null,"email":null,"status":"0","delFlag":"0","parentName":null,"amount":6,"childDepts":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":null,"parentId":null,"ancestors":null,"deptName":null,"orderNum":null,"leader":null,"phone":null,"email":null,"status":null,"delFlag":null,"parentName":null,"amount":null,"childDepts":null,"amounts":6,"branchId":2,"chainsaw":"若依"}],"amounts":null,"branchId":null,"chainsaw":null}]
                 * amounts : null
                 * branchId : null
                 * chainsaw : null
                 */

                private Object searchValue;
                private String createBy;
                private String createTime;
                private String updateBy;
                private String updateTime;
                private Object remark;
                private ParamsBeanXX params;
                private int deptId;
                private int parentId;
                private String ancestors;
                private String deptName;
                private String orderNum;
                private String leader;
                private String phone;
                private String email;
                private String status;
                private String delFlag;
                private Object parentName;
                private int amount;
                private int amounts;
                private int branchId;
                private String chainsaw;
                private List<ChildDeptsBeanX> childDepts;

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

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public ParamsBeanXX getParams() {
                    return params;
                }

                public void setParams(ParamsBeanXX params) {
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

                public String getLeader() {
                    return leader;
                }

                public void setLeader(String leader) {
                    this.leader = leader;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
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

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public int getAmounts() {
                    return amounts;
                }

                public void setAmounts(int amounts) {
                    this.amounts = amounts;
                }

                public int getBranchId() {
                    return branchId;
                }

                public void setBranchId(int branchId) {
                    this.branchId = branchId;
                }

                public String getChainsaw() {
                    return chainsaw;
                }

                public void setChainsaw(String chainsaw) {
                    this.chainsaw = chainsaw;
                }

                public List<ChildDeptsBeanX> getChildDepts() {
                    return childDepts;
                }

                public void setChildDepts(List<ChildDeptsBeanX> childDepts) {
                    this.childDepts = childDepts;
                }

                public static class ParamsBeanXX {
                }

                public static class ChildDeptsBeanX {
                    /**
                     * searchValue : null
                     * createBy : admin
                     * createTime : 2019-11-14 14:51:16
                     * updateBy :
                     * updateTime : null
                     * remark : null
                     * params : {}
                     * deptId : 116
                     * parentId : 105
                     * ancestors : 0,100,101,105
                     * deptName : 除治队
                     * orderNum : 1
                     * leader : null
                     * phone : null
                     * email : null
                     * status : 0
                     * delFlag : 0
                     * parentName : null
                     * amount : 6
                     * childDepts : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":null,"parentId":null,"ancestors":null,"deptName":null,"orderNum":null,"leader":null,"phone":null,"email":null,"status":null,"delFlag":null,"parentName":null,"amount":null,"childDepts":null,"amounts":6,"branchId":2,"chainsaw":"若依"}]
                     * amounts : null
                     * branchId : null
                     * chainsaw : null
                     */

                    private Object searchValue;
                    private String createBy;
                    private String createTime;
                    private String updateBy;
                    private Object updateTime;
                    private Object remark;
                    private ParamsBeanXXX params;
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
                    private int amount;
                    private int  amounts;
                    private int  branchId;
                    private String chainsaw;
                    private List<ChildDeptsBean> childDepts;

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

                    public ParamsBeanXXX getParams() {
                        return params;
                    }

                    public void setParams(ParamsBeanXXX params) {
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

                    public int getAmount() {
                        return amount;
                    }

                    public void setAmount(int amount) {
                        this.amount = amount;
                    }

                    public Object getAmounts() {
                        return amounts;
                    }

                    public void setAmounts(int amounts) {
                        this.amounts = amounts;
                    }

                    public int getBranchId() {
                        return branchId;
                    }

                    public void setBranchId(int branchId) {
                        this.branchId = branchId;
                    }

                    public String getChainsaw() {
                        return chainsaw;
                    }

                    public void setChainsaw(String chainsaw) {
                        this.chainsaw = chainsaw;
                    }

                    public List<ChildDeptsBean> getChildDepts() {
                        return childDepts;
                    }

                    public void setChildDepts(List<ChildDeptsBean> childDepts) {
                        this.childDepts = childDepts;
                    }

                    public static class ParamsBeanXXX {
                    }

                    public static class ChildDeptsBean {
                        /**
                         * searchValue : null
                         * createBy : null
                         * createTime : null
                         * updateBy : null
                         * updateTime : null
                         * remark : null
                         * params : {}
                         * deptId : null
                         * parentId : null
                         * ancestors : null
                         * deptName : null
                         * orderNum : null
                         * leader : null
                         * phone : null
                         * email : null
                         * status : null
                         * delFlag : null
                         * parentName : null
                         * amount : null
                         * childDepts : null
                         * amounts : 6
                         * branchId : 2
                         * chainsaw : 若依
                         */

                        private Object searchValue;
                        private Object createBy;
                        private Object createTime;
                        private Object updateBy;
                        private Object updateTime;
                        private Object remark;
                        private ParamsBeanXXXX params;
                        private Object deptId;
                        private Object parentId;
                        private Object ancestors;
                        private Object deptName;
                        private Object orderNum;
                        private Object leader;
                        private Object phone;
                        private Object email;
                        private Object status;
                        private Object delFlag;
                        private Object parentName;
                        private Object amount;
                        private Object childDepts;
                        private int amounts;
                        private int branchId;
                        private String chainsaw;

                        public Object getSearchValue() {
                            return searchValue;
                        }

                        public void setSearchValue(Object searchValue) {
                            this.searchValue = searchValue;
                        }

                        public Object getCreateBy() {
                            return createBy;
                        }

                        public void setCreateBy(Object createBy) {
                            this.createBy = createBy;
                        }

                        public Object getCreateTime() {
                            return createTime;
                        }

                        public void setCreateTime(Object createTime) {
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

                        public ParamsBeanXXXX getParams() {
                            return params;
                        }

                        public void setParams(ParamsBeanXXXX params) {
                            this.params = params;
                        }

                        public Object getDeptId() {
                            return deptId;
                        }

                        public void setDeptId(Object deptId) {
                            this.deptId = deptId;
                        }

                        public Object getParentId() {
                            return parentId;
                        }

                        public void setParentId(Object parentId) {
                            this.parentId = parentId;
                        }

                        public Object getAncestors() {
                            return ancestors;
                        }

                        public void setAncestors(Object ancestors) {
                            this.ancestors = ancestors;
                        }

                        public Object getDeptName() {
                            return deptName;
                        }

                        public void setDeptName(Object deptName) {
                            this.deptName = deptName;
                        }

                        public Object getOrderNum() {
                            return orderNum;
                        }

                        public void setOrderNum(Object orderNum) {
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

                        public Object getStatus() {
                            return status;
                        }

                        public void setStatus(Object status) {
                            this.status = status;
                        }

                        public Object getDelFlag() {
                            return delFlag;
                        }

                        public void setDelFlag(Object delFlag) {
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

                        public int getAmounts() {
                            return amounts;
                        }

                        public void setAmounts(int amounts) {
                            this.amounts = amounts;
                        }

                        public int getBranchId() {
                            return branchId;
                        }

                        public void setBranchId(int branchId) {
                            this.branchId = branchId;
                        }

                        public String getChainsaw() {
                            return chainsaw;
                        }

                        public void setChainsaw(String chainsaw) {
                            this.chainsaw = chainsaw;
                        }

                        public static class ParamsBeanXXXX {
                        }
                    }
                }
            }
        }
    }
}
