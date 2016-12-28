package com.halzhang.android.examples.accessibilityexample;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.halzhang.android.examples.accessibilityexample.models.SettingModel;

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
    private String mGroupTitle;
    private int mSendCount;
    private int mRecentChatListIndex = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
        Log.i(TAG, "onAccessibilityEvent: " + eventType + " step: " + mCurrentStep);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                switch (mCurrentStep) {
                    case 1:
                        openQQGroupChat();
                        break;
                    case 2:
                        openQQGroupSetting();
                        break;
                    case 3:
                        openQQGroupMember();
                        break;
                    case 4:
                        openUserInfoPage();
                        break;
                    case 5:
                        getQQNumberAndOpenChatPage();
                        break;
                    case 6:
                        sendMessageAndBack("hello " + mCurrentQQNum);
                        break;
                    default:
                        break;
                }
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
        SettingPresenter presenter = new SettingPresenter(getApplicationContext());
        SettingModel model = presenter.getSetting();
        mGroupTitle = model.getQGroupName();
        mSendCount = model.getSendCount();
    }

    private void reset() {
        mCurrentStep = 1;
        isProcessing.set(false);
        mHasOpenUserInfoNick.clear();
        mHasSendQqNums.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reset();
        Log.i(TAG, "onDestroy: end");
    }

    private void nextStep() {
        mCurrentStep++;
    }

    private void prevStep() {
        mCurrentStep--;
    }

    private void releaseProcess() {
        isProcessing.set(false);
    }

    /**
     * 1、打开群组聊天
     */
    private void openQQGroupChat() {
        if (mHasSendQqNums.size() == mSendCount) {
            Log.i(TAG, "openQQGroupChat: send count has enough!");
            return;
        }
        if (mCurrentStep != 1 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }

        AccessibilityNodeInfo listViewNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/recent_chat_list");
        if (listViewNodeInfo == null) {
            releaseProcess();
            return;
        }
        if (TextUtils.equals("android.widget.AbsListView", listViewNodeInfo.getClassName())) {
            int childCount = listViewNodeInfo.getChildCount();
            if (mRecentChatListIndex == childCount - 1) {
                mRecentChatListIndex = 0;
            }
            for (int i = mRecentChatListIndex; i < childCount; i++) {
                AccessibilityNodeInfo listItemNodeInfo = listViewNodeInfo.getChild(i);
                if (TextUtils.equals("android.widget.LinearLayout", listItemNodeInfo.getClassName())) {
                    Log.i(TAG, "openQQGroupChat: find list item node info");
                    listItemNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mRecentChatListIndex = i + 1;
                    nextStep();
                    releaseProcess();
                    return;
                }
            }
        }

//        AccessibilityNodeInfo unreadmsgNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/unreadmsg");
//        if (unreadmsgNodeInfo == null) {
//            return;
//        }
//        AccessibilityNodeInfo listItemNodeInfo = unreadmsgNodeInfo.getParent().getParent().getParent();
//        if ("android.widget.LinearLayout".equals(listItemNodeInfo.getClassName())) {
//            Log.i(TAG, "openQQGroupChat: find list item node info");
//            listItemNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            mCurrentStep++;
//        }
    }

    /**
     * 群聊天页面
     * 2、打开群设置页面
     */
    private void openQQGroupSetting() {
        if (mCurrentStep != 2 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }
        AccessibilityNodeInfo titleNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/title");
        if (titleNodeInfo == null) {
            releaseProcess();
            return;
        }
        CharSequence title = titleNodeInfo.getText();
        Log.i(TAG, "openQQGroupSetting: title: " + title);
        if (!TextUtils.equals(mGroupTitle, title)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            back(true);
            prevStep();
            releaseProcess();
            return;
        }
        //找到目标群，重置序号
        mRecentChatListIndex = 0;
        Log.i(TAG, "openQQGroupSetting: find qq group " + mGroupTitle);
        AccessibilityNodeInfo groupInfoBtnNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/ivTitleBtnRightImage");
        if (groupInfoBtnNodeInfo == null) {
            releaseProcess();
            return;
        }
        groupInfoBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        nextStep();
        releaseProcess();
    }

    /**
     * 3、打开群成员页面,遍历列表，并打开用户资料页面
     */
    private void openQQGroupMember() {
        if (mCurrentStep != 3 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }
        AccessibilityNodeInfo addNodeInfo = Utils.getNodeInfoByIdAndText(rootNodeInfo, "com.tencent.mobileqq:id/title", "群成员");
        if (addNodeInfo == null) {
            releaseProcess();
            return;
        }
        AccessibilityNodeInfo groupMemberNodeInfo = addNodeInfo.getParent();
        if (groupMemberNodeInfo != null) {
            groupMemberNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            nextStep();
        }
        releaseProcess();
    }

    /**
     * 4、打开用户资料页面
     */
    private synchronized void openUserInfoPage() {
        if (mCurrentStep != 4 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);

        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }

        List<AccessibilityNodeInfo> nickNameNodeInfos = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/tv_name");
        if (nickNameNodeInfos == null || nickNameNodeInfos.isEmpty()) {
            releaseProcess();
            return;
        }

        for (AccessibilityNodeInfo nickNameNodeInfo : nickNameNodeInfos) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String nickName = nickNameNodeInfo.getText().toString();
            if (nickNameNodeInfo.getParent() == null || nickNameNodeInfo.getParent().getParent() == null) {
                Log.w(TAG, "openUserInfoPage: nicknameNodeinfo parent is empty ,nickname: " + nickName);
                releaseProcess();
                return;
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

                Log.i(TAG, "openUserInfoPage: nickname: " + nickName);
                mHasOpenUserInfoNick.add(nickName);
                AccessibilityNodeInfo childNodeInfo = listItemNodeInfo.getChild(0);
                // listview 的 NodeInfo 层级比较坑
                childNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                nextStep();
                releaseProcess();
                return;
            }
        }

        final AccessibilityNodeInfo listViewNodeInfo = Utils.getParentByClassName(nickNameNodeInfos.get(0), "android.widget.AbsListView");
        releaseProcess();
        if (listViewNodeInfo == null) {
            return;
        }
        Log.i(TAG, "openUserInfoPage: all nick has send scroll forward");
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
     * 用户资料页面
     * 5、获取 QQ 号码，并打开聊天界面
     */
    private void getQQNumberAndOpenChatPage() {
//        Log.i(TAG, "getQQNumberAndOpenChatPage: " + mCurrentStep);
        if (mCurrentStep != 5 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }

        AccessibilityNodeInfo accountLabelNodeInfo = Utils.getNodeInfoByTextAndClassName(rootNodeInfo, "帐号信息", "android.widget.TextView");
        if (accountLabelNodeInfo == null) {
            releaseProcess();
            return;
        }
        AccessibilityNodeInfo accountParentNodeInfo = accountLabelNodeInfo.getParent();
        if (accountParentNodeInfo == null) {
            releaseProcess();
            return;
        }
        if (TextUtils.equals("android.widget.RelativeLayout", accountParentNodeInfo.getClassName())) {
            String contentDesc = accountParentNodeInfo.getContentDescription().toString().trim();
            String qqNum = parseQQMNumber(contentDesc);
            Log.i(TAG, "getQQNumberAndOpenChatPage: " + qqNum);
            if (mHasSendQqNums.contains(qqNum)) {
                back();
                prevStep();
            } else {
                mCurrentQQNum = qqNum;
                AccessibilityNodeInfo sendMessageBtnNodeInfo = Utils.getNodeInfoByTextAndClassName(rootNodeInfo, "发消息", "android.widget.Button");
                if (sendMessageBtnNodeInfo == null) {
                    return;
                }
                sendMessageBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                nextStep();
            }
        }
        releaseProcess();
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
        if (mCurrentStep != 6 || isProcessing.get()) {
            return;
        }
        isProcessing.set(true);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if (rootNodeInfo == null) {
            releaseProcess();
            return;
        }
        AccessibilityNodeInfo inputNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/input");
        if (inputNodeInfo == null) {
            releaseProcess();
            return;
        }
        if (TextUtils.equals("android.widget.EditText", inputNodeInfo.getClassName())) {
            Utils.setText(this, inputNodeInfo, message);
            final AccessibilityNodeInfo sendBtnNodeInfo = Utils.getNodeInfoById(rootNodeInfo, "com.tencent.mobileqq:id/fun_btn");
            if (sendBtnNodeInfo == null) {
                releaseProcess();
                return;
            }
            if (TextUtils.equals("android.widget.Button", sendBtnNodeInfo.getClassName())) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // TODO: 16/12/26 测试中先注释
//                        sendBtnNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.i(TAG, "run: " + mCurrentQQNum + " send!");
                        mHasSendQqNums.add(mCurrentQQNum);
                        mCurrentStep = 1;
                        back();
                        releaseProcess();
                    }
                }, 1000);
            } else {
                releaseProcess();
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
