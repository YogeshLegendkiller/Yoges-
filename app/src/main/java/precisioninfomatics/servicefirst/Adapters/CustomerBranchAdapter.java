package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.CustomerListModel;
import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 16/06/2017.
 */

public class CustomerBranchAdapter  extends CursorRecyclerViewAdapter<CustomerBranchAdapter.MyViewHolder> implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;

    public CustomerBranchAdapter( Cursor cursor) {
        super(cursor);


    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerbranchname;
         public MyViewHolder(final View itemView) {
            super(itemView);
            this.customerbranchname = (TextView) itemView.findViewById(R.id.branchname);
          }
    }

    @Override
    public CustomerBranchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerbranchlayout, parent, false);

        CustomerBranchAdapter.MyViewHolder myViewHolder = new CustomerBranchAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerBranchAdapter.MyViewHolder holder, Cursor cursor) {
        holder.customerbranchname.setText(cursor.getString(cursor.getColumnIndex(CustomerListModel.customerbranchname)));

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


