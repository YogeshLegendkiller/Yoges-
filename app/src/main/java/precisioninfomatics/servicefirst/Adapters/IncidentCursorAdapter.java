package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.PartModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 30-01-2017.
 */

public class IncidentCursorAdapter  extends CursorRecyclerViewAdapter<IncidentCursorAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    public IncidentCursorAdapter(Cursor cursor) {
        super( cursor);

    }
    public void setOnItemClickListener( OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView incidentcode, problemcategory, status, companyname, created_at,problemdescription;
        public MyViewHolder( View itemView) {
            super(itemView);
             companyname = (TextView) itemView.findViewById(R.id.companyname);
            incidentcode = (TextView) itemView.findViewById(R.id.incidentcode);
            problemcategory = (TextView) itemView.findViewById(R.id.problemcategory);
            problemdescription=(TextView)itemView.findViewById(R.id.problemdescription);
            status = (TextView) itemView.findViewById(R.id.status);
            created_at = (TextView) itemView.findViewById(R.id.created_date);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incidentrecyclerview, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, Cursor cursor) {
        String incidentCode = cursor.getString(cursor.getColumnIndex(IncidentModel.inc_incCode));
        holder.incidentcode.setText(incidentCode);
        holder.problemcategory.setText(cursor.getString(cursor.getColumnIndex(IncidentModel.inc_probcategory)));
        String status=cursor.getString(cursor.getColumnIndex(IncidentModel.inc_status));
        holder.status.setText(status);
        holder.problemdescription.setText(cursor.getString(cursor.getColumnIndex(IncidentModel.probdescription)));
        //ViewDateTimeFormat viewDateTimeFormat=new ViewDateTimeFormat();
        String createdat=ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(IncidentModel.inc_cd)));
        holder.created_at.setText(createdat);
        holder.companyname.setText(cursor.getString(cursor.getColumnIndex(IncidentModel.inc_companyname)));
    }


    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
             Cursor cursor = this.getItem(position);
            this.onItemClickListener.onItemClicked(cursor);
        }
    }

}