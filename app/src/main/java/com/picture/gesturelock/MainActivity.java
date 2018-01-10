package com.picture.gesturelock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GestureLockViewGroup mGestureLockViewGroup;
    private LockIndicator mLockIndicator;
    private String pwdString;
    private int count = 0;
    private TextView mHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
        mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
        mHint = (TextView) findViewById(R.id.tv_hint);
        mHint.setText("创建手势密码");
        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5});
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        Toast.makeText(MainActivity.this, "错误5次...",
                                Toast.LENGTH_SHORT).show();
                        mGestureLockViewGroup.setUnMatchExceedBoundary(5);
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {

                    }

                    @Override
                    public void onBlockSelected(int cId) {

                    }

                    @Override
                    public void onGesturePwdReturnStr(String pwdStr) {
                        if (pwdStr.length() < 4) {
                            Toast.makeText(MainActivity.this, "密码长度必须大于3位", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        count++;
                        if (count == 1) {
                            pwdString = pwdStr;
                            updateCodeList(pwdStr);
                            mHint.setText("请再输入一次");
                        } else if (count == 2) {
                            if (pwdString.equals(pwdStr)) {
                                Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                            } else {
                                count--;
                                mHint.setText("请再输入一次");
                            }
                        }
                    }
                });
    }

    private void updateCodeList(String inputCode) {
        // 更新选择的图案
        mLockIndicator.setPath(inputCode);
    }
}
