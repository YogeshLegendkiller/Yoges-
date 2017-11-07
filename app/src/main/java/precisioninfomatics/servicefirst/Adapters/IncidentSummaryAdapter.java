package precisioninfomatics.servicefirst.Adapters;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import precisioninfomatics.servicefirst.Model.ChartModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 23/05/2017.
 */

public class IncidentSummaryAdapter   extends CursorRecyclerViewAdapter<IncidentSummaryAdapter.IncidentSummaryViewHolder>  implements View.OnClickListener {
private OnItemClickListener onItemClickListener;
     public IncidentSummaryAdapter(Cursor cursor) {
        super(cursor);

        }
public void setOnItemClickListener( OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        }




    @Override
    public IncidentSummaryAdapter.IncidentSummaryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incidentsummary, parent, false);

        IncidentSummaryAdapter.IncidentSummaryViewHolder myViewHolder = new IncidentSummaryAdapter.IncidentSummaryViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(IncidentSummaryAdapter.IncidentSummaryViewHolder holder, Cursor cursor) {
        String label=cursor.getString(cursor.getColumnIndex(ChartModel.Label));
        if(label.contains("PENDING")){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#C12E2A"));
        ////   holder.card.setBackgroundColor(Color.parseColor("#C12E2A"));
        }
        if(label.contains("FOLLOW UP")){
         holder.relativeLayout.setBackgroundColor(Color.parseColor("#265A88"));
      ///     holder.card.setBackgroundColor(Color.parseColor("#265A88"));
        }
        if(label.contains("STAND BY")){
          holder.relativeLayout.setBackgroundColor(Color.parseColor("#EB9316"));
       ///   holder.card.setBackgroundColor(Color.parseColor("#EB9316"));

        }
        if(label.contains("OBSERVATION")){
          holder.relativeLayout.setBackgroundColor(Color.parseColor("#449D44"));
       ///    holder.card.setBackgroundColor(Color.parseColor("#449D44"));

        }
        if(label.contains("ASSIGNED")){
      holder.relativeLayout.setBackgroundColor(Color.parseColor("#2AABD2"));
        ///  holder.card.setBackgroundColor(Color.parseColor("#2AABD2"));
        }
        if(label.contains("WORK IN PROGRESS")){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#512DAB"));
        ///   holder.card.setBackgroundColor(Color.parseColor("#512DAB"));

        }
        if(label.contains("AWAITING SPARES")){
          holder.relativeLayout.setBackgroundColor(Color.parseColor("#795548"));
        ///   holder.card.setBackgroundColor(Color.parseColor("#795548"));
        }
        if(label.contains("COMPLETED")){
         holder.relativeLayout.setBackgroundColor(Color.parseColor("#8E24AA"));
          ///  holder.card.setBackgroundColor(Color.parseColor("#8E24AA"));

        }
            holder.label.setText(label);
            holder.count.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("data"))));
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
      static   class IncidentSummaryViewHolder extends RecyclerView.ViewHolder {
        private TextView label, count;
        private LinearLayout relativeLayout;
        private IncidentSummaryViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.label);
            count = (TextView) itemView.findViewById(R.id.count);
            relativeLayout=(LinearLayout)itemView.findViewById(R.id.tv_color);
        }
    }
}
