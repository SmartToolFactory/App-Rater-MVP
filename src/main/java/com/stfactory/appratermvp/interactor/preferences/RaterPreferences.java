package com.stfactory.appratermvp.interactor.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class RaterPreferences implements IPrefs {

	private static String NEVER_SHOW = "show_never";
	private static String USE_COUNT = "use_count";
	private static String USE_START_DATE = "use_start_date";

	private SharedPreferences mSharedPreferences;

	public RaterPreferences(SharedPreferences prefs) {
		mSharedPreferences = prefs;
	}

	@Override
	public boolean isShowNever() {
		return mSharedPreferences.getBoolean(NEVER_SHOW, false);
	}

	@Override
	public int getUseCount() {
		return mSharedPreferences.getInt(USE_COUNT, 0);
	}

	@Override
	public long getUseStartDate() {
		return mSharedPreferences.getLong(USE_START_DATE, 0);
	}

	@Override
	public void saveShowNever(boolean showNever) {
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(NEVER_SHOW, showNever);
		editor.apply();
	}

	@Override
	public void saveUseCount(int useCount) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(USE_COUNT, useCount);
		editor.apply();

	}

	@Override
	public void saveFirstStartDate(long date) {
		Editor editor = mSharedPreferences.edit();
		editor.putLong(USE_START_DATE, date);
		editor.apply();
	}

}
