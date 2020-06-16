package com.diana.draz.lb3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FragmentShow extends Fragment {

    private final NotesManager notesManager;
    private OnFragmentInteractionListener mListener;
    private NotesRecyclerViewAdapter adapter;

    public FragmentShow(NotesManager notesManager) {
        this.notesManager = notesManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.noteList);
        adapter = new NotesRecyclerViewAdapter(notesManager.getItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
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

    public void refresh() {
        if (adapter != null)
            adapter.notifyItemRangeChanged(0, this.notesManager.getItems().size());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class NotesRecyclerViewAdapter
            extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder> {

        private final List<Note> notes;

        NotesRecyclerViewAdapter(List<Note> notes) {
            this.notes = notes;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(notes.get(position).id));
            holder.mContentView.setText(notes.get(position).title);

            holder.itemView.setTag(notes.get(position));
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.id_title);
            }
        }
    }
}
