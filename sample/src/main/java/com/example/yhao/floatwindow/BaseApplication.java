package com.example.yhao.floatwindow;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.example.yhao.fixedfloatwindow.R;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;

import io.github.controlwear.virtual.joystick.android.JoystickView;

/**
 * Created by yhao on 2017/12/18.
 * https://github.com/yhaolpz
 */

public class BaseApplication extends Application {


    private static final String TAG = "FloatWindow";

    @Override
    public void onCreate() {
        super.onCreate();

        View view = LayoutInflater.from(this).inflate(R.layout.activity_floatview,null);
        final TextView mTextViewAngleRight = view.findViewById(R.id.textView_angle_right);
        final TextView mTextViewStrengthRight = view.findViewById(R.id.textView_strength_right);
        final TextView mTextViewCoordinateRight = view.findViewById(R.id.textView_coordinate_right);
        final JoystickView joystickRight = view.findViewById(R.id.joystickView_right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                mTextViewAngleRight.setText(angle + "°");
                mTextViewStrengthRight.setText(strength + "%");
                mTextViewCoordinateRight.setText(
                        String.format("x%03d:y%03d",
                                joystickRight.getNormalizedX(),
                                joystickRight.getNormalizedY())
                );
            }
        });

        FloatWindow
                .with(getApplicationContext())
                .setView(view)
                .setWidth(Screen.width, 0.5f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.5f)
                .setX(Screen.width, 0.4f)
                .setY(Screen.height, 0.3f)
//                .setMoveType(MoveType.slide, -100, -100)
                .setMoveType(MoveType.active)
                .setMoveStyle(500, new BounceInterpolator())
//                .setFilter(true, A_Activity.class, C_Activity.class)
                .setFilter(false)
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();
    }

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
}
