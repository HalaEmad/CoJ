package com.ibm.android.kit.utils.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;

public class LocalFile extends AbstractFile {

	private String fileName;
	
	private FileOutputStream outputStream;
	
	private BufferedReader reader;
	private StringBuffer fileContent;

	public LocalFile(Context context) {
		super(context);
	}

	@Override
	protected void openToRead(String path) throws IOException {
		super.openToRead(path);

		FileInputStream fis;
		fileContent = new StringBuffer("");
		
		fileName = path.substring(path.lastIndexOf("/") + 1);
		
		fis = context.openFileInput(fileName);
		reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(fis,
					/* "Windows-1252" */"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			try {
				reader = new BufferedReader(new InputStreamReader(fis));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void openToWrite(String path) throws IOException {
		super.openToWrite(path);

		fileName = path.substring(path.lastIndexOf("/") + 1);

		outputStream = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
	}

	@Override
	public String readFile() throws IOException {
		String content = null;
		int ch;
		
		while ((ch = reader.read()) != -1)
			fileContent.append((char) ch);

		content = new String(fileContent);
		// AkLog.i("CMS", "Reading " + filename);
		return content;
	}

	@Override
	public void writeFile(String content) throws IOException {

		outputStream.write(content.getBytes("UTF-8"/* "Windows-1252" */));
	}

	@Override
	protected void closeRead() throws IOException {
		reader.close();
	}

	@Override
	protected void closeWritten() throws IOException {
		outputStream.close();
	}

}
