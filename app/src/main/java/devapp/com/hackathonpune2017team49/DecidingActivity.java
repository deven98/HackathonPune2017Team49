package devapp.com.hackathonpune2017team49;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class DecidingActivity extends AppCompatActivity {

    RadioButton clientRadioButton;
    RadioButton managerRadioButton;

    Button signInButton;

    boolean isManager = false;
    boolean isClient = false;

    void initialize(){

        clientRadioButton = (RadioButton) findViewById(R.id.radioButton_client);
        managerRadioButton = (RadioButton) findViewById(R.id.radioButton_manager);
        signInButton = (Button) findViewById(R.id.button_sign_in);

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
                    Toast.makeText(DecidingActivity.this, "Manager!", Toast.LENGTH_SHORT).show();
                }else if(isClient){
                    //Intent for is client
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

    }
}
