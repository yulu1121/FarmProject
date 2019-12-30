//private String userName;
//private String townName;
//private int townId;
//private String villageName;
//private int villageId;
package com.anshi.farmproject.entry;

public class LoginEntry{

    /**
     * msg : 操作成功
     * code : 0
     * data : {"role":{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"roleId":7,"roleName":"监理公司","roleKey":"jianli","roleSort":"6","dataScope":"2","status":"0","delFlag":null,"flag":false,"menuIds":null,"deptIds":null,"admin":false},"loginName":"nieshixiong","dept":{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":101,"parentId":100,"ancestors":"0,100","deptName":"点军区林水局","orderNum":"1","leader":"","phone":null,"email":null,"status":"0","delFlag":null,"parentName":null,"type":"1","amount":null,"childDepts":null,"amounts":null,"branchId":null,"chainsaw":null},"userName":"聂世雄","userId":446}
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
         * role : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"roleId":7,"roleName":"监理公司","roleKey":"jianli","roleSort":"6","dataScope":"2","status":"0","delFlag":null,"flag":false,"menuIds":null,"deptIds":null,"admin":false}
         * loginName : nieshixiong
         * dept : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"deptId":101,"parentId":100,"ancestors":"0,100","deptName":"点军区林水局","orderNum":"1","leader":"","phone":null,"email":null,"status":"0","delFlag":null,"parentName":null,"type":"1","amount":null,"childDepts":null,"amounts":null,"branchId":null,"chainsaw":null}
         * userName : 聂世雄
         * userId : 446
         */

        private RoleBean role;
        private String loginName;
        private DeptBean dept;
        private String userName;
        private int userId;
        private String townName;
        private int townId;
        private String villageName;

        public String getTownName() {
            return townName;
        }

        public void setTownName(String townName) {
            this.townName = townName;
        }

        public int getTownId() {
            return townId;
        }

        public void setTownId(int townId) {
            this.townId = townId;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public int getVillageId() {
            return villageId;
        }

        public void setVillageId(int villageId) {
            this.villageId = villageId;
        }

        private int villageId;
        public RoleBean getRole() {
            return role;
        }

        public void setRole(RoleBean role) {
            this.role = role;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public DeptBean getDept() {
            return dept;
        }

        public void setDept(DeptBean dept) {
            this.dept = dept;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public static class RoleBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * roleId : 7
             * roleName : 监理公司
             * roleKey : jianli
             * roleSort : 6
             * dataScope : 2
             * status : 0
             * delFlag : null
             * flag : false
             * menuIds : null
             * deptIds : null
             * admin : false
             */

            private Object searchValue;
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private int roleId;
            private String roleName;
            private String roleKey;
            private String roleSort;
            private String dataScope;
            private String status;
            private Object delFlag;
            private boolean flag;
            private Object menuIds;
            private Object deptIds;
            private boolean admin;

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

            public int getRoleId() {
                return roleId;
            }

            public void setRoleId(int roleId) {
                this.roleId = roleId;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getRoleKey() {
                return roleKey;
            }

            public void setRoleKey(String roleKey) {
                this.roleKey = roleKey;
            }

            public String getRoleSort() {
                return roleSort;
            }

            public void setRoleSort(String roleSort) {
                this.roleSort = roleSort;
            }

            public String getDataScope() {
                return dataScope;
            }

            public void setDataScope(String dataScope) {
                this.dataScope = dataScope;
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

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public Object getMenuIds() {
                return menuIds;
            }

            public void setMenuIds(Object menuIds) {
                this.menuIds = menuIds;
            }

            public Object getDeptIds() {
                return deptIds;
            }

            public void setDeptIds(Object deptIds) {
                this.deptIds = deptIds;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public static class ParamsBean {
            }
        }

        public static class DeptBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * deptId : 101
             * parentId : 100
             * ancestors : 0,100
             * deptName : 点军区林水局
             * orderNum : 1
             * leader :
             * phone : null
             * email : null
             * status : 0
             * delFlag : null
             * parentName : null
             * type : 1
             * amount : null
             * childDepts : null
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
            private ParamsBeanX params;
            private int deptId;
            private int parentId;
            private String ancestors;
            private String deptName;
            private String orderNum;
            private String leader;
            private Object phone;
            private Object email;
            private String status;
            private Object delFlag;
            private Object parentName;
            private String type;
            private Object amount;
            private Object childDepts;
            private Object amounts;
            private Object branchId;
            private Object chainsaw;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public static class ParamsBeanX {
            }
        }
    }
}