package com.example.getchromebookmark;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ArrayList<BookmarkList> objBookmarkList = new ArrayList<BookmarkList>();
	private TextView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test = (TextView) findViewById(R.id.text);
		getBookmark();
		displayList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getBookmark() {
		String[] strBookmarkProjection = new String[]{Browser.BookmarkColumns.BOOKMARK, Browser.BookmarkColumns.CREATED, Browser.BookmarkColumns.DATE, Browser.BookmarkColumns.FAVICON, Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL, Browser.BookmarkColumns.VISITS};
		Cursor objBrowser = getContentResolver().query(Browser.BOOKMARKS_URI, strBookmarkProjection, Browser.BookmarkColumns.BOOKMARK + "=1", null, null);
		final int intUrlIndex = objBrowser.getColumnIndex(Browser.BookmarkColumns.URL);
		final int intTitleIndex = objBrowser.getColumnIndex(Browser.BookmarkColumns.TITLE);
		final int intVisitsIndex = objBrowser.getColumnIndex(Browser.BookmarkColumns.VISITS);
		if (objBrowser.moveToFirst()) {
			do {
				// レコード毎の処理
				String strTitle = objBrowser.getString(intTitleIndex);
				String strUrl = objBrowser.getString(intUrlIndex);
				String strVisits = objBrowser.getString(intVisitsIndex);
				objBookmarkList.add(new BookmarkList(strTitle, strUrl,strVisits));
			} while (objBrowser.moveToNext());
		}
		objBrowser.close();
	}

	private void displayList() {
		String alltext = new String();
		for (BookmarkList bl : objBookmarkList) {
			String line = /*bl.getTitle() + "," + bl.getUrl() + "," + */bl.getVisits() + "\n";
			alltext += line;
		}
		test.setText(alltext);
	}

	class BookmarkList {
		private String title;
		private String url;
		private String visits;
		public BookmarkList(String title, String url,String visits) {
			this.setTitle(title);
			this.setUrl(url);
			this.setVisits(visits);
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getVisits() {
			return visits;
		}
		public void setVisits(String visits) {
			this.visits = visits;
		}
	}
}
