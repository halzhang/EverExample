package com.halzhang.android.examples.accessibilityexample;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Hal on 16/12/16.
 */

public class Utils {


    public static AccessibilityNodeInfo getNodeInfoById(AccessibilityNodeInfo parent, String viewId) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByViewId(viewId);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        return nodeInfos.get(0);
    }

    public static AccessibilityNodeInfo getNodeInfoByIdAndContentDesc(AccessibilityNodeInfo parent, String viewId, String contentDesc) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByViewId(viewId);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
            if (TextUtils.equals(contentDesc, nodeInfo.getContentDescription())) {
                return nodeInfo;
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo getNodeInfoByIdAndText(AccessibilityNodeInfo parent, String viewId, String text) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByViewId(viewId);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
            CharSequence desc = nodeInfo.getText();
            if (TextUtils.equals(text, desc)) {
                return nodeInfo;
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo getNodeInfoByIdAndClassName(AccessibilityNodeInfo parent, String viewId, String className) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByViewId(viewId);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
            if (TextUtils.equals(className, nodeInfo.getClassName())) {
                return nodeInfo;
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo getNodeInfoByText(AccessibilityNodeInfo parent, String text) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByText(text);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        return nodeInfos.get(0);
    }

    public static AccessibilityNodeInfo getNodeInfoByTextAndClassName(AccessibilityNodeInfo parent, String text, String className) {
        List<AccessibilityNodeInfo> nodeInfos = parent.findAccessibilityNodeInfosByText(text);
        if (nodeInfos == null || nodeInfos.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
            if (TextUtils.equals(className, nodeInfo.getClassName())) {
                return nodeInfo;
            }
        }
        return null;
    }

    /**
     * 设置文本
     */
    public static void setText(Context context, AccessibilityNodeInfo node, String reply) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle args = new Bundle();
            args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    reply);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
        } else {
            ClipData data = ClipData.newPlainText("reply", reply);
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(data);
            node.performAction(AccessibilityNodeInfo.ACTION_FOCUS); // 获取焦点
            node.performAction(AccessibilityNodeInfo.ACTION_PASTE); // 执行粘贴
        }
    }

    public static AccessibilityNodeInfo getParentByClassName(@NonNull AccessibilityNodeInfo childNodeInfo, String className) {
        AccessibilityNodeInfo parent = childNodeInfo.getParent();
        if (parent == null) {
            return null;
        }
        if (TextUtils.equals(className, parent.getClassName())) {
            return parent;
        } else {
            return getParentByClassName(parent, className);
        }
    }
}
