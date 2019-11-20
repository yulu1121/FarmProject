package com.anshi.farmproject.entry;

import java.util.List;

public class DetailQueryEntry {

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 10:48:23","updateBy":"","updateTime":null,"remark":null,"params":{},"fellingId":15,"number":"LWZ-BJC-RY-00002","orders":2,"company":"白集村","cureTime":"2019-11-20 10:40:20","cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":6,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"小山坡","panoramaPath":"/profile/img/20191120/20191120104719.jpg","numberPath":"/profile/img/20191120/20191120104745.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138595","latitude":"32.082284","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":null},{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 11:01:13","updateBy":"","updateTime":"2019-11-20 14:40:58","remark":null,"params":{},"fellingId":16,"number":"LWZ-BJC-RY-00001","orders":1,"company":"白集村","cureTime":"2019-11-20 00:00:00","cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":5,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"半坡","panoramaPath":"/profile/img/20191120/20191120103122.jpg","numberPath":"/profile/img/20191120/20191120103322.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138595","latitude":"32.082284","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":null},{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 11:02:01","updateBy":"","updateTime":null,"remark":null,"params":{},"fellingId":17,"number":"LWZ-BJC-RY-00001","orders":1,"company":"白集村","cureTime":null,"cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":5,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"半坡","panoramaPath":"/profile/img/20191120/20191120103122.jpg","numberPath":"/profile/img/20191120/20191120103322.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138595","latitude":"32.082284","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":null},{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 11:02:29","updateBy":"","updateTime":null,"remark":null,"params":{},"fellingId":18,"number":"LWZ-BJC-RY-00001","orders":1,"company":"白集村","cureTime":null,"cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":5,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"半坡","panoramaPath":"/profile/img/20191120/20191120103122.jpg","numberPath":"/profile/img/20191120/20191120103322.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138595","latitude":"32.082284","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":null},{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 11:08:30","updateBy":"","updateTime":null,"remark":null,"params":{},"fellingId":19,"number":"LWZ-BJC-RY-00001","orders":1,"company":"白集村","cureTime":null,"cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":5,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"半坡","panoramaPath":"/profile/img/20191120/20191120103122.jpg","numberPath":"/profile/img/20191120/20191120103322.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138595","latitude":"32.082284","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":105},{"searchValue":null,"createBy":"ry","createTime":"2019-11-20 13:48:21","updateBy":"","updateTime":null,"remark":null,"params":{},"fellingId":20,"number":"LWZ-BJC-RY-00003","orders":3,"company":"白集村","cureTime":"2019-11-20 13:47:24","cureId":1,"cureName":"盛世嫡妃","townId":101,"townName":"龙王镇","villageId":112,"villageName":"赵集村","groups":6,"teamId":116,"teamName":"除治队","branchId":2,"branchName":null,"groundDiameter":12.5,"placeName":"汕尾","panoramaPath":"/profile/img/20191120/20191120134757.jpg","numberPath":"/profile/img/20191120/20191120134809.jpg","chainsaw":"若依","botanyId":1,"botanyName":"喇叭花","longitude":"112.138563","latitude":"32.082119","dataSources":null,"status":"0","delFlag":"0","userId":2,"startTime":null,"endTime":null,"amount":null,"deptId":105}]
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
         * createBy : ry
         * createTime : 2019-11-20 10:48:23
         * updateBy :
         * updateTime : null
         * remark : null
         * params : {}
         * fellingId : 15
         * number : LWZ-BJC-RY-00002
         * orders : 2
         * company : 白集村
         * cureTime : 2019-11-20 10:40:20
         * cureId : 1
         * cureName : 盛世嫡妃
         * townId : 101
         * townName : 龙王镇
         * villageId : 112
         * villageName : 赵集村
         * groups : 6
         * teamId : 116
         * teamName : 除治队
         * branchId : 2
         * branchName : null
         * groundDiameter : 12.5
         * placeName : 小山坡
         * panoramaPath : /profile/img/20191120/20191120104719.jpg
         * numberPath : /profile/img/20191120/20191120104745.jpg
         * chainsaw : 若依
         * botanyId : 1
         * botanyName : 喇叭花
         * longitude : 112.138595
         * latitude : 32.082284
         * dataSources : null
         * status : 0
         * delFlag : 0
         * userId : 2
         * startTime : null
         * endTime : null
         * amount : null
         * deptId : null
         */

        private Object searchValue;
        private String createBy;
        private String createTime;
        private String updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private int fellingId;
        private String number;
        private int orders;
        private String company;
        private String cureTime;
        private int cureId;
        private String cureName;
        private int townId;
        private String townName;
        private int villageId;
        private String villageName;
        private int groups;
        private int teamId;
        private String teamName;
        private int branchId;
        private Object branchName;
        private double groundDiameter;
        private String placeName;
        private String panoramaPath;
        private String numberPath;
        private String chainsaw;
        private int botanyId;
        private String botanyName;
        private String longitude;
        private String latitude;
        private Object dataSources;
        private String status;
        private String delFlag;
        private int userId;
        private Object startTime;
        private Object endTime;
        private Object amount;
        private Object deptId;

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

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public int getFellingId() {
            return fellingId;
        }

        public void setFellingId(int fellingId) {
            this.fellingId = fellingId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCureTime() {
            return cureTime;
        }

        public void setCureTime(String cureTime) {
            this.cureTime = cureTime;
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

        public int getTownId() {
            return townId;
        }

        public void setTownId(int townId) {
            this.townId = townId;
        }

        public String getTownName() {
            return townName;
        }

        public void setTownName(String townName) {
            this.townName = townName;
        }

        public int getVillageId() {
            return villageId;
        }

        public void setVillageId(int villageId) {
            this.villageId = villageId;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }

        public int getGroups() {
            return groups;
        }

        public void setGroups(int groups) {
            this.groups = groups;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getBranchId() {
            return branchId;
        }

        public void setBranchId(int branchId) {
            this.branchId = branchId;
        }

        public Object getBranchName() {
            return branchName;
        }

        public void setBranchName(Object branchName) {
            this.branchName = branchName;
        }

        public double getGroundDiameter() {
            return groundDiameter;
        }

        public void setGroundDiameter(double groundDiameter) {
            this.groundDiameter = groundDiameter;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPanoramaPath() {
            return panoramaPath;
        }

        public void setPanoramaPath(String panoramaPath) {
            this.panoramaPath = panoramaPath;
        }

        public String getNumberPath() {
            return numberPath;
        }

        public void setNumberPath(String numberPath) {
            this.numberPath = numberPath;
        }

        public String getChainsaw() {
            return chainsaw;
        }

        public void setChainsaw(String chainsaw) {
            this.chainsaw = chainsaw;
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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public Object getDataSources() {
            return dataSources;
        }

        public void setDataSources(Object dataSources) {
            this.dataSources = dataSources;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public Object getDeptId() {
            return deptId;
        }

        public void setDeptId(Object deptId) {
            this.deptId = deptId;
        }

        public static class ParamsBean {
        }
    }
}
