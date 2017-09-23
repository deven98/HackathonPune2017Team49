package devapp.com.hackathonpune2017team49.Client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import devapp.com.hackathonpune2017team49.Manager.AssignTaskActivity;
import devapp.com.hackathonpune2017team49.Manager.ManagerRecyclerAdapter;
import devapp.com.hackathonpune2017team49.R;

/**
 * Created by root on 23/9/17.
 */

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.MyHolder> {


    private static final String TAG = "Test";
    ArrayList<TaskHelper> helpers;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    boolean isTaskAlive = false;

    double lat = 18.5831363;
    double lon = 73.7419016;

    public TaskRecyclerAdapter(ArrayList<TaskHelper> helpers, Context context) {
        this.helpers = helpers;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_tasks, parent , false);
        return new TaskRecyclerAdapter.MyHolder( v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        holder.tvName.setText(helpers.get(position).getName());
        holder.tvDetails.setText(helpers.get(position).getDetails());
        holder.tvTime.setText(helpers.get(position).getTime());
       // holder.tvLocation.setText(helpers.get(position).getPlace().toString());
        holder.tvDetails.setText(helpers.get(position).getTaskID());

        if (!RecieveTaskActivity.isClient){

            holder.btnStartTask.setText("View Progress");

        }else {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String isTaskStarted = (String) dataSnapshot.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("isTaskStarted").getValue();

                    if (!isTaskStarted.equals("0")){

                        holder.btnStartTask.setText("End Task");
                        isTaskAlive = true;
                    }else {
                        isTaskAlive = false;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        holder.btnStartTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RecieveTaskActivity.isClient) {

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String isTaskStarted = (String) dataSnapshot.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("isTaskStarted").getValue();

                            if (isTaskStarted.equals("0")){
                                Toast.makeText(context , "Task not yet started ...", Toast.LENGTH_SHORT).show();
                            }else {
                               String lat = (String) dataSnapshot.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("CurrentLat").getValue();
                               String longitude = (String) dataSnapshot.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("CurrentLong").getValue();

                                Intent intent = new Intent(context , MapsActivity.class);
                                intent.putExtra("Longitude" , longitude);
                                intent.putExtra("Latitude" , lat);
                                context.startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }else {

                    if (isTaskAlive){
                        // end the task
                        Toast.makeText(context , "Task ended..." , Toast.LENGTH_SHORT).show();


                        holder.btnStartTask.setVisibility(View.GONE);

                        databaseReference.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("isTaskStarted").setValue("0");
                    }else {

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("isTaskStarted").setValue("1");
                        databaseReference.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("CurrentLat").setValue("18.5831363");
                        databaseReference.child("Clients").child(RecieveTaskActivity.employeeID).child("Tasks").child(helpers.get(position).getTaskID()).child("CurrentLong").setValue("73.7419016");

                    }


                }




            }
        });


        holder.btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // take the latLang
                Intent intent = new Intent(context , MapsActivity.class);
                intent.putExtra("Longitude" , helpers.get(position).getLongitude());
                intent.putExtra("Latitude" , helpers.get(position).getLatitude());
                context.startActivity(intent);
                // start maps activity here
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpers.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tvLocation , tvTime , tvDetails, tvName;
        Button btnMaps , btnStartTask;
        public MyHolder(View itemView) {
            super(itemView);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLoc);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnMaps = (Button) itemView.findViewById(R.id.btnMap);
            btnStartTask = (Button) itemView.findViewById(R.id.btnStartTask);
        }
    }

}
