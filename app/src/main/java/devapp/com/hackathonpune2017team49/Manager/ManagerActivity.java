package devapp.com.hackathonpune2017team49.Manager;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import devapp.com.hackathonpune2017team49.EmpDatabase;
import devapp.com.hackathonpune2017team49.R;
import io.realm.Realm;

public class ManagerActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    static String eid;
    Realm realm;
    EmpDatabase database;
    ArrayList<String> empList ;
    RecyclerView recyclerManager;
    RecyclerView.LayoutManager layoutManager;
    Context context = ManagerActivity.this;
    ManagerRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        empList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        database = realm.where(EmpDatabase.class).findFirst();
        if (database != null){
            eid = database.getEid();
        }else {
            Log.d(TAG, "onCreate: Realm is null");
        }



        empList = new ArrayList<>();


        recyclerManager = (RecyclerView) findViewById(R.id.recyclermanager);
        layoutManager = new LinearLayoutManager(context );

        recyclerManager.setLayoutManager(layoutManager);
        adapter = new ManagerRecyclerAdapter(empList , context);
        recyclerManager.setAdapter(adapter);
        adapter.notifyDataSetChanged();





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addEmp :

                final Dialog dialog = new Dialog(ManagerActivity.this);
                dialog.setContentView(R.layout.dialog_add_emp);
                dialog.setCancelable(true);
                Button button = (Button) dialog.findViewById(R.id.save);
                dialog.show();
                final EditText etEmpId = (EditText) dialog.findViewById(R.id.et_addEmp);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        empList.add(etEmpId.getText().toString());
                        Log.d(TAG, "onClick: emp added");
                        //adapter = new ManagerRecyclerAdapter(empList , context);
                        recyclerManager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        dialog.dismiss();




                    }
                });

                break;
        }


        return super.onOptionsItemSelected(item);


    }
    }
