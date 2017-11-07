package precisioninfomatics.servicefirst.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import precisioninfomatics.servicefirst.Adapters.SerialNumberMapAdapter;
import precisioninfomatics.servicefirst.DAO.SerialNumberDAO;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 28/03/2017.
 */

public class SerialNumber  extends DialogFragment {
    private SerialNumber.SerialnumValue mListener;
    private List<SerialNumberMapModel> filteredList;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_recyclerview, container, false);
        RecyclerView  mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Toolbar toolbar = v. findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        if (toolbar!=null) {
            toolbar.setTitle("SerialNumber");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        }
        int incid=0;
        Bundle bundle=getArguments();
        if(bundle!=null){
            incid=bundle.getInt("incid");
        }
        SerialNumberDAO   serialNumberDAO=new SerialNumberDAO(getActivity());
        filteredList=serialNumberDAO.listobj(incid);
        SerialNumberMapAdapter serialNumberMapAdapter=new SerialNumberMapAdapter(filteredList);
        mRecyclerView.setAdapter(serialNumberMapAdapter);
         serialNumberMapAdapter.setOnItemClickListener(new SerialNumberMapAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
             String text=filteredList.get(position).getSerialNumber();
             Integer id=filteredList.get(position).getSerialNumberID();
                mListener.onResultReciever(text,id);
                if (getDialog() != null && getDialog().isShowing()) {
                    getDialog().dismiss();
                }
            }
        });
     /*   toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
             //   item.setIcon(R.drawable.ic_done_24dp);
                //int id=item.getItemId();
                 List<SerialNumberMapModel>listobj=new ArrayList<SerialNumberMapModel>();
                for (SerialNumberMapModel model : filteredList) {
                    if (model.isSelected()) {
                        SerialNumberMapModel serialNumberMapModel=new SerialNumberMapModel();
                        serialNumberMapModel.setSerialNumber(model.getSerialNumber());
                          serialNumberMapModel.setSerialNumberID(model.getSerialNumberID());
                          listobj.add(serialNumberMapModel);
                        mListener.onResultReciever(listobj);
                        if (getDialog() != null && getDialog().isShowing()) {
                            getDialog().dismiss();
                        }
                    }
                }
            //   Log.d("TAG","Output : " + text);
                return false;
            }
        });*/
       // toolbar.inflateMenu(R.menu.save);
        return v;
    }



    public  interface SerialnumValue {
        void onResultReciever(String text, Integer id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onedestroy","ondestroy");
        getActivity().getSupportLoaderManager().destroyLoader(0);
    }

    public void onAttach(Context context) {
        Activity activity=null;
        super.onAttach(context);
        try {
            activity = (Activity) context;
            mListener = (SerialNumber.SerialnumValue) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

}
