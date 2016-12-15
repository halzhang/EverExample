package com.halzhang.android.examples.accessibilityexample;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

    public static final int STEP_COUNT = 5;
    private int mCurrentStep = 0;

    public MyAccessibilityService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: MyAccessibilityService");
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
                break;
        }

    }

    /**
     * 1、打开聊天界面
     */
    private void openChat() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> hmNodeInfos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/hm");
            for (AccessibilityNodeInfo hmNodeInfo : hmNodeInfos) {
                CharSequence text = hmNodeInfo.getText();
                Log.i(TAG, "openChat: " + text);
                AccessibilityNodeInfo parent = hmNodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        }
    }

    /**
     * 2、打开聊天设置界面
     */
    private void openChatSettingPage() {

    }

    /**
     * 3、打开用户个人资料页面，并获取微信号，并点击“发消息”，打开聊天页面
     */
    private void openUserInfoPage() {

    }

    /**
     * 4、打开聊天界面，
     * @param message
     */
    private void openChatPageSendMessage(String message){

    }

    @Override
    public void onInterrupt() {

    }
}
