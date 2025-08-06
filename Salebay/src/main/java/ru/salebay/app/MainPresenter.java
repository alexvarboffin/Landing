package ru.salebay.app;

public class MainPresenter {

    private final MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void onSplashTimeout() {
        view.showMainContent();
    }

    public interface MainView {
        void showMainContent();
    }
}

