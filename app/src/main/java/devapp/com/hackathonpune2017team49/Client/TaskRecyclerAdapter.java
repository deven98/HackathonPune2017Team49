package devapp.com.hackathonpune2017team49.Client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.tvName.setText(helpers.get(position).getName());
        holder.tvDetails.setText(helpers.get(position).getDetails());
        holder.tvTime.setText(helpers.get(position).getTime());
       // holder.tvLocation.setText(helpers.get(position).getPlace().toString());
        holder.tvDetails.setText(helpers.get(position).getLatitude()+" "+helpers.get(position).getLongitude());



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
        Button btnMaps;
        public MyHolder(View itemView) {
            super(itemView);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLoc);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnMaps = (Button) itemView.findViewById(R.id.btnMap);

        }
    }

}
