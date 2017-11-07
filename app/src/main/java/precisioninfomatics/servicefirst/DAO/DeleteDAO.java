package precisioninfomatics.servicefirst.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.DBhelper.SFSingelton;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.SpareModel;

/**
 * Created by 4264 on 03/05/2017.
 */

public class DeleteDAO extends SFSingelton {

    public DeleteDAO(Context context) {
        super(context);
    }
    public void InsertRecords(DeleteModel deleteModel){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DeleteModel.flag,deleteModel.getFlag());
        contentValues.put(DeleteModel.WeBID,deleteModel.getWebID());
      ///  contentValues.put(DeleteModel.issent,deleteModel.getIsSent());
        db.insert(DeleteModel.DeleteTable,null,contentValues);
    }
    public void Delete(int id,int flag){
        db.delete(DeleteModel.DeleteTable, DeleteModel.WeBID + " ="+id+ " and " + DeleteModel.flag + " =" + flag ,null);
       // mcontext.getContentResolver().notifyChange(DB_SF_Spare, null);
    }
  public List<DeleteModel>deletelist(){
      String Query=" select * from "+ DeleteModel.DeleteTable;
      Cursor cursor=db.rawQuery(Query,null);
      List<DeleteModel> listobj = new ArrayList<>();
      if (cursor != null && cursor.getCount() > 0) {
          if (cursor.moveToFirst()) {
              do {
                  DeleteModel deleteModel = new DeleteModel();
                  deleteModel.setFlag(cursor.getInt(cursor.getColumnIndex(DeleteModel.flag)));
                  deleteModel.setWebID(cursor.getInt(cursor.getColumnIndex(DeleteModel.WeBID)));
                  listobj.add(deleteModel);
              } while (cursor.moveToNext());

          }
      }
      if (cursor != null || !cursor.isClosed()) {
          cursor.close();
      }
      return listobj;

  }}