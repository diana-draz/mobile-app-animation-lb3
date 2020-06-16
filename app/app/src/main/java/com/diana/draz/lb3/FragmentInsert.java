package com.diana.draz.lb3;

import android.content.Context;
import android.content.RestrictionEntry;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentInsert extends Fragment implements View.OnClickListener {

    private final NotesManager notesManager;

    private Button insertButton;
    private EditText insertIdEditText;
    private EditText insertTitleEditText;

    private OnFragmentInteractionListener mListener;

    public FragmentInsert(NotesManager notesManager) {
        this.notesManager = notesManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        insertButton = view.findViewById(R.id.insert_button);
        insertIdEditText = view.findViewById(R.id.insert_note_id);
        insertTitleEditText = view.findViewById(R.id.insert_note_title);
        insertButton.setOnClickListener(this);
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
        if (v == insertButton) {
            String idText = insertIdEditText.getText().toString();
            int id = 0;
            try {
                id = Integer.parseInt(idText);
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Идентификатор должен быть целым числом.", Toast.LENGTH_LONG).show();
                return;
            }

            String title =insertTitleEditText.getText().toString();
            if (title.length() == 0) {
                Toast.makeText(getActivity(), "Описание заметки не может быть пустым.", Toast.LENGTH_LONG).show();
                return;
            }

            if (this.notesManager.getItem(id) != null) {
                Toast.makeText(getActivity(), "В списке заметок уже есть записка с таким идентификатором.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                this.notesManager.insert(new Note(id, title));
                Toast.makeText(getActivity(), "Заметка успешно добавлена.", Toast.LENGTH_LONG).show();
                insertIdEditText.setText("");
                insertTitleEditText.setText("");
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "Произошла ошибка при добавлении заметки.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
