package ddwucom.mobile.software_competition.one;

import java.io.Serializable;

public class CourierDTO implements Serializable {
    private String name;
    private String newAddress;
    private String oldAddress;
    private double lat;
    private double lng;
    private String weekdayStartTime;
    private String weekdayEndTime;
    private String saturdayStartTime;
    private String saturdayEndTime;
    private String holidayStartTime;
    private String holidayEndTime;
    private String freeUseTime;
    private String lateFee;
    private String lateFeeTime;
    private String instruction;
    private String box;
    private String serviceCenterTel;
    private String managementAgencyTel;
    private String updateDate;

    public CourierDTO(String name, String newAddress, String oldAddress, double lat, double lng, String weekdayStartTime, String weekdayEndTime, String saturdayStartTime, String saturdayEndTime, String holidayStartTime, String holidayEndTime, String freeUseTime, String lateFee, String lateFeeTime, String instruction, String box, String serviceCenterTel, String managementAgencyTel, String updateDate) {
        this.name = name;
        this.newAddress = newAddress;
        this.oldAddress = oldAddress;
        this.lat = lat;
        this.lng = lng;
        this.weekdayStartTime = weekdayStartTime;
        this.weekdayEndTime = weekdayEndTime;
        this.saturdayStartTime = saturdayStartTime;
        this.saturdayEndTime = saturdayEndTime;
        this.holidayStartTime = holidayStartTime;
        this.holidayEndTime = holidayEndTime;
        this.freeUseTime = freeUseTime;
        this.lateFee = lateFee;
        this.lateFeeTime = lateFeeTime;
        this.instruction = instruction;
        setBox(box);
        this.serviceCenterTel = serviceCenterTel;
        this.managementAgencyTel = managementAgencyTel;
        this.updateDate = updateDate;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNewAddress() { return newAddress; }

    public void setNewAddress(String newAddress) { this.newAddress = newAddress; }

    public String getOldAddress() { return oldAddress; }

    public void setOldAddress(String oldAddress) { this.oldAddress = oldAddress; }

    public double getLat() { return lat; }

    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }

    public void setLng(double lng) { this.lng = lng; }

    public String getWeekdayStartTime() { return weekdayStartTime; }

    public void setWeekdayStartTime(String weekdayStartTime) { this.weekdayStartTime = weekdayStartTime; }

    public String getWeekdayEndTime() { return weekdayEndTime; }

    public void setWeekdayEndTime(String weekdayEndTime) { this.weekdayEndTime = weekdayEndTime; }

    public String getSaturdayStartTime() { return saturdayStartTime; }

    public void setSaturdayStartTime(String saturdayStartTime) { this.saturdayStartTime = saturdayStartTime; }

    public String getSaturdayEndTime() { return saturdayEndTime; }

    public void setSaturdayEndTime(String saturdayEndTime) { this.saturdayEndTime = saturdayEndTime; }

    public String getHolidayStartTime() { return holidayStartTime; }

    public void setHolidayStartTime(String holidayStartTime) { this.holidayStartTime = holidayStartTime; }

    public String getHolidayEndTime() { return holidayEndTime; }

    public void setHolidayEndTime(String holidayEndTime) { this.holidayEndTime = holidayEndTime; }

    public String getFreeUseTime() { return freeUseTime; }

    public void setFreeUseTime(String freeUseTime) { this.freeUseTime = freeUseTime; }

    public String getLateFee() { return lateFee; }

    public void setLateFee(String lateFee) { this.lateFee = lateFee; }

    public String getLateFeeTime() { return lateFeeTime; }

    public void setLateFeeTime(String lateFeeTime) { this.lateFeeTime = lateFeeTime; }

    public String getInstruction() { return instruction; }

    public void setInstruction(String instruction) { this.instruction = instruction; }

    public String getBox() { return box; }

    public void setBox(String box) {
        String box2 = "";
        Character c;
        for (int i = 0; i < box.length(); i++) {
            c = box.charAt(i);
            if (c == '/')
                box2 += ": ";
            else if (c == '+')
                box2 += "개, ";
            else
                box2 += c;
        }
        box2 += "개";
        this.box = box2;
    }

    public String getServiceCenterTel() { return serviceCenterTel; }

    public void setServiceCenterTel(String serviceCenterTel) { this.serviceCenterTel = serviceCenterTel; }

    public String getManagementAgencyTel() { return managementAgencyTel; }

    public void setManagementAgencyTel(String managementAgencyTel) { this.managementAgencyTel = managementAgencyTel; }

    public String getUpdateDate() { return updateDate; }

    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }
}
