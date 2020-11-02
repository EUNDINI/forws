package ddwucom.mobile.software_competition;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

public class XmlParser {
    private ArrayList<CourierDTO> courierList = null;
    private ArrayList<GuardHouseDTO> guardHouseList = null;
    private Context context;

    public XmlParser(Context context){
        this.context = context;
    }

    public void parseXMLCourier() throws Exception {
        if(courierList != null)
            return;

        String tagName;
        double lat = 0, lng = 0;
        String name = "", newAddress = "", oldAddress = "", weekdayStartTime = "", weekdayEndTime = "", saturdayStartTime = "", saturdayEndTime = "";
        String holidayStartTime = "", holidayEndTime = "", freeUseTime = "", lateFee = "", lateFeeTime = "", instruction = "", box = "", serviceCenterTel = "", managementAgencyTel = "", updateDate = "";
        courierList = new ArrayList<CourierDTO>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(context.getResources().openRawResource(R.raw.courier_data), "UTF-8");

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    switch (tagName) {
                        case "시설명":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                name = " ";
                            else
                                name = parser.getText();
                            break;
                        case "소재지도로명주소":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                newAddress = " ";
                            else
                                newAddress = parser.getText();
                            break;
                        case "소재지지번주소":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                oldAddress = " ";
                            else
                                oldAddress = parser.getText();
                            break;
                        case "위도":
                            eventType = parser.next();
                            lat = Double.parseDouble(parser.getText());
                            break;
                        case "경도":
                            eventType = parser.next();
                            lng = Double.parseDouble(parser.getText());
                            break;
                        case "평일운영시작시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                weekdayStartTime = " ";
                            else
                                weekdayStartTime = parser.getText();
                            break;
                        case "평일운영종료시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                weekdayEndTime = " ";
                            else
                                weekdayEndTime = parser.getText();
                            break;
                        case "토요일운영시작시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                saturdayStartTime = " ";
                            else
                                saturdayStartTime = parser.getText();
                            break;
                        case "토요일운영종료시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                saturdayEndTime = " ";
                            else
                                saturdayEndTime = parser.getText();
                            break;
                        case "공휴일운영시작시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                holidayStartTime = " ";
                            else
                                holidayStartTime = parser.getText();
                            break;
                        case "공휴일운영종료시각":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                holidayEndTime = " ";
                            else
                                holidayEndTime = parser.getText();
                            break;
                        case "무료이용시간":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                freeUseTime = " ";
                            else
                                freeUseTime = parser.getText();
                            break;
                        case "연체료":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                lateFee = " ";
                            else
                                lateFee = parser.getText();
                            break;
                        case "연체료부과단위시간":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                lateFeeTime = " ";
                            else
                                lateFeeTime = parser.getText();
                            break;
                        case "사용방법설명":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                instruction = " ";
                            else
                                instruction = parser.getText();
                            break;
                        case "택배함종류코드":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                box = " ";
                            else
                                box = parser.getText();
                            break;
                        case "고객센터전화번호":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                serviceCenterTel = " ";
                            else
                                serviceCenterTel = parser.getText();
                            break;
                        case "관리기관전화번호":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                managementAgencyTel = " ";
                            else
                                managementAgencyTel = parser.getText();
                            break;
                        case "데이터기준일자":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                updateDate = " ";
                            else
                                updateDate = parser.getText();
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equals("데이터기준일자")) {
                    courierList.add(new CourierDTO(name, newAddress, oldAddress, lat, lng, weekdayStartTime, weekdayEndTime,
                            saturdayStartTime, saturdayEndTime, holidayStartTime, holidayEndTime, freeUseTime, lateFee, lateFeeTime,
                            instruction, box, serviceCenterTel, managementAgencyTel, updateDate));
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseXMLGuardHouse() throws Exception {
        String tagName;
        double lat = 0, lng = 0;
        String name = "", newAddress = "", oldAddress = "", tel = "", policeOfficeName = "", updateDate = "";
        guardHouseList = new ArrayList<GuardHouseDTO>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(context.getResources().openRawResource(R.raw.guardhouse_data), "UTF-8");

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    switch (tagName) {
                        case "점포명":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                name = " ";
                            else
                                name = parser.getText();
                            break;
                        case "소재지도로명주소":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                newAddress = " ";
                            else
                                newAddress = parser.getText();
                            break;
                        case "소재지지번주소":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                oldAddress = " ";
                            else
                                oldAddress = parser.getText();
                            break;
                        case "위도":
                            eventType = parser.next();
                            lat = Double.parseDouble(parser.getText());
                            break;
                        case "경도":
                            eventType = parser.next();
                            lng = Double.parseDouble(parser.getText());
                            break;
                        case "여성안심지킴이집전화번호":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                tel = " ";
                            else
                                tel = parser.getText();
                            break;
                        case "관할경찰서명":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                policeOfficeName = " ";
                            else
                                policeOfficeName = parser.getText();
                        case "데이터기준일자":
                            eventType = parser.next();
                            if (eventType == XmlPullParser.END_TAG)
                                updateDate = " ";
                            else
                                updateDate = parser.getText();
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equals("데이터기준일자")) {
                    guardHouseList.add(new GuardHouseDTO(name, newAddress, oldAddress, lat, lng, tel, policeOfficeName, updateDate));
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CourierDTO> getCourierList() {
        return courierList;
    }

    public ArrayList<GuardHouseDTO> getGuardHouseList() {
        return guardHouseList;
    }
}
