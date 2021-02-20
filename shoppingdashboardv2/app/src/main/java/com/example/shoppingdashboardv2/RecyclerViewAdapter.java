package com.example.shoppingdashboardv2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.DateFormat;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Task> mTasks = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Task> mTasks, Context mContext) {
        this.mTasks = mTasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflates the view

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listtasks, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called here");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String nameText = "Name: " + mTasks.get(position).getName();
        String locationText = "Destination: " + mTasks.get(position).getDestination();
        String startText = "Start time: " + dateFormat.format(mTasks.get(position).getStart());
        String endText = "End time: " + dateFormat.format(mTasks.get(position).getFinish());
        String numpeopleText = "Max Count: " + mTasks.get(position).getMax_orders();

        holder.task_nameTV.setText(nameText);
        holder.task_locationTV.setText(locationText); // this could throw an error...
        holder.task_starttimeTV.setText(startText);
        holder.task_endtimeTV.setText(endText);
        holder.task_numpeopleTV.setText(numpeopleText);

        holder.listtasks_rv_ConLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mTasks.get(position).getName());

                // this is where we would go to the message or more descriptive part
                Toast.makeText(mContext, mTasks.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView task_nameTV;
        TextView task_locationTV;
        TextView task_starttimeTV;
        TextView task_endtimeTV;
        TextView task_numpeopleTV;

        ConstraintLayout listtasks_rv_ConLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task_nameTV = itemView.findViewById(R.id.task_nameTV);
            task_locationTV = itemView.findViewById(R.id.task_locationTV);
            task_starttimeTV = itemView.findViewById(R.id.task_starttime_TV);
            task_endtimeTV = itemView.findViewById(R.id.task_endtime_TV);
            task_numpeopleTV = itemView.findViewById(R.id.task_numpeople_TV);

            listtasks_rv_ConLay = itemView.findViewById(R.id.listtasks_rv_ConLay);
        }
    }
}
