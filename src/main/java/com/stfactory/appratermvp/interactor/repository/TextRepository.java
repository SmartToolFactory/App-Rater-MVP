package com.stfactory.appratermvp.interactor.repository;

import android.content.res.Resources;

import com.stfactory.appratermvp.R;

public class TextRepository {
	private Resources mResources;

	public TextRepository(Resources resources) {
		mResources = resources;
	}

	public String getRatingString(int rating) {
		String ratingString = mResources.getString(R.string.app_rater_star_description_5);
		switch (rating) {
		case 1:
			ratingString = mResources.getString(R.string.app_rater_star_description_1);
			break;

		case 2:
			ratingString = mResources.getString(R.string.app_rater_star_description_2);
			break;

		case 3:
			ratingString = mResources.getString(R.string.app_rater_star_description_3);
			break;

		case 4:
			ratingString = mResources.getString(R.string.app_rater_star_description_4);
			break;

		case 5:
			ratingString = mResources.getString(R.string.app_rater_star_description_5);
			break;

		}

		return ratingString;
	}

}
