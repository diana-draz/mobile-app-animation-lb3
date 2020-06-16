package com.diana.draz.lb3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentDelete extends Fragment implements View.OnClickListener {

    private final NotesManager notesManager;
    private OnFragmentInteractionListener mListener;
    private EditText _idEditText;
    private Button button;


    public FragmentDelete(NotesManager notesManager) {
        this.notesManager = notesManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_delete, container, false);
        button = view.findViewById(R.id.delete_button);
        _idEditText = view.findViewById(R.id.delete_id);
        button.setOnClickListener(this);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v == button) {

            int id = 0;
            try {
                id = Integer.parseInt(_idEditText.getText().toString());
            } catch (Exception e) {
                Toast.makeText( getActivity(), "Идентификатор должен быть целым числом", Toast.LENGTH_SHORT).show();
                return;
            }

            if (this.notesManager.getItem(id) == null) {
                Toast.makeText( getActivity(), "В базе данных не обнаружено записи с таким идентификатором", Toast.LENGTH_SHORT).show();
                return;
            }


            try {
                this.notesManager.delete(id);
                _idEditText.setText("");
                Toast.makeText(getActivity(), "Заметка успешно удалена.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Произошла ошибка при удалении", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
