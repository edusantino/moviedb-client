package com.san.moviedbclientmvc.common.controllers;

public interface BackPressedListener {
    /**
     *
     * @return true if the listener handled the back press; false otherwise
     */
    boolean onBackPressed();
}
