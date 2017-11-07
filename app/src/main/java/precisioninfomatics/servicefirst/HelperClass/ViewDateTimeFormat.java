package precisioninfomatics.servicefirst.HelperClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import precisioninfomatics.servicefirst.Model.ResourceModel;

/**
 * Created by 4264 on 15-02-2017.
 */

public class ViewDateTimeFormat  {


   /* public static  String DateViewforServer(String date,int flag){
        String formattedTime=null;
        SimpleDateFormat output=null;
        try {
            //flag==1 date alone
            if(flag==1){
             output   = new SimpleDateFormat("dd-MM-yy");

            }
            else if(flag==2) {
                //flag other with time
                output   = new SimpleDateFormat("dd-MM-yy hh:mm a");
            }
            else if(flag==3){
                output=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            else if(flag==4){
                output=new SimpleDateFormat("dd-MM-yyyy");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           Date d = sdf.parse(date);
            formattedTime= output.format(d);
          } catch (ParseException e) {
            e.printStackTrace();
        }
    return formattedTime;
    }*/

    public static String DateFormatter(String input_type,String output_type,String date){
        String formattedTime=null;
        try {
        SimpleDateFormat  input   = new SimpleDateFormat(input_type);
        SimpleDateFormat output = new SimpleDateFormat(output_type);
        Date d = null;

            d = input.parse(date);
            formattedTime= output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    return formattedTime;
    }
    public static String DateView(String date,int flag){
        SimpleDateFormat output=null;
        String formattedTime=null;
        try {
        if(flag==1){
            output   = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date dateformat = sdf.parse(date);
            formattedTime= output.format(dateformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }
    public static String Date(String date,int flag){
        SimpleDateFormat output=null;
        String formattedTime=null;
        try {
            if(flag==1){
                output   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date dateformat = sdf.parse(date);
            formattedTime= output.format(dateformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

}
