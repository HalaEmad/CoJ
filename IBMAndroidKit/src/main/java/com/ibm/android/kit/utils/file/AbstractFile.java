package com.ibm.android.kit.utils.file;

import java.io.IOException;

import android.content.Context;

public abstract class AbstractFile {

	protected String path;
	protected Context context;

	public AbstractFile(Context context) {
		this.context = context;
	}

	public String read(String path) throws IOException {
		openToRead(path);
		String content = readFile();
		closeRead();
		
		return content;
	}

	public void write(String path, String content) throws IOException {
		openToWrite(path);
		writeFile(content);
		closeWritten();
	}

	protected void openToRead(String path) throws IOException {
		this.path = path;
	}

	protected void openToWrite(String path) throws IOException {
		this.path = path;
	}

	protected abstract String readFile() throws IOException;
	protected abstract void writeFile(String content) throws IOException;
	
	protected abstract void closeRead() throws IOException;
	protected abstract void closeWritten() throws IOException;
}
