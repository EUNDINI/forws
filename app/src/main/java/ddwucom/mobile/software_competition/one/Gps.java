package ddwucom.mobile.software_competition.one;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Gps {
    private  Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private  double longitude;
    final static int REQUEST_CODE_LOCATION = 100;

    public Gps(Context context) {
        this.context = context;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getGps() {
        settingGPS();
        Location userLocation = getMyLocation();

        if (userLocation != null) {
            latitude = userLocation.getLatitude();
            longitude = userLocation.getLongitude();

            Geocoder gcK = new Geocoder(context, Locale.KOREA);
            try {
                List<Address> addresses = gcK.getFromLocation(latitude, longitude, 1);
                if (null != addresses && addresses.size() > 0) {
                    Address addr = addresses.get(0);
                    String res = addr.getAddressLine(0);
                    return res;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public Location getMyLocation() {
        Location currentLocation = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLongitude();
                double lat = currentLocation.getLatitude();
            }
        }
        return currentLocation;
    }

    private void settingGPS() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
               latitude = location.getLatitude();
               longitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }

    boolean canReadLocation = false;

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Location userLocation = getMyLocation();
                if (userLocation != null) {
                    latitude = userLocation.getLatitude();
                    longitude = userLocation.getLongitude();
                }
                canReadLocation = true;
            } else {
                canReadLocation = false;
            }
        }
    }

}