package com.torres.toni.bakingapp.pojo;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by Toni on 11/08/2018.
 */

public class WebServiceResponse {

    MutableLiveData<Status> mStatus = new MutableLiveData<>();

    public enum Status{
        SUCCESS, FAILURE, LOADING
    }

    public MutableLiveData<Status> getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus.setValue(status);
    }
}
