package com.rnd.mobilepayment;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnd.mobilepayment.general.HistoryFragment;
import com.rnd.mobilepayment.general.PrinterSettingFragment;
import com.rnd.mobilepayment.pln.PLNNonTaglisFragment;
import com.rnd.mobilepayment.pln.PLNPostpaidFragment;
import com.rnd.mobilepayment.pln.PLNPrepaidFragment;
import com.rnd.mobilepayment.preferences.SessionManager;
import com.rnd.mobilepayment.utils.MobilePayments;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private CharSequence mSubsTitle = "username";

	/**
	 * IMEI used to binding device to service.
	 */
	@SuppressWarnings("unused")
	private static String IMEI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * init
		 */
		SessionManager.getInstance().getSession().checkLogin();

		// Get username
		HashMap<String, String> user = SessionManager.getInstance()
				.getSession().getUserDetails();
		mSubsTitle = user.get("username");

		IMEI = MobilePayments.getIMEI_ID();

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		// FragmentTransaction ft =
		// getSupportFragmentManager().beginTransaction();
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			// Fragment homeFragment = new HomeFragment();
			// ft.replace(R.id.container, homeFragment);
			fragmentManager.beginTransaction()
					.replace(R.id.container, new HomeFragment()).commit();
			onSectionAttached(0);
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new PLNPostpaidFragment())
					.commit();
			onSectionAttached(2);
			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new PLNPrepaidFragment()).commit();
			onSectionAttached(3);
			break;
		case 4:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new PLNNonTaglisFragment())
					.commit();
			onSectionAttached(4);
			break;

		case 7:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new HistoryFragment()).commit();
			onSectionAttached(7);
			break;

		default:
			// FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							PlaceholderFragment.newInstance(position)).commit();
			break;
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 0:
			mTitle = "HOME";
			break;
		case 2:
			mTitle = "PLN - POSTPAID";
			break;
		case 3:
			mTitle = "PLN - PREPAID";
			break;
		case 4:
			mTitle = "PLN - NON TAGIHAN";
			break;

		case 7:
			mTitle = "HISTORY TRANSAKSI";
			break;
		default:
			mTitle = "" + number;
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		// set username
		actionBar.setSubtitle(mSubsTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// ActionBar Menu
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
//			Intent myIntent = new Intent(MainActivity.this,PrinterSettingFragment.class);
//			startActivity(myIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
