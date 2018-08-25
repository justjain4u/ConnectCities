package com.java.connected.cities.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * CustomFileLoader class responsible of loading data file.
 * 
 * */
public class CustomFileLoaderService {
	/**
	 * load data file .
	 * 
	 * @param filePath
	 *            data file path.
	 * @return {@link BufferedReader} referencing loaded data.
	 * */
	public BufferedReader loadFile(final String filePath) {
		File dataFile = new File(filePath);
		checkFile(dataFile);
		MappedByteBuffer mByteBuffer = loadDataFile(dataFile);
		return getBufferedReader(mByteBuffer);
	}

	protected final MappedByteBuffer loadDataFile(File dataFile) {
		FileInputStream fInputStream = null;
		MappedByteBuffer mBytebuffer = null;
		FileChannel fChannel = null;
		try {
			fInputStream = new FileInputStream(dataFile);
			fChannel = fInputStream.getChannel();
			mBytebuffer = fChannel.map(MapMode.READ_ONLY, 0, fChannel.size());
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("File is having IO error.");
		} finally {
			closeResource(fInputStream, fChannel);
		}
		return mBytebuffer;
	}

	/**
	 * @return {@link BufferedReader} to read through {@link MappedByteBuffer}
	 * */
	protected final BufferedReader getBufferedReader(
			final MappedByteBuffer mByteBuffer) {
		byte[] buffer = new byte[mByteBuffer.limit()];
		mByteBuffer.get(buffer);
		ByteArrayInputStream isr = new ByteArrayInputStream(buffer);
		InputStreamReader ip = new InputStreamReader(isr);
		return new BufferedReader(ip);
	}

	/**
	 * Close file input stream quietly.
	 * 
	 * @param fis
	 *            file stream to close.
	 * */
	protected final void closeResource(final FileInputStream fis,
			final FileChannel fChannel) {
		try {
			fis.close();
			fChannel.close();
		} catch (IOException ex) {
			System.out.println("File is having IO error.");
		}

	}

	/**
	 * Checks if the file exist or not, empty or not. Incase of file doesn't
	 * exist a file doesn't exist will
	 * 
	 * @param targetFile
	 *            file to check.
	 * */
	protected final void checkFile(final File file) {
		if (!file.exists()) {
			System.out.println(file.getName() + "FILE_NOT_FOUND");
		}
		if (file.length() == 0) {
			System.out.println(file.getName() + "FILE_IS_EMPTY");
		}
	}

}
