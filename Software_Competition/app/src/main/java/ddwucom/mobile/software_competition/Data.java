package ddwucom.mobile.software_competition;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Data {
    private Context context;
    private AssetManager assetmanager;
    private InputStreamReader is;
    private BufferedReader reader;
    private ArrayList<double[]> cctvList;
    private ArrayList<String[]> policeList;

    Data(Context context){
        this.context = context;
        assetmanager = context.getResources().getAssets();
        cctvList = new ArrayList<double[]>();
        policeList = new ArrayList<String[]>();
    }

    public void getCCTVData(){
        try {
            is = new InputStreamReader(assetmanager.open("cctv.csv"));
            reader = new BufferedReader(is);

            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                double[] dataDouble = {Double.valueOf(data[0]), Double.valueOf(data[1])};
                cctvList.add(dataDouble);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPoliceData(){
        try {
            is = new InputStreamReader(assetmanager.open("police.csv"), "UTF-16LE");
            reader = new BufferedReader(is);

            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String[] dataString = {data[0], data[1], data[2]};
                policeList.add(dataString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<double[]> getCctvList() {
        return cctvList;
    }

    public ArrayList<String[]> getPoliceList() {
        return policeList;
    }
}
