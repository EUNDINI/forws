package ddwucom.mobile.software_competition;

import java.io.Serializable;

public class GuardHouseDTO implements Serializable {
    private String name;
    private String newAddress;
    private String oldAddress;
    private double lat;
    private double lng;
    private String tel;
    private String policeOfficeName;
    private String updateDate;

    public GuardHouseDTO(String name, String newAddress, String oldAddress, double lat, double lng, String tel, String policeOfficeName, String updateDate) {
        this.name = name;
        this.newAddress = newAddress;
        this.oldAddress = oldAddress;
        this.lat = lat;
        this.lng = lng;
        this.tel = tel;
        this.policeOfficeName = policeOfficeName;
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

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getPoliceOfficeName() { return policeOfficeName; }

    public void setPoliceOfficeName(String policeOfficeName) { this.policeOfficeName = policeOfficeName; }

    public String getUpdateDate() { return updateDate; }

    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }
}
