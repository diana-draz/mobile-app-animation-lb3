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

public class FragmentUpdate extends Fragment implements View.OnClickListener {
    private final NotesManager notesManager;
    private OnFragmentInteractionListener mListener;
    private Button updateButton;
    private EditText updateIdEditText;
    private EditText updateTitleEditText;

    public FragmentUpdate(NotesManager notesManager) {
        this.notesManager = notesManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        updateButton = view.findViewById(R.id.update_button);
        updateIdEditText = view.findViewById(R.id.update_note_id);
        updateTitleEditText = view.findViewById(R.id.update_note_title);
        updateButton.setOnClickListener(this);

        return view;
    }

   public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == updateButton) {
            String idText = updateIdEditText.getText().toString();
            int id = 0;
            try {
                id = Integer.parseInt(idText);
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Идентификатор должен быть целым числом.", Toast.LENGTH_LONG).show();
                return;
            }

            String title =updateTitleEditText.getText().toString();
            if (title.length() == 0) {
                Toast.makeText(getActivity(), "Описание заметки не может быть пустым.", Toast.LENGTH_LONG).show();
                return;
            }

            if (this.notesManager.getItem(id) == null) {
                Toast.makeText(getActivity(), "В списке не содержится заметок с таким идентификатором.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                this.notesManager.update(new Note(id, title));
                Toast.makeText(getActivity(), "Заметка успешно обновлена.", Toast.LENGTH_LONG).show();
                updateIdEditText.setText("");
                updateTitleEditText.setText("");
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Произошла ошибка при обновлении заметки.", Toast.LENGTH_LONG).show();
            }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
