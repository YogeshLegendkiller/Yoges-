package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 16-02-2017.
 */

public class VisitListAdapter extends  CursorRecyclerViewAdapter<VisitListAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    private Context context;
    public VisitListAdapter(Context mContext, Cursor cursor) {
        super( cursor);
           this.context=mContext;
        }


public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        }


public static class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView startdate,status,fromloc,toloc,duration,distance,error_msg;
    TextView enddate,date,findingsatsite,at;
    TimelineView timelineView;
    SeekBar seekBar2,seekBar1,seekBar3;
    RelativeLayout visit_layout,visittexts,seebar,DurationLayout,error_layout;
     public MyViewHolder(final View itemView,int viewtype) {
        super(itemView);
         this.status = itemView.findViewById(R.id.status);
         this.distance= itemView.findViewById(R.id.distance);
         this.error_msg= itemView.findViewById(R.id.error_msg);
         this.date= itemView.findViewById(R.id.date);
         this.startdate= itemView.findViewById(R.id.starttime);
         this.distance= itemView.findViewById(R.id.distance);
         this.error_layout= itemView.findViewById(R.id.error_layout);
         this.duration= itemView.findViewById(R.id.duration);
         this.visittexts= itemView.findViewById(R.id.rl3);
         this.seebar= itemView.findViewById(R.id.rl9);
         this.findingsatsite= itemView.findViewById(R.id.findingatsite_text);
         this.at= itemView.findViewById(R.id.at);
         this.fromloc= itemView.findViewById(R.id.fromloc);
         this.toloc= itemView.findViewById(R.id.toloc);
         this.enddate= itemView.findViewById(R.id.endtime);
         this.DurationLayout= itemView.findViewById(R.id.duration_layout);
         this.timelineView= itemView.findViewById(R.id.time_marker);
         timelineView.initLine(viewtype);
         this.visit_layout= itemView.findViewById(R.id.visit_layout);
         seekBar1= itemView.findViewById(R.id.seek1);
         seekBar3= itemView.findViewById(R.id.seek3);
         seekBar2= itemView.findViewById(R.id.seek2);

     /*    seekBar1.setOnTouchListener(new View.OnTouchListener() {
       @Override
       public boolean onTouch(View view, MotionEvent motionEvent) {
           seekBar1.setEnabled(false);
           seekBar1.getProgressDrawable().setAlpha(255);
           seekBar3.setProgress(0);
           Log.d("one","one");
           seekBar1.getThumb().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.MULTIPLY);
           seekBar2.setEnabled(true);
           seekBar1.setProgress(0);
           return false;
       }
   });*/
       /* seekBar3.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 seekBar3.setEnabled(false);
              ///   seekBar1.setProgress(0);
                 seekBar3.getProgressDrawable().setAlpha(255);
                 Log.d("2","2");
                 seekBar1.getThumb().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.MULTIPLY);
                 return false;
             }
         });

       seekBar2.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 seekBar2.setEnabled(false);
                 seekBar2.getProgressDrawable().setAlpha(255);
              ///   seekBar1.setProgress(0);
                 seekBar1.getThumb().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.MULTIPLY);
                 return false;
             }
         });
*/
     }

}

    @Override
    public VisitListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
       //  View view =mLayoutInflater.inflate(R.layout.visitlistadapter, parent, false);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitlistadapter, parent, false);

        VisitListAdapter.MyViewHolder myViewHolder = new VisitListAdapter.MyViewHolder(view,viewType);
        view.setOnClickListener(this);
        return myViewHolder;
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public void onBindViewHolder(VisitListAdapter.MyViewHolder holder, Cursor cursor) {
      int visit_or_travel=cursor.getInt(cursor.getColumnIndex(VisitModel.travelorcallflag));
        String  findatsite=cursor.getString(cursor.getColumnIndex(VisitModel.TempStartAddress));
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        String at=cursor.getString(cursor.getColumnIndex(VisitModel.TempAtorEndAdr));
        if(visit_or_travel==1){
            String status=cursor.getString(cursor.getColumnIndex(VisitModel.TempStartLong));
            if(status==null){
                holder.status.setText("Visit Is In Process");
            }
            else {
                holder.status.setText(status);
            }
            holder.visit_layout.setVisibility(View.VISIBLE);
            holder.visittexts.setVisibility(View.GONE);
            holder.seekBar1.setVisibility(View.GONE);
            holder.seekBar2.setVisibility(View.GONE);
            holder.seekBar3.setVisibility(View.GONE);
            holder.seebar.setVisibility(View.GONE);
            if(findatsite!=null) {
                holder.findingsatsite.setText("FINDINGS AT SITE :" + "  " + findatsite);
            }
            else {
                holder.findingsatsite.setText("FINDINGS AT SITE :");

            }
            if (at!=null){
                holder.at.setText("ACTION TAKEN :" + "  "+at);
            }
            else {
                holder.at.setText("ACTION TAKEN :");
            }
            int errocode=cursor.getInt(cursor.getColumnIndex(VisitModel.TempMaporother));
            if(errocode==0){
                holder.error_layout.setVisibility(View.GONE);
            }
            else {
                holder.error_layout.setVisibility(View.VISIBLE);
                holder.error_msg.setText(cursor.getString(cursor.getColumnIndex(VisitModel.TempKm)));
            }

             p.addRule(RelativeLayout.BELOW, R.id.visit_layout);
            holder.DurationLayout.setLayoutParams(p);
            holder.timelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_wrench));
        }
        else {
            holder.error_layout.setVisibility(View.GONE);
            String mode=cursor.getString(cursor.getColumnIndex(VisitModel.TempMode));
            //String distance=cursor.getString(cursor.getColumnIndex(VisitModel.TempKm));
        //    if(distance!=null){
          //      holder.distance.setText(distance);
           // }
            if(mode==null){
                holder.status.setText("MODE : "+" "+"Two Wheeler");
            }
            else {
                holder.status.setText(mode);
            }
            holder.visit_layout.setVisibility(View.GONE);
            holder.visittexts.setVisibility(View.VISIBLE);
            holder.seekBar1.setVisibility(View.VISIBLE);
            holder.seekBar2.setVisibility(View.VISIBLE);
            holder.seekBar3.setVisibility(View.VISIBLE);
            holder.seebar.setVisibility(View.VISIBLE);
            holder.seekBar1.setEnabled(false);
            holder.seekBar2.setEnabled(false);
            holder.seekBar3.setEnabled(false);
             holder.status.setText("Travel");
            p.addRule(RelativeLayout.BELOW, R.id.rl9);
            holder.DurationLayout.setLayoutParams(p);
            holder.timelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_motorbike));
            if(findatsite!=null){
                if(findatsite.length()>=10){
                    holder.fromloc.setText(findatsite.substring(0,10)+"...");
                }
                else {
                    holder.fromloc.setText(findatsite);
                }
            }
            if(at!=null){
                if(at.length()>=10){
                    holder.toloc.setText(at.substring(0,10)+"...");
                }
                else {
                    holder.toloc.setText(at);
                }
            }
        }


        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat printFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date checkin_date = null;
        String checkout_time=null;
        Date checkout_date=null;
        try {

            checkin_date = parseFormat.parse(cursor.getString(cursor.getColumnIndex(VisitModel.TempCheckin)));
            String checkin_time = printFormat.format(checkin_date);
            String checkoutTime = cursor.getString(cursor.getColumnIndex(VisitModel.TempCheckout));
            holder.date.setText(dateFormat.format(checkin_date));

            holder.startdate.setText(checkin_time);
            if (checkoutTime == null) {
                holder.enddate.setText("In Progress");
            } else {
                checkout_date = parseFormat.parse(checkoutTime);
                checkout_time = printFormat.format(checkout_date);
                holder.enddate.setText(checkout_time);
            }

            if (checkin_time != null && checkoutTime != null) {
              //  SimpleDateFormat durationFromat=new SimpleDateFormat("HH:mm");
                Date starttime=printFormat.parse(checkin_time);
                Date checkout=printFormat.parse(checkout_time);
                long diff = Math.abs(starttime.getTime() - checkout.getTime());
            //    long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                holder.duration.setText(diffHours +":"+diffMinutes);
            }
            else {
                holder.duration.setText("On Going");
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


    }




    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int   position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            final Cursor cursor = this.getItem(position);
            this.onItemClickListener.onItemClicked(cursor);
        }
    }

}

