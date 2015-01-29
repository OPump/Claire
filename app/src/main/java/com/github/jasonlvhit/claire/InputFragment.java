package com.github.jasonlvhit.claire;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jason Lyu on 2015/1/27.
 */
public class InputFragment extends DialogFragment {
    private static InputFragment object = null;
    public static String mDialogTitle = null;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_edit, null);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(mDialogTitle)
                .setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = ((EditText) v
                                        .findViewById(R.id.input)).getText().toString();

                                sendResult(Activity.RESULT_OK, input);
                            }
                        }
                )
                .create();
    }

    private void sendResult(int resultCode, String input){
        if(getTargetFragment() == null) return;
        Intent i = new Intent();

        i.putExtra("INPUT", input);
        i.putExtra("TYPE", mDialogTitle);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);

    }

    public static InputFragment newInstance(String title){
        if(object == null){
            object = new InputFragment();
            object.mDialogTitle = title;
            return object;
        }
        else return object;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        object = null;
        ((MainActivity)getActivity()).setInDialog(false);
    }
}
