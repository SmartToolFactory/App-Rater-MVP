package com.stfactory.appratermvp.interactor.preferences;

public interface IPrefs {
	
	boolean isShowNever();
	
	int getUseCount();

	long getUseStartDate();
	
	void saveShowNever(boolean showNever);

	void saveFirstStartDate(long date);

	void saveUseCount(int useCount);
}
