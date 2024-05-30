package com.phy.ota_demo.bean;

import java.util.Arrays;

public class BoxDevice {
    private String imei;
    private String bleVersion;

    private String mac;

    private String searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String[] params;
    private String deviceId;
    private String deviceCode;
    private String devicePrintCode;
    private String moduleStatusDetail;
    private String status;
    private String isOnline;
    private String customerName;
    private String hubName;
    private String macFlag;
    private String batteryPercent;
    private String warningTypes;
    private String storageName;
    private String wlId;
    private String hubId;
    private String wlName;
    private String imsi;
    private String boxModel;
    private String boxCode;
    private String storageId;
    private String usedCount;
    private String longitude;
    private String latitude;
    private String location;
    private String boxUseStatus;
    private String lockStatus;
    private String batteryStatus;
    private String tempStatus;
    private String tiltStatus;
    private String batteryTemStatus;
    private String abnormalLock;
    private String temperature;
    private String lowTemp;
    private String highTemp;
    private String humidity;
    private String vibration;
    private String tilt;
    private String battery;
    private String customerId;
    private String locUpdateTime;
    private String lockOffTime;
    private String tcpUpdateTime;
    private String unum;
    private String maxTem;
    private String minTem;
    private String tem;
    private String deviceLocation;
    private String deviceLocationDetaill;
    private String endTime;
    private String catVersion;
    private String id;
    private String firstArrivalTime;
    private String deleted;

