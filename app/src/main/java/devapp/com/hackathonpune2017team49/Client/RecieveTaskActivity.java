package devapp.com.hackathonpune2017team49.Client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public static String employeeID;
    TaskHelper helper;
    TaskHelper notHelping;
    public static boolean isClient = true;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.utils, menu);
        if (isClient){
            return true;

        }else {
         return false;
        }
     }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.utils:
                startActivity(new Intent(RecieveTaskActivity.this , SelectUtilActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);

    }


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_task);

         helper = new TaskHelper();


        String user = getIntent().getStringExtra("User");
        if(user.equals("Client")){
            isClient = true;
            tvEid = (TextView) findViewById(R.id.tvEid);

            RealmConfiguration configuration = new RealmConfiguration.Builder(RecieveTaskActivity.this).deleteRealmIfMigrationNeeded().schemaVersion(4).build();
            Realm.setDefaultConfiguration(configuration);
            Log.d(TAG , "Realm set");
            realm = Realm.getDefaultInstance();
            database = realm.where(EmpDatabase.class).findFirst();
            employeeID = database.getEid();
            tvEid.setText(employeeID);

        }else {
            employeeID = getIntent().getStringExtra("EID");
             isClient = false;
        }


        initializeFirebase();
        loadRecyclerView();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             Iterable<DataSnapshot> iterable =  dataSnapshot.child("Clients").child(employeeID).child("Tasks").getChildren();



                for(DataSnapshot d : iterable){

                    try {
                        Log.d(TAG, "onDataChange: address is " + d.child("Address").getValue());

                        notHelping = new TaskHelper();
                        notHelping.setLongitude(d.child("Longitude").getValue().toString());
                        notHelping.setName(d.child("Name").getValue().toString());
                        notHelping.setTime(d.child("time").getValue().toString());
                        notHelping.setLatitude(d.child("Latitude").getValue().toString());
                        notHelping.setPlace(d.child("Address").getValue().toString());
                        notHelping.setTaskID(d.getKey());
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
