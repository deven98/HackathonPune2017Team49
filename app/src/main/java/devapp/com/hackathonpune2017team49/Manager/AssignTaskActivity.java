package devapp.com.hackathonpune2017team49.Manager;

import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    EditText nameEditText;
    EditText phoneEditText;
    TextView addressTextView;
    Button submitButton;
    EditText IDEditText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static String SELECTED_CLIENT = "00000";

    void initialize(){

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        addressTextView = (TextView) findViewById(R.id.address_text_view);
        submitButton = (Button) findViewById(R.id.button_submit);
        IDEditText = (EditText) findViewById(R.id.id_edit_text);
        phoneEditText = (EditText) findViewById(R.id.phone_edit_text);
    }

    void initializeFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    void setOnClickListeners(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time = Calendar.getInstance().getTime().toString();
                specifics = nameEditText.getText().toString();
                phoneNumber = phoneEditText.getText().toString();
                taskID = IDEditText.getText().toString();

                Toast.makeText(AssignTaskActivity.this, time + specifics + phoneNumber + placeName , Toast.LENGTH_SHORT).show();

                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Latitude").setValue(latitude);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Longitude").setValue(longitude);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Name").setValue(specifics);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("Address").setValue(placeName);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("time").setValue(time);
                databaseReference.child("Clients").child(SELECTED_CLIENT).child("Tasks").child(taskID).child("isCompleted").setValue("0");
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

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });
    }
}
