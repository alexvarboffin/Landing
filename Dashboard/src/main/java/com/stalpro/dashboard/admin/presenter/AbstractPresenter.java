package com.stalpro.dashboard.admin.presenter;

public abstract class AbstractPresenter implements MainContract.Presenter{

    protected final MainContract.View mView;

    public AbstractPresenter(MainContract.View view) {
        this.mView = view;
    }
}
