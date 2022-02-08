package com.softwares.techvibez.letsdate;

import androidx.recyclerview.widget.DiffUtil;

import com.softwares.techvibez.letsdate.models.TestModel;

import java.util.List;

public class SpotsDiffCallback extends DiffUtil.Callback {
    private List<TestModel> old;
    private List<TestModel> latest;

    public SpotsDiffCallback(List<TestModel> old, List<TestModel> latest) {
        this.old = old;
        this.latest = latest;
    }

    public List<TestModel> getOld() {
        return old;
    }

    public void setOld(List<TestModel> old) {
        this.old = old;
    }

    public List<TestModel> getLatest() {
        return latest;
    }

    public void setLatest(List<TestModel> latest) {
        this.latest = latest;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return latest.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return old.get(i) == latest.get(i1);
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return old.get(i) == latest.get(i1) ;
    }
}
