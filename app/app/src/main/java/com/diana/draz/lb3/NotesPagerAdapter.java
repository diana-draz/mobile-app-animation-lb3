package com.diana.draz.lb3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NotesPagerAdapter extends FragmentPagerAdapter {
    static final int PAGE_COUNT = 4;
    private final NotesManager notesManager;

    public NotesPagerAdapter(FragmentManager fm, NotesManager notesManager) {
        super(fm);
        this.notesManager = notesManager;
    }
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new FragmentShow(this.notesManager);
            case 1: return new FragmentInsert(this.notesManager);
            case 2: return new FragmentUpdate(this.notesManager);
            case 3: return new FragmentDelete(this.notesManager);
            default: return null;
        }
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        switch (i){
            case 0: return "Показать";
            case 1: return "Вставить";
            case 2: return "Обновить";
            case 3: return "Удалить";
            default: return null;
        }
    }
}