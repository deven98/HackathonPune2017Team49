package devapp.com.hackathonpune2017team49.Client;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import devapp.com.hackathonpune2017team49.Manager.ManagerActivity;
import devapp.com.hackathonpune2017team49.Manager.ManagerRecyclerAdapter;
import devapp.com.hackathonpune2017team49.R;

public class RecieveTaskActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    RecyclerView recyclerManager;
    RecyclerView.LayoutManager layoutManager;
    Context context = RecieveTaskActivity.this;
    TaskRecyclerAdapter adapter;

    ArrayList<TaskHelper> helpers= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_task);






 // data to be fed here to the arraylist
        recyclerManager = (RecyclerView) findViewById(R.id.recyclertasks);
        layoutManager = new LinearLayoutManager(context );
        recyclerManager.setLayoutManager(layoutManager);
        adapter = new TaskRecyclerAdapter(helpers, context);
        recyclerManager.setAdapter(adapter);
        adapter.notifyDataSetChanged();




    }
}
