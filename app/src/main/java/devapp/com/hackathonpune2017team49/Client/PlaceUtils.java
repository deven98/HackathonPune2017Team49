package devapp.com.hackathonpune2017team49.Client;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by HP on 23-09-2017.
 */

public class PlaceUtils {

    public static String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";

    public static String LATITUDE;
    public static String LONGITUDE;
    public static String TYPE;
    public static final String API_KEY = "AIzaSyDkLql3STa_sMdy41PnmH47aq2Shgf6rL4";

    public static URL buildUrl(String Latitude, String Longitude, String type) {

        LATITUDE = Latitude;
        LONGITUDE = Longitude;
        TYPE = type;

        URL url = null;
        try {
            url = new URL(BASE_URL + LATITUDE + "," + LONGITUDE + "&radius=500&type=" + TYPE + "&key=" + API_KEY);
            Log.d("TAG",url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void parseJSON(String in,GoogleMap mMap){

        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(in);
            JSONArray results = jsonObject.getJSONArray("results");

            for(int i = 0; i<results.length(); i++){
                Log.d("JSON",results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                UtilMapActivity.latlngs.add(new LatLng(Double.parseDouble(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat")),Double.parseDouble(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"))));
                mMap.addMarker(new MarkerOptions().position(UtilMapActivity.latlngs.get(i)).title("Place"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
