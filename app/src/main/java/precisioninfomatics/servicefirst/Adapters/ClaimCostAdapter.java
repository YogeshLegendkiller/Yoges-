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
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 25/05/2017.
 */

public class ClaimCostAdapter extends RecyclerView.Adapter<ClaimCostAdapter.ClaimViewHolder> {

    private List<ClaimFieldModel> listobj;

    public ClaimCostAdapter(List<ClaimFieldModel>listobj){
         this.listobj=listobj;
    }

    @Override
    public ClaimCostAdapter.ClaimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.claimview, parent, false);
        return new  ClaimCostAdapter.ClaimViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ClaimCostAdapter.ClaimViewHolder holder, int position) {
                 holder.Label.setText(listobj.get(position).getName());
                 holder.Data.setText(String.valueOf(listobj.get(position).getClaimAmount()));

        }

    @Override
    public int getItemCount() {
        return listobj.size();
    }

    static class ClaimViewHolder extends RecyclerView.ViewHolder {
        TextView Label,
                Data;



        private ClaimViewHolder(View itemView) {
            super(itemView);
            this.Label= itemView.findViewById(R.id.label);
            this.Data= itemView.findViewById(R.id.data);
        }
    }
}
