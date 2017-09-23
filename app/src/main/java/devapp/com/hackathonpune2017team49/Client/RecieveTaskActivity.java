package devapp.com.hackathonpune2017team49.Client;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import devapp.com.hackathonpune2017team49.EmpDatabase;
import devapp.com.hackathonpune2017team49.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RecieveTaskActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    RecyclerView recyclerManager;
    RecyclerView.LayoutManager layoutManager;
    Context context = RecieveTaskActivity.this;
    TaskRecyclerAdapter adapter;

    ArrayList<TaskHelper> helpers= new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView tvEid;
    Realm realm;
    EmpDatabase database;

    String employeeID;
    TaskHelper helper;
    TaskHelper notHelping;

    void initializeFirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void loadRecyclerView(){
        recyclerManager = (RecyclerView) findViewById(R.id.recyclertasks);
        layoutManager = new LinearLayoutManager(context );
        recyclerManager.setLayoutManager(layoutManager);
        adapter = new TaskRecyclerAdapter(helpers, context);
        recyclerManager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_task);

         helper = new TaskHelper();

        tvEid = (TextView) findViewById(R.id.tvEid);

        RealmConfiguration configuration = new RealmConfiguration.Builder(RecieveTaskActivity.this).deleteRealmIfMigrationNeeded().schemaVersion(4).build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG , "Realm set");
        realm = Realm.getDefaultInstance();
        database = realm.where(EmpDatabase.class).findFirst();
        employeeID = database.getEid();
        tvEid.setText(employeeID);

        initializeFirebase();
        loadRecyclerView();

        helper.setName("Shubham");
        helper.setLongitude("78");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             Iterable<DataSnapshot> iterable =  dataSnapshot.child("Clients").child(employeeID).child("Tasks").getChildren();



                for(DataSnapshot d : iterable){

                    try {
                        Log.d(TAG, "onDataChange: address is " + d.child("Address").getValue());

                        Toast.makeText(context, d.toString(), Toast.LENGTH_SHORT).show();
                        notHelping = new TaskHelper();
                        notHelping.setLongitude(d.child("Longitude").getValue().toString());
                        notHelping.setName(d.child("Name").getValue().toString());
                        notHelping.setTime(d.child("time").getValue().toString());
                        notHelping.setLatitude(d.child("Latitude").getValue().toString());
                        notHelping.setPlace(d.child("Address").getValue().toString());
                       helpers.add(notHelping);
                      loadRecyclerView();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

              }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // data to be fed here to the arraylist




    }
}
