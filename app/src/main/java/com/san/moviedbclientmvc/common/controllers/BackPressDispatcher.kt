package com.san.moviedbclientmvc.common.controllers

interface BackPressDispatcher {
    fun registerListener(listener: BackPressedListener?)
    fun unregisterListener(listener: BackPressedListener?)
}