package com.joannedeng.astrodoodle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.joannedeng.framework.Screen;
import com.joannedeng.framework.implementation.AndroidGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.joannedeng.astrodoodle.R;
import android.util.Log;

public class SampleGame extends AndroidGame {

	@Override
	public Screen getInitScreen() {

		return new SplashLoadScreen(this);

	}

	@Override
	// when the user presses the back button,
	// whatever is defined in the current screen's backButton() method is
	// called.
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

}
