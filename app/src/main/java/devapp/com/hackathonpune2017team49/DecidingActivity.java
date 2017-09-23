package devapp.com.hackathonpune2017team49;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class DecidingActivity extends AppCompatActivity {

    Realm realm;
    EmpDatabase database ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        database = realm.where(EmpDatabase.class).findFirst();







    }
}
