package com.simicart.plugins.ccavanue.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.simicart.core.config.Rconfig;


public class OtpFragment extends Fragment{


    //Button okBtn;
    TextView timerText;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(Rconfig.getInstance().layout("plugins_ccavanue_otp_fragment"),container,false);
        /*okBtn = (Button) view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(this);*/
        timerText = (TextView) view.findViewById(Rconfig.getInstance().id("timerText"));
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //timerText.setText("done!");
            }
        }.start();

        return view;
    }

    /*@Override
    public void onClick(View v) {
        if(v.getId()==R.id.okBtn) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }
    }*/
}
