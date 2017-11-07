package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import java.io.File;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 03/05/2017.
 */

public class VisitViewCursorAdapter extends CursorRecyclerViewAdapter<VisitViewCursorAdapter.VisitViewHolder>   {
private Context mContext;
    public VisitViewCursorAdapter(Context mContext, Cursor cursor) {
        super( cursor);
        this.mContext = mContext;

    }

    @Override
    public VisitViewCursorAdapter.VisitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitview, parent, false);
        return new VisitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VisitViewCursorAdapter.VisitViewHolder holder, Cursor cursor) {
        holder.CheckIn.setText(ViewDateTimeFormat.DateView(cursor.getString(cursor.getColumnIndex(VisitModel.checkin)),1));
        holder.CheckOut.setText(ViewDateTimeFormat.DateView(cursor.getString(cursor.getColumnIndex(VisitModel.checkout)),1));
        final String DocumentPath=cursor.getString(cursor.getColumnIndex(VisitModel.docpath));
        holder.ActionTaken.setText(cursor.getString(cursor.getColumnIndex(VisitModel.actiontaken)));
        holder.FindingsAtSite.setText(cursor.getString(cursor.getColumnIndex(VisitModel.findsatsite)));
        //   holder.Observation.setText("-");
        holder.CallSlipno.setTextColor(Color.parseColor("#ff7e00"));
        String nextvisitdate=cursor.getString(cursor.getColumnIndex(VisitModel.nextvisitdate));
        String cutoffdate=cursor.getString(cursor.getColumnIndex(VisitModel.cutofdate));
        if(nextvisitdate!=null&&!nextvisitdate.contentEquals("null")){
            holder.NextVisitDate.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy",nextvisitdate));
        }
        else {
            holder.NextVisitDate.setText("-");
        }
//        Log.d("next",nextvisitdate);
        if(cutoffdate!=null&&!cutoffdate.contentEquals("null")){
            holder.CutOffDate.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy",cutoffdate));
        }
        else {
            holder.CutOffDate.setText("-");
        }
        String pendingclassification=cursor.getString(cursor.getColumnIndex(VisitModel.pendingclas));
        if(pendingclassification!=null&&!pendingclassification.contentEquals("null")){
            holder.pendingclassification.setText(pendingclassification);

        }
        else {
            holder.pendingclassification.setText("-");

        }
        holder.visitstatus.setText(cursor.getString(cursor.getColumnIndex(VisitModel.status)));
        if(cursor.getString(cursor.getColumnIndex(VisitModel.calslipno))!=null&&!cursor.getString(cursor.getColumnIndex(VisitModel.calslipno)).equals("null")){
            holder.CallSlipno.setText(cursor.getString(cursor.getColumnIndex(VisitModel.calslipno)));

        }
        else {
            holder.CallSlipno.setText("-");
        }
        String slip=cursor.getString(cursor.getColumnIndex(VisitModel.docname));
        if(slip!=null){
            holder.Slip.setText(slip);
            holder.Slip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File file=new File(DocumentPath);
                    String mime = getMimeType(DocumentPath);
                    //    Log.d("type",type);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), mime);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
        else {
            holder.Slip.setText("-");
        }

       /// holder.CutOffDate.setText("-");
        String remarks=cursor.getString(cursor.getColumnIndex(VisitModel.remarks));
        if(!remarks.contentEquals("null")){
        holder.Remarks.setText(remarks);
        }
        else {
            holder.Remarks.setText("-");
        }

       /* holder.Createdeby.setText(listobj.get(position).getCreatedby());
        holder.Lastmodifiedby.setText(listobj.get(position).getLastmodifiedby());
        holder.Createdat.setText(listobj.get(position).getCreatedat());
        holder.LastModifiedat.setText(listobj.get(position).getLastModifiedat());
   */
          }
    private String getMimeType(String url) {

        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(url);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension);

    }
    static class VisitViewHolder extends RecyclerView.ViewHolder {
        TextView  CheckIn,
                CheckOut,
                ActionTaken,
                FindingsAtSite,
                CallSlipno ,
                NextVisitDate,
                CutOffDate,
                Remarks,
                visitstatus,
                pendingclassification,
                Slip;


        private VisitViewHolder(View itemView) {
            super(itemView);
            this.CheckIn=(TextView)itemView.findViewById(R.id.checkin);
            this.CheckOut=(TextView)itemView.findViewById(R.id.checkout);
            this.ActionTaken=(TextView)itemView.findViewById(R.id.actiontaken);
            this.FindingsAtSite=(TextView)itemView.findViewById(R.id.findingatsite);
            this.CallSlipno=(TextView)itemView.findViewById(R.id.callslipno);
            this.NextVisitDate=(TextView)itemView.findViewById(R.id.nextvisitdate);
            this.CutOffDate=(TextView)itemView.findViewById(R.id.cutoffdate);
            this.Remarks=(TextView)itemView.findViewById(R.id.remarks);
            this.visitstatus=(TextView)itemView.findViewById(R.id.visitstatus);
            this.Slip=(TextView)itemView.findViewById(R.id.slip);
            this.pendingclassification=(TextView)itemView.findViewById(R.id.pendingclassification);
        }
    }
}

