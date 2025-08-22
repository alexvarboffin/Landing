package run.kinobay.android.presenter

abstract class AbstractPresenter(@JvmField protected val mView: MainContract.View) :
    MainContract.Presenter
