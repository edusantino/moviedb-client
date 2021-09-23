package com.san.moviedbclientmvc.common.controllers

interface BackPressedListener {
    /**
     *
     * @return true if the listener handled the back press; false otherwise
     */
    fun onBackPressed(): Boolean
}