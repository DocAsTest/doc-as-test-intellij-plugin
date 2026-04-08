package com.github.docastest.plugin.intellij.action;

import com.github.docastest.plugin.intellij.approvalFile.ApprovalFile;

public class SwitchToReceivedFileAction extends SwitchToFileAction {

    public SwitchToReceivedFileAction() {
        super(ApprovalFile.Status.RECEIVED);
    }

    @Override
    protected String getMenuText() {
        return "Switch to received file";
    }


}
