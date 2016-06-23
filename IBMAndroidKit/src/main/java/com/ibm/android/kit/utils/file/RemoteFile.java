package com.ibm.android.kit.utils.file;

import android.content.Context;

import com.ibm.android.kit.utils.GeneralUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RemoteFile extends AbstractFile {

	public RemoteFile(Context context) {
		super(context);
	}

	private HttpURLConnection urlConnection;

	@Override
	protected void openToRead(String path) throws IOException {
		super.openToRead(path);

		URL url = new URL(path);

		// create the new connection
		if (path.contains("https"))
			urlConnection = (HttpsURLConnection) url.openConnection();
		else
			urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setReadTimeout(20000);

		// set up some things on the connection
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Cache-Control", "no-cache");
		urlConnection.setRequestProperty("Pragma", "no-cache");

		// and connect!
		urlConnection.connect();
	}

	@Override
	protected void openToWrite(String path) throws IOException {
		super.openToWrite(path);
	}

	@Override
	public String readFile() throws IOException {
		InputStream inputStream = urlConnection.getInputStream();

		String content = GeneralUtility.convertStreamToString(inputStream);

		return content;
	}

	@Override
	public void writeFile(String content) {

	}

	@Override
	protected void closeRead() throws IOException {
		urlConnection.disconnect();
	}

	@Override
	protected void closeWritten() throws IOException {
		// TODO Auto-generated method stub

	}

}
