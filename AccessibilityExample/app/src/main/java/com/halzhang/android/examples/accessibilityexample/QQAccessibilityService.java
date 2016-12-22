package com.halzhang.android.examples.accessibilityexample;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QAS
 * Created by Hal on 16/12/16.
 */

public class QQAccessibilityService extends AccessibilityService {

    private static final String TAG = "QAS";
    private int mCurrentStep = 1;
    private String mCurrentQQNum;
    private Set<String> mHasSendQqNums = new HashSet<>();
    private Set<String> mHasOpenUserInfoNick = new HashSet<>();//此次轮训中已经打开过的qq 昵称

    private final static boolean DEBUG = true;

    private AtomicBoolean isProcessing = new AtomicBoolean(false);

    private Handler handler = new Handler();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
//        Log.i(TAG, "onAccessibilityEvent: " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                openQQGroupChat();
                openQQGroupSetting();
                openQQGroupMember();
                openUserInfoPage();
                getQQNumberAndOpenChatPage();
                sendMessageAndBack("hello " + mCurrentQQNum);
                break;
            default:
                break;
        }
    }

    /**
     * 0-切换 tab
     */
    private void switchTab() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: start");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCurrentStep = 1;
        mHasOpenUserInfoNick.clear();
        mHasSendQqNums.clear();
        Log.i(TAG, "onDestroy: end");
    }

    /**
     * 1、打开群组聊天
     */
    private void openQQGroupChat() {
        if (mCurrentStep != 1) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo unreadmsgNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/unreadmsg");
        if (unreadmsgNodeInfo == null) {
            return;
        }
        AccessibilityNodeInfo listItemNodeInfo = unreadmsgNodeInfo.getParent().getParent().getParent();
        if ("android.widget.LinearLayout".equals(listItemNodeInfo.getClassName())) {
            Log.i(TAG, "openQQGroupChat: find list item node info");
            listItemNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mCurrentStep++;
        }
    }

    /**
     * 2、打开群设置页面
     */
    private void openQQGroupSetting() {
        if (mCurrentStep != 2) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo titleNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/title");
        if (titleNodeInfo == null) {
            return;
        }
        // TODO: 16/12/21 正式发布打开
//        String title = titleNodeInfo.getText().toString().trim();
//        if (!TextUtils.equals("群名称", title)) {
//            return;
//        }
        AccessibilityNodeInfo groupInfoBtnNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/ivTitleBtnRightImage");
        if (groupInfoBtnNodeInfo == null) {
            return;
        }
        groupInfoBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mCurrentStep++;

    }

    /**
     * 3、打开群成员页面,遍历列表，并打开用户资料页面
     */
    private void openQQGroupMember() {
        if (mCurrentStep != 3) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo addNodeInfo = Utils.getNodeInfoByIdAndText(rootNodeInfo, "com.tencent.mobileqq:id/title", "群成员");
        if (addNodeInfo == null) {
            return;
        }
        AccessibilityNodeInfo groupMemberNodeInfo = addNodeInfo.getParent();
        if (groupMemberNodeInfo != null) {
            groupMemberNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mCurrentStep++;
        }
    }

    /**
     * 4、打开用户资料页面
     */
    private void openUserInfoPage() {
        if (mCurrentStep != 4 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);

        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            isProcessing.set(false);
            return;
        }

        List<AccessibilityNodeInfo> nickNameNodeInfos = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/tv_name");
        if (nickNameNodeInfos == null || nickNameNodeInfos.isEmpty()) {
            isProcessing.set(false);
            return;
        }

        for (AccessibilityNodeInfo nickNameNodeInfo : nickNameNodeInfos) {
            String nickName = nickNameNodeInfo.getText().toString();
            if (nickNameNodeInfo.getParent() == null || nickNameNodeInfo.getParent().getParent() == null) {
                Log.w(TAG, "openUserInfoPage: nicknameNodeinfo parent is empty ,nickname: " + nickName);
                continue;
            }
            AccessibilityNodeInfo listItemNodeInfo = nickNameNodeInfo.getParent().getParent();
            if ("android.widget.FrameLayout".equals(listItemNodeInfo.getClassName())) {
                if (isFoundMember(listItemNodeInfo)) {
                    continue;
                }
//                Log.i(TAG, "openUserInfoPage: check nickname before " + nickName);
                if (mHasOpenUserInfoNick.contains(nickName)) {
                    continue;
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "openUserInfoPage: nickname: " + nickName);
                mHasOpenUserInfoNick.add(nickName);
                AccessibilityNodeInfo childNodeInfo = listItemNodeInfo.getChild(0);
                // listview 的 NodeInfo 层级比较坑
                childNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mCurrentStep++;
                isProcessing.set(false);
                return;
            }
        }

        Log.i(TAG, "openUserInfoPage: all nick has send scroll forward");
        final AccessibilityNodeInfo listViewNodeInfo = Utils.getParentByClassName(nickNameNodeInfos.get(0), "android.widget.AbsListView");
        isProcessing.set(false);
        if (listViewNodeInfo == null) {
            return;
        }
        listViewNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }


    /**
     * 是否是管理员或者群主
     *
     * @return
     */
    private boolean isFoundMember(AccessibilityNodeInfo nodeInfo) {
        AccessibilityNodeInfo founderNodeInfo = Utils.getNodeInfoByText(nodeInfo, "群主");
        AccessibilityNodeInfo adminNodeInfo = Utils.getNodeInfoByText(nodeInfo, "管理员");
        return founderNodeInfo != null || adminNodeInfo != null;
    }

    /**
     * 5、获取 QQ 号码，并打开聊天界面
     */
    private void getQQNumberAndOpenChatPage() {
//        Log.i(TAG, "getQQNumberAndOpenChatPage: " + mCurrentStep);
        if (mCurrentStep != 5 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);


        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();

        //test
        if (DEBUG) {
            back();
            mCurrentStep--;
            isProcessing.set(false);
            return;
        }
        //


        AccessibilityNodeInfo accountLabelNodeInfo = Utils.getNodeInfoByTextAndClassName(rootNodeInfo, "帐号信息", "android.widget.TextView");
        if (accountLabelNodeInfo == null) {
            return;
        }
        AccessibilityNodeInfo accountParentNodeInfo = accountLabelNodeInfo.getParent();
        if (accountParentNodeInfo == null) {
            return;
        }
        if (TextUtils.equals("android.widget.RelativeLayout", accountParentNodeInfo.getClassName())) {
            String contentDesc = accountParentNodeInfo.getContentDescription().toString().trim();
            String qqNum = parseQQMNumber(contentDesc);
            Log.i(TAG, "getQQNumberAndOpenChatPage: " + qqNum);
            if (mHasSendQqNums.contains(qqNum)) {
                back();
                mCurrentStep--;
            } else {
                mCurrentQQNum = qqNum;
                AccessibilityNodeInfo sendMessageBtnNodeInfo = Utils.getNodeInfoByTextAndClassName(rootNodeInfo, "发消息", "android.widget.Button");
                if (sendMessageBtnNodeInfo == null) {
                    return;
                }
                sendMessageBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mCurrentStep++;
            }
        }
    }

    private String parseQQMNumber(CharSequence source) {
        final Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 6、发消息，并返回
     *
     * @param message
     */
    private void sendMessageAndBack(String message) {
        if (mCurrentStep != 6) {
            return;
        }
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo inputNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/input");
        if (inputNodeInfo == null) {
            return;
        }
        if (TextUtils.equals("android.widget.EditText", inputNodeInfo.getClassName())) {
            Utils.setText(this, inputNodeInfo, message);
            final AccessibilityNodeInfo sendBtnNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/fun_btn");
            if (sendBtnNodeInfo == null) {
                return;
            }
            if (TextUtils.equals("android.widget.Button", sendBtnNodeInfo.getClassName())) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHasSendQqNums.add(mCurrentQQNum);
                        mCurrentStep = 1;
                        back();
                    }
                }, 1000);

            }
        }
    }

    private void back() {
        back(false);
    }

    private void back(boolean immediately) {
        if (immediately) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);   // 返回
        } else {
            handler.postDelayed(new Runnable() { // 返回主界面，这里延迟执行，为了有更好的交互
                @Override
                public void run() {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);   // 返回
                }
            }, 500);

        }


    }


    @Override
    public void onInterrupt() {

    }
}
