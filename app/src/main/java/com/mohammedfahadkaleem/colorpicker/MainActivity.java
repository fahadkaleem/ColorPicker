package com.mohammedfahadkaleem.colorpicker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, OnCrollerChangeListener {
    private static final String TAG = "MainActivity";
    private static int red = 85;
    private static int green = 150;
    private static int blue = 255;
    private static boolean red_touched = false;
    private static boolean green_touched = false;
    private static boolean blue_touched = false;
    private static String color;
    private ClipboardManager myClipboard;
    @BindView(R.id.tv_color) TextView tv_color;
    @BindView(R.id.tv_red) TextView tv_red;
    @BindView(R.id.tv_green) TextView tv_green;
    @BindView(R.id.tv_blue) TextView tv_blue;
    @BindView(R.id.red_croller) Croller red_croller;
    @BindView(R.id.green_croller) Croller green_croller;
    @BindView(R.id.blue_croller) Croller blue_croller;
    @BindView(R.id.color_view) View color_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight));
        ButterKnife.bind(this);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        setCrollChangeListners();
        red_croller.setProgress(red);
        green_croller.setProgress(green);
        blue_croller.setProgress(blue);
        changeColor(red, green, blue);
    }

    private void setCrollChangeListners() {
        color_view.setOnLongClickListener(this);
        red_croller.setOnCrollerChangeListener(this);
        green_croller.setOnCrollerChangeListener(this);
        blue_croller.setOnCrollerChangeListener(this);
    }


    @Override
    public void onProgressChanged(Croller croller, int progress) {
        if (croller.getId() == R.id.red_croller) {
            if (red_touched) {
                red = progress - 1;
                Log.d(TAG, "Red:" + red);
                changeColor(red, green, blue);
            } else {
                red_touched = true;
            }
            tv_red.setText(String.valueOf(red));
        } else if (croller.getId() == R.id.green_croller) {
            if (green_touched) {
                green = progress - 1;
                Log.d(TAG, "Green:" + green);
                changeColor(red, green, blue);
            } else {
                green_touched = true;
            }
            tv_green.setText(String.valueOf(green));
        } else if (croller.getId() == R.id.blue_croller) {
            if (blue_touched) {
                blue = progress - 1;
                Log.d(TAG, "Blue:" + blue);
                changeColor(red, green, blue);
            } else {
                blue_touched = true;
            }
            tv_blue.setText(String.valueOf(blue));
        }
    }

    @Override
    public void onStartTrackingTouch(Croller croller) {

    }

    @Override
    public void onStopTrackingTouch(Croller croller) {

    }

    public void changeColor(int red, int green, int blue) {
        Log.d(TAG, "Change Color:" + "\nRED:" + red + "\nGREEN:" + green + "\nBLUE:" + blue);
        if(red>=200 && green>=200 && blue>=200){
            tv_color.setTextColor(Color.GRAY);
        }
        else {
            tv_color.setTextColor(Color.WHITE);
        }
        color_view.setBackgroundColor(Color.rgb(red, green, blue));
        color = String.format("#%02x%02x%02x", red, green, blue);
        tv_color.setText(color);
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == color_view) {
            ClipData clipData = ClipData.newPlainText("color", color);
            myClipboard.setPrimaryClip(clipData);
            new Toast(this).makeText(this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
