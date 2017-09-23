package devapp.com.hackathonpune2017team49.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import devapp.com.hackathonpune2017team49.R;

public class SelectUtilActivity extends AppCompatActivity {

    Button atmButton;
    Button resButton;
    Button petrolButton;

    public static final String UTIL_RESTAURANT = "restaurant";
    public static final String UTIL_ATM = "atm";
    public static final String UTIL_GAS= "gas_station";

    void initialize(){

        atmButton = (Button) findViewById(R.id.atm_button);
        resButton = (Button) findViewById(R.id.restaurant_button);
        petrolButton = (Button) findViewById(R.id.wc_button);

    }

    void setOnClickListeners(){

        atmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),UtilMapActivity.class);
                in.putExtra("Utility",UTIL_ATM);
                startActivity(in);
            }
        });

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),UtilMapActivity.class);
                in.putExtra("Utility",UTIL_RESTAURANT);
                startActivity(in);
            }
        });

        petrolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),UtilMapActivity.class);
                in.putExtra("Utility",UTIL_GAS);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_util);

        initialize();

        setOnClickListeners();

    }
}
