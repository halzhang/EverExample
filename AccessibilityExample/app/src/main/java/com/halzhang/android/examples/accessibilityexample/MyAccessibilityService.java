package com.halzhang.android.examples.accessibilityexample;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

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

    @Override
    public void onInterrupt() {

    }
}
