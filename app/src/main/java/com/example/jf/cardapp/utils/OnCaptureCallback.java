package com.example.jf.cardapp.utils;


import android.graphics.Bitmap;

public interface OnCaptureCallback {

	public void onCapture(boolean success, Bitmap bitmap);
}