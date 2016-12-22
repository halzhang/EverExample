package com.halzhang.android.examples.accessibilityexample;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class WechatAccessibilityService extends AccessibilityService {
    private static final String TAG = "WechatService";

    public static final int STEP_COUNT = 5;
    private Handler handler = new Handler();
    private int mCurrentStep = 0;

    public WechatAccessibilityService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: WechatAccessibilityService");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        int eventType = accessibilityEvent.getEventType();
//        AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//                if (nodeInfo != null) {
//                    List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/boi");
//                    AccessibilityNodeInfo faxian = nodeInfos.get(2).getParent();
//                    faxian.performAction(AccessibilityNodeInfo.ACTION_CLICK);
////                    faxian.recycle();
////                    nodeInfo.recycle();
//                }
                openChat();
                openChatSettingPage();
                openUserInfoPage();
                getWeChatIdAndStartChat();
                setMessageAndSend("http://www.halzhang.com/?id=" + mWxId);
                break;
        }

    }

    /**
     * 0、打开聊天界面
     */
    private void openChat() {
        if (mCurrentStep != 0) {
            return;
        }
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> hmNodeInfos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/hm");
            for (AccessibilityNodeInfo hmNodeInfo : hmNodeInfos) {
                CharSequence text = hmNodeInfo.getText();
                Log.i(TAG, "openChat: " + text);
                AccessibilityNodeInfo parent = hmNodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mCurrentStep++;
                break;
            }
        }
    }

    /**
     * 1、打开聊天设置界面
     */
    private void openChatSettingPage() {
        Log.i(TAG, "openChatSettingPage: start");
        if (mCurrentStep != 1) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo != null) {
            List<AccessibilityNodeInfo> chatSetting = rootNodeInfo.findAccessibilityNodeInfosByText("聊天信息");
            if (chatSetting != null && chatSetting.size() == 1) {
                // TODO: 16/12/15 处理聊天界面中有『聊天信息』
                Log.i(TAG, "openChatSettingPage: find chat setting button lo!");
                chatSetting.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mCurrentStep++;
            }
        }
    }

    /**
     * 2、打开用户个人资料页面，并获取微信号，并点击“发消息”，打开聊天页面
     */

    private void openUserInfoPage() {
        Log.i(TAG, "openUserInfoPage: start");
        if (mCurrentStep != 2) {
            return;
        }
        Log.i(TAG, "openUserInfoPage: start in");
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo != null) {
            List<AccessibilityNodeInfo> bpoNodeInfos = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bpo");
            if (bpoNodeInfos != null && bpoNodeInfos.size() == 1) {
                AccessibilityNodeInfo bpoNodeInfo = bpoNodeInfos.get(0);
                AccessibilityNodeInfo bpoNodeInfoChild = bpoNodeInfo.getChild(0);
                bpoNodeInfoChild.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mCurrentStep++;
            }
        }
    }

    private String mWxId;

    /**
     * 3、获取微信号，生成特征链接，开始聊天
     */
    private void getWeChatIdAndStartChat() {
        Log.i(TAG, "getWeChatIdAndStartChat: start");
        if (mCurrentStep != 3) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo != null) {
            List<AccessibilityNodeInfo> accountNodeInfos = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ac2");
            if (accountNodeInfos == null || accountNodeInfos.isEmpty()) {
                //页面跳转过程中，有可能获取不到
                return;
            }
            AccessibilityNodeInfo accountTextViewNodeInfo = accountNodeInfos.get(0);
            String wechatIdText = accountTextViewNodeInfo.getText().toString();
            Log.i(TAG, "getWeChatIdAndStartChat: Wechat id: " + wechatIdText);
            mWxId = wechatIdText.substring(4, wechatIdText.length()).trim();

            AccessibilityNodeInfo sendButton = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/abw").get(0);
            sendButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mCurrentStep++;
        }
    }

    /**
     * 4、输入特征链接，发送，并返回
     *
     * @param message
     */
    private void setMessageAndSend(String message) {
        Log.i(TAG, "setMessageAndSend: start");
        if (mCurrentStep != 4) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            return;
        }
        AccessibilityNodeInfo editTextNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mm:id/a2u");
        if (editTextNodeInfo == null) {
            return;
        }
        if ("android.widget.EditText".equals(editTextNodeInfo.getClassName())) {
            Log.i(TAG, "setMessageAndSend: find editText");
            Utils.setText(this,editTextNodeInfo, message);
        }

        AccessibilityNodeInfo sendButtonNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mm:id/a30");
        if (sendButtonNodeInfo == null) {
            return;
        }
        String buttonText = sendButtonNodeInfo.getText().toString().trim();
        if ("发送".equals(buttonText) && "android.widget.Button".equals(sendButtonNodeInfo.getClassName())) {
            sendButtonNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mCurrentStep = 0;
            handler.postDelayed(new Runnable() { // 返回主界面，这里延迟执行，为了有更好的交互
                @Override
                public void run() {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);   // 返回
                }
            }, 1500);
        }
    }


    @Override
    public void onInterrupt() {

    }
}
