package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 10-02-2017.
 */

public class SpareAdapter  extends CursorRecyclerViewAdapter<SpareAdapter.MyViewHolder>  {

    public SpareAdapter( Cursor cursor) {
        super( cursor);

    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partno,advancereturn,samepart,partstatus,issueapproval;


        public MyViewHolder(final View itemView) {
            super(itemView);
            this.partno = (TextView) itemView.findViewById(R.id.partno);
            this.advancereturn=(TextView)itemView.findViewById(R.id.advancereturn);
            this.samepart=(TextView)itemView.findViewById(R.id.samepart);
            this.partstatus=(TextView)itemView.findViewById(R.id.partstatus);
            this.issueapproval=(TextView)itemView.findViewById(R.id.issueapproval);
        }
    }
    @Override
    public SpareAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spareadapter, parent, false);

        return new   SpareAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SpareAdapter.MyViewHolder holder, Cursor cursor) {
        //   String partno=cursor.getString(cursor.getColumnIndex(PartModel.partno));
        // holder.brand.setText(cursor.getString(cursor.getColumnIndex(PartModel.brand)));
        //holder.model.setText(cursor.getString(cursor.getColumnIndex(PartModel.model)));
        holder.partno.setText(cursor.getString(cursor.getColumnIndex(SpareModel.partno)));
        holder.issueapproval.setText("No");
        int advancereturn=cursor.getInt(cursor.getColumnIndex(SpareModel.advreturn));
        if(advancereturn==1){
            holder.advancereturn.setText("Yes");
        }
        else {
            holder.advancereturn.setText("No");
        }
      int samepart=cursor.getInt(cursor.getColumnIndex(SpareModel.samepart));
        if(samepart==1){
            holder.samepart.setText("Yes");
        }
        else {
            holder.samepart.setText("No");
        }
        holder.partstatus.setText(cursor.getString(cursor.getColumnIndex(SpareModel.partstatus)));
    }
}

