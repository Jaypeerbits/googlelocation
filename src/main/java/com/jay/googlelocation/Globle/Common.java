package com.jay.googlelocation.Globle;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jay.googlelocation.models.googledistance.Example;
import com.jay.googlelocation.retrofit.APIClient;
import com.jay.googlelocation.retrofit.ApisInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lg on 8/29/2016.
 */
public class Common {

    public static final String INTERNET_NOT_FOUND_MSG = "No Internet found!!";

    public static boolean checkInternetConnection(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static void alertDialog(Context contex, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contex);
        builder.setTitle("Alert!");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                // Intent i=new
                // Intent(contex,BankDebitCardConfigurationMain.class);
                // startActivity(i);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

}
