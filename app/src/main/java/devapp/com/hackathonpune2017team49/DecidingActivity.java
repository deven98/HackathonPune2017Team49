package devapp.com.hackathonpune2017team49;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import devapp.com.hackathonpune2017team49.Manager.ManagerActivity;
import io.realm.Realm;

public class DecidingActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    RadioButton clientRadioButton;
    RadioButton managerRadioButton;

    Button signInButton;

    Realm realm;

    boolean isManager = false;
    boolean isClient = false;

    EditText etEid;

    EmpDatabase database;

    void initialize(){

        clientRadioButton = (RadioButton) findViewById(R.id.radioButton_client);
        managerRadioButton = (RadioButton) findViewById(R.id.radioButton_manager);
        signInButton = (Button) findViewById(R.id.button_sign_in);
        etEid = (EditText) findViewById(R.id.etEid);
        realm = Realm.getDefaultInstance();

    }

    void setOnClickListeners(){

        clientRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClient = true;
                isManager = false;
                managerRadioButton.setChecked(false);
            }
        });

        managerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isManager = true;
                isClient = false;
                clientRadioButton.setChecked(false);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isManager){
                    //Intent for manager

                    startActivity(new Intent(getApplicationContext() , ManagerActivity.class));


                    Toast.makeText(DecidingActivity.this, "Manager!", Toast.LENGTH_SHORT).show();
                }else if(isClient){
                    //Intent for is client
                    startActivity(new Intent(getApplicationContext() , ManagerActivity.class));

                    Toast.makeText(DecidingActivity.this, "Client!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DecidingActivity.this, "At least one option should be chosen.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deciding);

        initialize();
        setOnClickListeners();

        database = realm.where(EmpDatabase.class).findFirst();


        if (database != null){

            realm.beginTransaction();
            database.setClient(isClient);
            database.setManager(isManager);
            database.setEid(etEid.getText().toString());
            realm.commitTransaction();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(database);
                }
            });

        }else {
            Log.d(TAG, "onCreate: realm is null");
            database = new EmpDatabase();
            database.setClient(isClient);
            database.setManager(isManager);
            database.setEid(etEid.getText().toString());
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(database);
                    Log.d(TAG, "execute: realm stuff done");
                }
            });
        }


    }


}
