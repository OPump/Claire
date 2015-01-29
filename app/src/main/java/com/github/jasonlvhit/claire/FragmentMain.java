package com.github.jasonlvhit.claire;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigInteger;

/**
 * Created by Jason Lyu on 2015/1/25.
 */
public class FragmentMain extends Fragment {
    
    private static final String LOG_TAG = FragmentMain.class.getSimpleName();
    private static BigInteger mCelsius = BigInteger.ZERO;
    private static BigInteger mFahrenheit = BigInteger.ZERO;

    private static TextView mCelsiusTextView;
    private static TextView mFahrenheitTextView;
    private static RelativeLayout mLeftSlide;
    private static RelativeLayout mRightSlide;

    private static TextView mLeftColorTextView;
    private static TextView mRightColorTextView;

    private static ColorMap mColorMap = new ColorMap();

    private static final String INPUT_DIALOG_TAG = "INPUT_DIALOG";
    private static final int REQUEST_INPUT = 0;

    private static final int CELSIUS_TO_FAHRENHEIT = 0;
    private static final int FAHRENHEIT_TO_CELSIUS = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mCelsiusTextView = (TextView) rootView.findViewById(R.id.celsius);
        mFahrenheitTextView = (TextView) rootView.findViewById(R.id.fahrenheit);

        mLeftSlide = (RelativeLayout) rootView.findViewById(R.id.left_slide);
        mRightSlide = (RelativeLayout) rootView.findViewById(R.id.right_slide);

        mLeftColorTextView = (TextView) rootView.findViewById(R.id.left_color_text);
        mRightColorTextView = (TextView) rootView.findViewById(R.id.right_color_text);
        doChange(CELSIUS_TO_FAHRENHEIT);
        return rootView;
    }

    public void increaseDegree() {
        mCelsius = mCelsius.add(BigInteger.ONE);
        doChange(CELSIUS_TO_FAHRENHEIT);
    }

    public void decreaseDegree() {
        mCelsius = mCelsius.add(BigInteger.valueOf(-1));
        doChange(CELSIUS_TO_FAHRENHEIT);
    }

    public void setCelsius(BigInteger celsius){
        mCelsius = celsius;
        doChange(CELSIUS_TO_FAHRENHEIT);
    }

    public void setFahrenheit(BigInteger fahrenheit){
        mFahrenheit = fahrenheit;
        doChange(FAHRENHEIT_TO_CELSIUS);
    }

    private void doChange(int TYPE) {
        if(TYPE == CELSIUS_TO_FAHRENHEIT)
            mFahrenheit = Utils.celsiusToFahrenheit(mCelsius);
        else if(TYPE == FAHRENHEIT_TO_CELSIUS)
            mCelsius = Utils.fahrenheitToCelsius(mFahrenheit);
        mCelsiusTextView.setText(String.valueOf(mCelsius));
        mFahrenheitTextView.setText(String.valueOf(mFahrenheit));
        ColorMap.ColorHolder holder = mColorMap.getColor(mCelsius);

        mLeftSlide.setBackgroundColor(Color.parseColor(holder.getLeftColor()));
        mRightSlide.setBackgroundColor(Color.parseColor(holder.getRightColor()));
        mRightColorTextView.setText(holder.getRightColor());
        mLeftColorTextView.setText(holder.getLeftColor());
    }

    public void openInputDialog(String title){
        InputFragment dialog = InputFragment.newInstance(title);
        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(this, REQUEST_INPUT);
        dialog.show(fm, INPUT_DIALOG_TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((MainActivity)getActivity()).setInDialog(false);
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_INPUT){
            String input = data.getStringExtra("INPUT");
            String TYPE = data.getStringExtra("TYPE");
            if(input.length() != 0 && TYPE.length() != 0){
                if(TYPE == getString(R.string.input_dialog_title_celsius))
                setCelsius(new BigInteger(input));
                else setFahrenheit(new BigInteger(input));
            }
        }
    }
}
