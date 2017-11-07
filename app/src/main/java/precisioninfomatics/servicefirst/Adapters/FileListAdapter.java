package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.FileModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 21/06/2017.
 */

public class FileListAdapter   extends CursorRecyclerViewAdapter<FileListAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    public FileListAdapter( Cursor cursor) {
        super(cursor);

    }
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public   static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView filename;
        public MyViewHolder(final View itemView) {
            super(itemView);
            this.filename = (TextView) itemView.findViewById(R.id.filename);

        }
    }
    @Override
    public FileListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fileadapter, parent, false);

        FileListAdapter.MyViewHolder myViewHolder = new FileListAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FileListAdapter.MyViewHolder holder, Cursor cursor) {
        int filetype=cursor.getInt(cursor.getColumnIndex(FileModel.Filetype));
        if(filetype==1){
            holder.filename.setText("Installation Note");
        }
        else if(filetype==2){
            holder.filename.setText(cursor.getString(cursor.getColumnIndex(FileModel.Filename)));
        }
        else if (filetype==3){
            holder.filename.setText("Installation Note Print");
        }
        else {
            holder.filename.setText("Attachment");
        }

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
}
