package com.danmo.commonutil.dialog;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.base.dialog.BaseDialogFragment;
import com.base.dialog.DialogLayoutCallback;
import com.luck.picture.lib.R;


/**
 * <pre>
 *
 * </pre>
 */
public class CommonDialogLoading extends BaseDialogFragment {

    public CommonDialogLoading init(FragmentActivity activity, final Runnable onCancelListener) {
        super.init(activity, new DialogLayoutCallback() {
            @Override
            public int bindTheme() {
                return R.style.CommonLoadingDialogStyle;
            }

            @Override
            public int bindLayout() {
                return R.layout.common_dialog_loading;
            }

            @Override
            public void initView(BaseDialogFragment dialog, View contentView) {
                if (onCancelListener == null) {
                    setCancelable(false);
                } else {
                    setCancelable(true);
                }
            }

            @Override
            public void setWindowStyle(Window window) {
            }

            @Override
            public void onCancel(BaseDialogFragment dialog) {
                if (onCancelListener != null) {
                    onCancelListener.run();
                }
            }

            @Override
            public void onDismiss(BaseDialogFragment dialog) {

            }
        });
        return this;
    }
}
