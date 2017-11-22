package id.sch.smkn2cikbar.exambrowser;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by robby on 21/11/17.
 */

public class progressDialogModel {

    static ProgressDialog progressDialog;

    public static void pdMenyiapkanDataLogin(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Menyiapkan Data....");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
