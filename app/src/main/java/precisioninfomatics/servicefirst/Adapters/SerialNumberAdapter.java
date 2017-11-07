package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Activities.VisitUpdation;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 16/06/2017.
 */

public class SerialNumberAdapter  extends CursorRecyclerViewAdapter<SerialNumberAdapter.MyViewHolder> implements View.OnClickListener {
    private precisioninfomatics.servicefirst.Adapters.OnItemClickListener onItemClickListener;
    private Context mcontext;

    public SerialNumberAdapter(Context mContext, Cursor cursor) {
        super(cursor);
        this.mcontext = mContext;
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

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serialno, shiptoserialno;
        private ImageButton delete;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.serialno = (TextView) itemView.findViewById(R.id.serialnumber);
            this.shiptoserialno = (TextView) itemView.findViewById(R.id.shiptoserialno);
            this.delete=(ImageButton)itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public SerialNumberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serialnumber, parent, false);
        view.setOnClickListener(this);

        return new SerialNumberAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SerialNumberAdapter.MyViewHolder holder, Cursor cursor) {
        //   String partno=cursor.getString(cursor.getColumnIndex(PartModel.partno));
        // holder.brand.setText(cursor.getString(cursor.getColumnIndex(PartModel.brand)));
        //holder.model.setText(cursor.getString(cursor.getColumnIndex(PartModel.model)));

        holder.serialno.setText(cursor.getString(cursor.getColumnIndex(SerialNumberMapModel.serialno)));
        holder.shiptoserialno.setText(cursor.getString(cursor.getColumnIndex(SerialNumberMapModel.shiptoserialno)));
        final Integer  id = cursor.getInt(cursor.getColumnIndex(SerialNumberMapModel.id));
      //  final Integer id=cursor.getInt(cursor.getColumnIndex(SerialNumberMapModel.id));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SerialNumberDAO serialNumberDAO=new SerialNumberDAO(mcontext);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mcontext);

                alertDialog.setTitle("Confirm Delete...");

                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        serialNumberDAO.Delete(id);
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }
        });

    }


}