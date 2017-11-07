package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.CustomerBranchModel;
import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 21/06/2017.
 */

public class CustomerBranchIncidentViewAdapter   extends CursorRecyclerViewAdapter<CustomerBranchIncidentViewAdapter.MyViewHolder> implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    public CustomerBranchIncidentViewAdapter(Cursor cursor) {
        super( cursor);

    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView customerbranchname;
        ImageView print;
        public MyViewHolder(final View itemView) {
            super(itemView);
            this.customerbranchname = (TextView) itemView.findViewById(R.id.branchname);
            this.print=(ImageView)itemView.findViewById(R.id.print);
        }
    }

    @Override
    public CustomerBranchIncidentViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerbranchviewlayout, parent, false);

        CustomerBranchIncidentViewAdapter.MyViewHolder myViewHolder = new CustomerBranchIncidentViewAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerBranchIncidentViewAdapter.MyViewHolder holder, Cursor cursor) {
       holder.customerbranchname.setText(cursor.getString(cursor.getColumnIndex(CustomerBranchModel.CustomerBranchname)));

    }


    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            final Cursor cursor = this.getItem(position);
            this.onItemClickListener.onItemClicked(cursor);
        }


    }

}



