package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 08/07/2017.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FiterViewHolder> implements View.OnClickListener{
    private SerialNumberMapAdapter.OnItemClickListener onItemClickListener;

    private List<String> listobj;
    public void setOnItemClickListener(final SerialNumberMapAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FilterAdapter(List<String>listobj){
        this.listobj=listobj;
    }
    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            this.onItemClickListener.onItemClicked(position);
        }


    }
    public interface OnItemClickListener {
        void onItemClicked(int  position);
    }
    @Override
    public FiterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filteradapter, parent, false);
        view.setOnClickListener(this);
        return new  FilterAdapter.FiterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FiterViewHolder holder, int position) {
        holder.Text.setText(listobj.get(position));
    }
    @Override
    public int getItemCount() {
        return listobj.size();
    }

    static class FiterViewHolder extends RecyclerView.ViewHolder {
         private TextView Text;
        private FiterViewHolder(View itemView) {
            super(itemView);
            this.Text= itemView.findViewById(R.id.text);
        }
    }
}

