package org.hopto.tiempoplaya.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogFindActivity extends DialogFragment {

    private EditText editTextInputSearch;
    private Button buttonOK;

    public interface OnInputSearchTextListener{
        void sendInputText(String textToSearch);
    }

    public OnInputSearchTextListener onInputSearchTextListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dialog, container, false);

        //rounded corners
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editTextInputSearch = view.findViewById(R.id.et_dialog_search_beach_by_name);
        buttonOK = view.findViewById(R.id.tv_dialog_seach_ok);

        //rounded buttons
        buttonOK.setBackgroundResource(R.drawable.custom_trans_button_tiempoplaya);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("DIALOG", "dialog OK");

                String beachToSearch = editTextInputSearch.getText().toString();
                onInputSearchTextListener.sendInputText(beachToSearch);

                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSearchTextListener = (OnInputSearchTextListener) getActivity();
        }catch(ClassCastException e){
            //Log.d("ERRNO: ", e.toString());
        }
    }
}
