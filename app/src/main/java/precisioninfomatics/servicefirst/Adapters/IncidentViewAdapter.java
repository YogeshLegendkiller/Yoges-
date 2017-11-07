package precisioninfomatics.servicefirst.Adapters;

/**
 * Created by 4264 on 29-11-2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.R;

public class IncidentViewAdapter extends CursorRecyclerViewAdapter<IncidentViewAdapter.IncidentViewHolder>   {

    public IncidentViewAdapter(Cursor cursor) {
        super(cursor);
       // this.mContext = mContext;

    }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incidenview, parent, false);
        return new IncidentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, Cursor cursor) {
        holder.comapnyname.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.inc_companyname)));
        holder.Callorigin.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.inc_callorgin)));
        holder.Serviceclassification.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.serviceclassification)));
        holder.servicecategory.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.servicecategory)));
        holder.servicesubcategory.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.servicesubcategory)));
        holder.problemcategory.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.inc_probcategory)));
        holder.problemdescription.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.probdescription)));
        holder.priority.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.priority)));
        holder.remarks.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.remarks)));
        holder.serviceprovider.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.serviceprovider)));
        holder.serialno.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.serialno)));
        holder.customercode.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.customercode)));
        holder.address.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.customerbranchaddress)));
        holder.contactname.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.contactname)));
        holder.designation.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.designation)));
        holder.emailid.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.mailid)));
        holder.phoneno.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.phno)));
        holder.partno.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.partno)));
        String startdate=ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(IncidentViewModel.periodfrom)));
        String enddate=ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(IncidentViewModel.periodto)));
        holder.startdate.setText(startdate);
        holder.enddate.setText(enddate);
        holder.assetin.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.periodtype)));
        holder.periodtype.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.contractcode)));
        holder.baseconfiguration.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(IncidentViewModel.contaract_sdate))));
        holder.contractenddate.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(IncidentViewModel.contract_enddate))));
        holder.contractstatus.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.conatractstatus)));
        holder.accountmanager.setText(cursor.getString(cursor.getColumnIndex(IncidentViewModel.acm)));
    }
    static  class IncidentViewHolder extends RecyclerView.ViewHolder{
         private TextView comapnyname,customercode,address,contactname,designation,emailid,phoneno,partno,serialno,
                  startdate,enddate,assetin,periodtype,baseconfiguration,
                  contractenddate,
                  contractstatus,
                  accountmanager,Callorigin,Serviceclassification,servicecategory,
                  servicesubcategory,
                  problemcategory,problemdescription,priority,remarks,serviceprovider;
        private IncidentViewHolder(View itemView) {
            super(itemView);
            this.partno= itemView.findViewById(R.id.partno);
            this.startdate= itemView.findViewById(R.id.startdate);
            this.enddate= itemView.findViewById(R.id.enddate);
            this.assetin= itemView.findViewById(R.id.assetin);
            this.baseconfiguration= itemView.findViewById(R.id.contractsdate);
         //   this.contractstartdate=(TextView)itemView.findViewById(R.id.contractsdate);
           this.contractenddate= itemView.findViewById(R.id.contract_edate);
           this.contractstatus= itemView.findViewById(R.id.contractstatus);
            this.accountmanager= itemView.findViewById(R.id.acm);
            this.periodtype= itemView.findViewById(R.id.contractcode);
            this.comapnyname= itemView.findViewById(R.id.companyname);
            this.Callorigin= itemView.findViewById(R.id.callorgin);
            this.Serviceclassification= itemView.findViewById(R.id.serviceclasification);
            this.servicecategory= itemView.findViewById(R.id.servicecatgory);
            this.servicesubcategory= itemView.findViewById(R.id.servicesubcategory);
            this.problemcategory= itemView.findViewById(R.id.problemcategory);
            this.problemdescription= itemView.findViewById(R.id.problemdescription);
            this.priority= itemView.findViewById(R.id.priority);
            this.remarks= itemView.findViewById(R.id.remarks);
            this.serviceprovider= itemView.findViewById(R.id.serviceprovider);
            this.serialno= itemView.findViewById(R.id.serialnumber);
            ///
            this.customercode= itemView.findViewById(R.id.customercode);
            this.address= itemView.findViewById(R.id.address);
            this.contactname= itemView.findViewById(R.id.contactname);
            this.designation= itemView.findViewById(R.id.designation);
            this.emailid= itemView.findViewById(R.id.emailid);
            this.phoneno= itemView.findViewById(R.id.phoneno);

    //        this.incidentcodetext=(TextView)itemView.findViewById(R.id.incidentcodetext);
        }
    }
}
