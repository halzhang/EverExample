package com.halzhang.android.examples.accessibilityexample;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.halzhang.android.examples.accessibilityexample.models.SettingModel;

public class QQServiceSettingsActivity extends AppCompatActivity {

    private static final String TAG = "QSSA";
    private SettingPresenter mPresenter;
    private SettingModel mModel;

    private EditText mGroupEt;
    private EditText mCountEt;

    private TextInputLayout mGroupTextInputLayout;
    private TextInputLayout mCountTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_settings);

        mCountEt = (EditText) findViewById(R.id.et_send_count);
        mGroupEt = (EditText) findViewById(R.id.et_group_name);

        mGroupTextInputLayout = (TextInputLayout) findViewById(R.id.group_til);
        mCountTextInputLayout = (TextInputLayout) findViewById(R.id.count_til);

        mPresenter = new SettingPresenter(getApplicationContext());
        mModel = mPresenter.getSetting();
        mGroupEt.setText(mModel.getQGroupName());
        mCountEt.setText(String.valueOf(mModel.getSendCount()));
    }

    public void onSave(View view) {

        mGroupEt.setError(null);
        mCountEt.setError(null);

        String groupName = mGroupEt.getText().toString();
        if (TextUtils.isEmpty(groupName)) {
            mGroupTextInputLayout.setErrorEnabled(true);
            mGroupTextInputLayout.setError("群名不能为空");
            mGroupEt.requestFocus();
            return;
        } else {
            mGroupTextInputLayout.setErrorEnabled(false);
        }
        String countStr = mCountEt.getText().toString();
        if (TextUtils.isEmpty(countStr)) {
            mCountTextInputLayout.setErrorEnabled(true);
            mCountTextInputLayout.setError("发送数量不能为空");
            mCountEt.requestFocus();
            return;
        } else {
            mCountTextInputLayout.setErrorEnabled(false);
        }
        int count = Integer.valueOf(countStr);
        if (count < 1) {
            mCountTextInputLayout.setErrorEnabled(true);
            mCountTextInputLayout.setError("数量不能小于1");
            mCountEt.requestFocus();
            return;
        } else {
            mCountTextInputLayout.setErrorEnabled(false);
        }
        mModel.setQGroupName(groupName);
        mModel.setSendCount(count);
        mPresenter.onSave(mModel);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
    }

}
