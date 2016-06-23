/**
 * 
 */

package com.ibm.android.kit.utils.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * @author bahamada
 */
public class AssetFile extends AbstractFile {
	
	StringBuilder buf;
	InputStream input;
	BufferedReader reader;
	
	public AssetFile(Context context) {
	
		super(context);
	}
	
	@Override
	protected void openToRead(String path) throws IOException {
	
		super.openToRead(path);
		
		// create string buffer for the asset file content
		buf = new StringBuilder();
		
		// create input stream to open asset file in
		input = context.getAssets().open(path);
		
		// create reader buffer to load the asset file inpit stream in
		reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
	}
	
	@Override
	protected String readFile() throws IOException {
	
		String str;
		
		// read line by line from asset file
		while ((str = reader.readLine()) != null) {
			buf.append(str);
		}
		
		return buf.toString();
	}
	
	@Override
	protected void writeFile(String content) throws IOException {
	
		throw new IOException("can't write to asset file because assets files are read only");
	}
	
	@Override
	protected void closeRead() throws IOException {
	
		// release reader object
		if (reader != null)
			reader.close();
	}
	
	@Override
	protected void closeWritten() throws IOException {
	
		throw new IOException("can't write to asset file, so can't close it");
		
	}
	
}
