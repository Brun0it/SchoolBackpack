package com.theog.schoolbackpack.ui.devoirs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevoirsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DevoirsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}