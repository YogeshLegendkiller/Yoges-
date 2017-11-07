package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import precisioninfomatics.servicefirst.Model.VehicleModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 19-01-2017.
 */

public class VehicleRegistrationAdapter extends RecyclerView.Adapter<VehicleRegistrationAdapter.RegistrationViewHolder> {

    private List<VehicleModel> listobj;

    public VehicleRegistrationAdapter(List<VehicleModel>listobjs){
        listobj=listobjs;
    }

    @Override
    public VehicleRegistrationAdapter.RegistrationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_registration_view, parent, false);
        return new VehicleRegistrationAdapter.RegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegistrationViewHolder holder, int position) {
        holder.reg_no.setText(listobj.get(position).getRegNo());
        holder.lic_exp_date.setText(listobj.get(position).getExpiryDate());
        holder.lic_no.setText(listobj.get(position).getLicenseNo());
        holder.insurance_exp_date.setText(listobj.get(position).getInsuranceExpiryDate());
        holder.lic_type.setText(listobj.get(position).getLicenseType());
    }


    @Override
    public int getItemCount() {
        return listobj.size();
    }

     static class RegistrationViewHolder extends RecyclerView.ViewHolder {
      private   TextView reg_no,
                lic_no,
                lic_type,
                lic_exp_date ,
                insurance_exp_date;


        private RegistrationViewHolder(View itemView) {
            super(itemView);
            this.reg_no= itemView.findViewById(R.id.reg_no);
            this.lic_no= itemView.findViewById(R.id.lic_no);
            this.lic_type= itemView.findViewById(R.id.lic_type);
            this.lic_exp_date= itemView.findViewById(R.id.lic_expirydate);
            this.insurance_exp_date= itemView.findViewById(R.id.insurace_exp);

        }
    }
}