    public BoxDevice(String imei, String bleVersion) {
        this.imei = imei;
        this.bleVersion = bleVersion;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
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

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDevicePrintCode() {
        return devicePrintCode;
    }

    public void setDevicePrintCode(String devicePrintCode) {
        this.devicePrintCode = devicePrintCode;
    }

    public String getModuleStatusDetail() {
        return moduleStatusDetail;
    }

    public void setModuleStatusDetail(String moduleStatusDetail) {
        this.moduleStatusDetail = moduleStatusDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnline() {
        return isOnline;
    }

    public void setOnline(String online) {
        isOnline = online;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public String getMacFlag() {
        return macFlag;
    }

    public void setMacFlag(String macFlag) {
        this.macFlag = macFlag;
    }

    public String getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(String batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public String getWarningTypes() {
        return warningTypes;
    }

    public void setWarningTypes(String warningTypes) {
        this.warningTypes = warningTypes;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getWlId() {
        return wlId;
    }

    public void setWlId(String wlId) {
        this.wlId = wlId;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public String getWlName() {
        return wlName;
    }

    public void setWlName(String wlName) {
        this.wlName = wlName;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getBoxModel() {
        return boxModel;
    }

    public void setBoxModel(String boxModel) {
        this.boxModel = boxModel;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(String usedCount) {
        this.usedCount = usedCount;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBoxUseStatus() {
        return boxUseStatus;
    }

    public void setBoxUseStatus(String boxUseStatus) {
        this.boxUseStatus = boxUseStatus;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(String tempStatus) {
        this.tempStatus = tempStatus;
    }

    public String getTiltStatus() {
        return tiltStatus;
    }

    public void setTiltStatus(String tiltStatus) {
        this.tiltStatus = tiltStatus;
    }

    public String getBatteryTemStatus() {
        return batteryTemStatus;
    }

    public void setBatteryTemStatus(String batteryTemStatus) {
        this.batteryTemStatus = batteryTemStatus;
    }

    public String getAbnormalLock() {
        return abnormalLock;
    }

    public void setAbnormalLock(String abnormalLock) {
        this.abnormalLock = abnormalLock;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVibration() {
        return vibration;
    }

    public void setVibration(String vibration) {
        this.vibration = vibration;
    }

    public String getTilt() {
        return tilt;
    }

    public void setTilt(String tilt) {
        this.tilt = tilt;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLocUpdateTime() {
        return locUpdateTime;
    }

    public void setLocUpdateTime(String locUpdateTime) {
        this.locUpdateTime = locUpdateTime;
    }

    public String getLockOffTime() {
        return lockOffTime;
    }

    public void setLockOffTime(String lockOffTime) {
        this.lockOffTime = lockOffTime;
    }

    public String getTcpUpdateTime() {
        return tcpUpdateTime;
    }

    public void setTcpUpdateTime(String tcpUpdateTime) {
        this.tcpUpdateTime = tcpUpdateTime;
    }

    public String getUnum() {
        return unum;
    }

    public void setUnum(String unum) {
        this.unum = unum;
    }

    public String getMaxTem() {
        return maxTem;
    }

    public void setMaxTem(String maxTem) {
        this.maxTem = maxTem;
    }

    public String getMinTem() {
        return minTem;
    }

    public void setMinTem(String minTem) {
        this.minTem = minTem;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getDeviceLocationDetaill() {
        return deviceLocationDetaill;
    }

    public void setDeviceLocationDetaill(String deviceLocationDetaill) {
        this.deviceLocationDetaill = deviceLocationDetaill;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCatVersion() {
        return catVersion;
    }

    public void setCatVersion(String catVersion) {
        this.catVersion = catVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstArrivalTime() {
        return firstArrivalTime;
    }

    public void setFirstArrivalTime(String firstArrivalTime) {
        this.firstArrivalTime = firstArrivalTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "BoxDevice{" +
                "imei='" + imei + '\'' +
                ", bleVersion='" + bleVersion + '\'' +
                ", mac='" + mac + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", params=" + Arrays.toString(params) +
                ", deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", devicePrintCode='" + devicePrintCode + '\'' +
                ", moduleStatusDetail='" + moduleStatusDetail + '\'' +
                ", status='" + status + '\'' +
                ", isOnline='" + isOnline + '\'' +
                ", customerName='" + customerName + '\'' +
                ", hubName='" + hubName + '\'' +
                ", macFlag='" + macFlag + '\'' +
                ", batteryPercent='" + batteryPercent + '\'' +
                ", warningTypes='" + warningTypes + '\'' +
                ", storageName='" + storageName + '\'' +
                ", wlId='" + wlId + '\'' +
                ", hubId='" + hubId + '\'' +
                ", wlName='" + wlName + '\'' +
                ", imsi='" + imsi + '\'' +
                ", boxModel='" + boxModel + '\'' +
                ", boxCode='" + boxCode + '\'' +
                ", storageId='" + storageId + '\'' +
                ", usedCount='" + usedCount + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", boxUseStatus='" + boxUseStatus + '\'' +
                ", lockStatus='" + lockStatus + '\'' +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", tempStatus='" + tempStatus + '\'' +
                ", tiltStatus='" + tiltStatus + '\'' +
                ", batteryTemStatus='" + batteryTemStatus + '\'' +
                ", abnormalLock='" + abnormalLock + '\'' +
                ", temperature='" + temperature + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", highTemp='" + highTemp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", vibration='" + vibration + '\'' +
                ", tilt='" + tilt + '\'' +
                ", battery='" + battery + '\'' +
                ", customerId='" + customerId + '\'' +
                ", locUpdateTime='" + locUpdateTime + '\'' +
                ", lockOffTime='" + lockOffTime + '\'' +
                ", tcpUpdateTime='" + tcpUpdateTime + '\'' +
                ", unum='" + unum + '\'' +
                ", maxTem='" + maxTem + '\'' +
                ", minTem='" + minTem + '\'' +
                ", tem='" + tem + '\'' +
                ", deviceLocation='" + deviceLocation + '\'' +
                ", deviceLocationDetaill='" + deviceLocationDetaill + '\'' +
                ", endTime='" + endTime + '\'' +
                ", catVersion='" + catVersion + '\'' +
                ", id='" + id + '\'' +
                ", firstArrivalTime='" + firstArrivalTime + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }

    public BoxDevice(String imei, String bleVersion, String mac, String searchValue, String createBy, String createTime, String updateBy, String updateTime, String[] params, String deviceId, String deviceCodenull, String devicePrintCode, String moduleStatusDetail, String status, String isOnline, String customerName, String hubName, String macFlag, String batteryPercent, String warningTypes, String storageName, String wlId, String hubId, String wlName, String imsi, String boxModel, String boxCode, String storageId, String usedCount, String longitude, String latitude, String location, String boxUseStatus, String lockStatus, String batteryStatus, String tempStatus, String tiltStatus, String batteryTemStatus, String abnormalLock, String temperature, String lowTemp, String highTemp, String humidity, String vibration, String tilt, String battery, String customerId, String locUpdateTime, String lockOffTime, String tcpUpdateTime, String unum, String maxTem, String minTem, String tem, String deviceLocation, String deviceLocationDetaill, String endTime, String catVersion, String id, String firstArrivalTime, String deleted) {
        this.imei = imei;
        this.bleVersion = bleVersion;
        this.mac = mac;
        this.searchValue = searchValue;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.params = params;
        this.deviceId = deviceId;
        this.deviceCode = deviceCodenull;
        this.devicePrintCode = devicePrintCode;
        this.moduleStatusDetail = moduleStatusDetail;
        this.status = status;
        this.isOnline = isOnline;
        this.customerName = customerName;
        this.hubName = hubName;
        this.macFlag = macFlag;
        this.batteryPercent = batteryPercent;
        this.warningTypes = warningTypes;
        this.storageName = storageName;
        this.wlId = wlId;
        this.hubId = hubId;
        this.wlName = wlName;
        this.imsi = imsi;
        this.boxModel = boxModel;
        this.boxCode = boxCode;
        this.storageId = storageId;
        this.usedCount = usedCount;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.boxUseStatus = boxUseStatus;
        this.lockStatus = lockStatus;
        this.batteryStatus = batteryStatus;
        this.tempStatus = tempStatus;
        this.tiltStatus = tiltStatus;
        this.batteryTemStatus = batteryTemStatus;
        this.abnormalLock = abnormalLock;
        this.temperature = temperature;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.humidity = humidity;
        this.vibration = vibration;
        this.tilt = tilt;
        this.battery = battery;
        this.customerId = customerId;
        this.locUpdateTime = locUpdateTime;
        this.lockOffTime = lockOffTime;
        this.tcpUpdateTime = tcpUpdateTime;
        this.unum = unum;
        this.maxTem = maxTem;
        this.minTem = minTem;
        this.tem = tem;
        this.deviceLocation = deviceLocation;
        this.deviceLocationDetaill = deviceLocationDetaill;
        this.endTime = endTime;
        this.catVersion = catVersion;
        this.id = id;
        this.firstArrivalTime = firstArrivalTime;
        this.deleted = deleted;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBleVersion() {
        return bleVersion;
    }

    public void setBleVersion(String bleVersion) {
        this.bleVersion = bleVersion;
    }
}
