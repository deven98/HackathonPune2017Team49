package devapp.com.hackathonpune2017team49.Manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import devapp.com.hackathonpune2017team49.R;

/**
 * Created by root on 23/9/17.
 */

public class ManagerRecyclerAdapter extends RecyclerView.Adapter<ManagerRecyclerAdapter.MyHolder>  {

    private static final String TAG = "Test";
    ArrayList<String> eids;
    Context context;

    public ManagerRecyclerAdapter(ArrayList<String> eids, Context context) {
        this.eids = eids;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.emplayout, parent , false);
        return new MyHolder( v);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {


        holder.btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , AssignTaskActivity.class);
                intent.putExtra("clientID" , eids.get(position));
                context.startActivity(intent);
            }
        });

        Log.d(TAG, "onBindViewHolder: position is "+position);
        holder.tvEid.setText(eids.get(position));
        Log.d(TAG, "onBindViewHolder: array list gave "+eids.get(position));

    }

    @Override
    public int getItemCount() {
        return eids.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tvEid;
        Button btnAddTask;
        public MyHolder(View itemView) {
            super(itemView);
            tvEid = (TextView) itemView.findViewById(R.id.tvEmpid);
            btnAddTask = (Button) itemView.findViewById(R.id.empButton);
        }
    }
}
