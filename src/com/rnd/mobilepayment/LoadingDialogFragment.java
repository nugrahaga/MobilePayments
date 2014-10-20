package com.rnd.mobilepayment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class LoadingDialogFragment extends DialogFragment {

	public static LoadingDialogFragment newInstance() {
		return new LoadingDialogFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateDialog(savedInstanceState);
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(("Loading"));

		return dialog;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);
		triggerActivityListener();
	}
	
	private void triggerActivityListener() {
        if(getActivity() instanceof OnDialogClosedListener) {
            OnDialogClosedListener listener = (OnDialogClosedListener) getActivity();
            listener.onDialogClosed();
        }
    }

    public interface OnDialogClosedListener {
        public void onDialogClosed();
    }
}
