package precisioninfomatics.servicefirst.Adapters;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 28/03/2017.
 */

public class SerialNumberMapAdapter  extends RecyclerView.Adapter<SerialNumberMapAdapter.MyViewHolder> implements View.OnClickListener  {
    private OnItemClickListener onItemClickListener;

    private List<SerialNumberMapModel> mModelList;
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SerialNumberMapAdapter(List<SerialNumberMapModel> modelList) {
        mModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiselect, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SerialNumberMapModel  model = mModelList.get(position);
        holder.textView.setText(model.getSerialNumber());
  //      holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
     /*   holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
     private TextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
             textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    } @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            this.onItemClickListener.onItemClicked(position);;
        }


    }
    public interface OnItemClickListener {
        void onItemClicked(int  position);
    }
}
