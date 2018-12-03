package tabbardemo.com.materialdesigntabs_demo.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tabbardemo.com.materialdesigntabs_demo.R;
import tabbardemo.com.materialdesigntabs_demo.pojo.Example;

public class SMSFragment extends Fragment {
    private View view;

    private String title;//String for tab title

    Button smsSend;

    EditText mPhoneNumber,mMessage;

    public SMSFragment() {
    }

    @SuppressLint("ValidFragment")
    public SMSFragment(String title) {
        this.title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.smsfragment, container, false);
        setView();
        return view;

    }

    //Setting view
    private void setView() {

        mPhoneNumber = (EditText) view.findViewById(R.id.input_phonenumber);
        mMessage = (EditText) view.findViewById(R.id.input_message);
        smsSend = (Button) view.findViewById(R.id.sendsms);

        smsSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
              //No validation requested
               smsFunction(mPhoneNumber.getText().toString(),mMessage.getText().toString());

            }
        });
    }

    //Sending message with content using action intent
private void smsFunction(String phoneNumber,String messageBody){
    Intent smsMsgAppVar = new Intent(Intent.ACTION_VIEW);
    smsMsgAppVar.setData(Uri.parse("sms:" + phoneNumber ));
    smsMsgAppVar.putExtra("sms_body", messageBody);
    startActivity(smsMsgAppVar);
}


}