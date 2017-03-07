package com.whitehedge.firebasedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Mahesh Chakkarwar on 28-02-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private LinkedList<User> list;

    public ListAdapter(Context context, LinkedList<User> list) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewFirstName.setText(list.get(position).getFirstName());
        holder.textViewMiddleName.setText(list.get(position).getMiddleName());
        holder.textViewLastName.setText(list.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFirstName;
        private TextView textViewMiddleName;
        private TextView textViewLastName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFirstName = (TextView) itemView.findViewById(R.id.textViewFirstName);
            textViewMiddleName = (TextView) itemView.findViewById(R.id.textViewMiddleName);
            textViewLastName = (TextView) itemView.findViewById(R.id.textViewLastName);

        }
    }
}
