package org.apache.mvp


interface CompatView {
    fun orientation404(): Int? //orientationGame

    fun orientationWeb(): Int?

    //boolean checkUpdate();
    fun webTitle(): Boolean
}