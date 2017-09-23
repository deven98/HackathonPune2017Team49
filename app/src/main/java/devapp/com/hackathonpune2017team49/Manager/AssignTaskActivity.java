package devapp.com.hackathonpune2017team49.Manager;

import java.util.ArrayList;
import java.util.Calendar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import devapp.com.hackathonpune2017team49.DecidingActivity;
import devapp.com.hackathonpune2017team49.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AssignTaskActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    String placeName;
    String specifics;
    String phoneNumber;
    String time;
    String taskID;
    String latitude;
    String longitude;

    double currentLatitude;
    double currentLongitude;
    double[][] adjencencyMatrix;
    int count=0;

    EditText nameEditText;
    EditText phoneEditText;
    TextView addressTextView;
    Button submitButton;
    EditText IDEditText;

    ArrayList<Distance> listOfdistances = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    LocationManager manager;
    LocationListener listener;

    public static String SELECTED_CLIENT = "00000";

    void initialize() {

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        addressTextView = (TextView) findViewById(R.id.address_text_view);
        submitButton = (Button) findViewById(R.id.button_submit);
        IDEditText = (EditText) findViewById(R.id.id_edit_text);
        phoneEditText = (EditText) findViewById(R.id.phone_edit_text);
    }

    void initializeFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



       listener = new LocationListener() {
           @Override
           public void onLocationChanged(Location location) {
               currentLatitude = location.getLatitude();
               currentLongitude = location.getLongitude();
               Distance distance = new Distance();
               distance.setLang(currentLongitude);
               distance.setLat(currentLatitude);
               //listOfdistances.add(distance);
               Log.d(TAG, "onLocationChanged: current location added....");
           }

           @Override
           public void onStatusChanged(String provider, int status, Bundle extras) {

           }

           @Override
           public void onProviderEnabled(String provider) {

           }

           @Override
           public void onProviderDisabled(String provider) {

           }
       };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
     }

    void startProcessing(){

        count = listOfdistances.size();
        Log.d(TAG, "onClick: submit clicked... count value is "+count);


        adjencencyMatrix = new double[count][count];
        for (int i=0; i<count ; i++){
            for (int j=0 ; j<count ; j++){
                Log.d(TAG, "startProcessing: in loop "+i +" , "+j);

                adjencencyMatrix[i][j] = distance(listOfdistances.get(i).getLat() , listOfdistances.get(i).getLang(),
                        listOfdistances.get(j).getLat() , listOfdistances.get(j).getLang());


                if (i==j){
                    // make that element 0
                    adjencencyMatrix[i][j] =0;

                    Log.d(TAG, "startProcessing: making "+i +j + " 0");
                }
            }
        }
        Log.d(TAG, "startProcessing: see the distances");
        for (int i=0; i<count ; i++){
            for (int j=0 ; j<count ; j++){
                Log.d(TAG, "startProcessing: "+adjencencyMatrix[i][j]);
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
       result =  TSP.getMinDistance(adjencencyMatrix , count);
        for(int i : result){
            Log.d(TAG, "startProcessing: node visited is "+result.get(i));
        }


    }

    void setOnClickListeners(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                time = Calendar.getInstance().getTime().toString();
                specifics = nameEditText.getText().toString();
                phoneNumber = phoneEditText.getText().toString();
                taskID = IDEditText.getText().toString();


                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Latitude").setValue(latitude);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Longitude").setValue(longitude);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Name").setValue(specifics);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Address").setValue(placeName);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("time").setValue(time);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("isCompleted").setValue("0");
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("isTaskStarted").setValue("0");
                finish();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);

        SELECTED_CLIENT =  getIntent().getStringExtra("clientID");


        initialize();
        setOnClickListeners();
        initializeFirebase();
        Distance dis = new Distance();
        dis.setLat(18.5831363);
        dis.setLang(73.7419016);
        listOfdistances.add(dis);
  //      manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 20 , 0 ,  listener);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place);
                addressTextView.setText(place.getName() + "," + place.getAddress());

                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);

                placeName = place.getName().toString() + place.getAddress().toString();

               //double dis =  distance(place.getLatLng().latitude , place.getLatLng().longitude , 23 , 24);

              Distance distance = new Distance();
                distance.setLang(place.getLatLng().longitude);
                distance.setLang(place.getLatLng().latitude);
                listOfdistances.add(distance);
                Log.d(TAG, "onPlaceSelected: location added in list");

               // Log.d(TAG, "onPlaceSelected: distance is "+dis);

            }









            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });
    }
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



    public class Distance{

        double lat;
        double lang;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLang() {
            return lang;
        }

        public void setLang(double lang) {
            this.lang = lang;
        }
    }

}
