package com.googlecode.jtiger.modules.ecside.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PackUtils {

	public static int BUFFER_SIZE = 2048;
	public static void packGZIP(String inFilePath, String outFilePath, String key)
			throws IOException {
		
		byte[] keyByte=getKeyBytes(key);
		int skip=keyByte.length;
		
		
		FileInputStream fileIn= new FileInputStream(inFilePath);
		FileChannel fcin = fileIn.getChannel();
		FileOutputStream fileOut= new FileOutputStream(outFilePath);
		
		if (skip>0){
			fileOut.write(keyByte);
		}
		
		GZIPOutputStream gzipOut = new GZIPOutputStream(fileOut);

		WritableByteChannel fcout = Channels.newChannel(gzipOut);

		ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		


		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}
		fcout.close();
		fcin.close();
	}

	public static void unpackGZIP(String inFilePath, String outFilePath,String key) throws IOException {
		
		GZIPInputStream gzipIn = unpackGZIP(inFilePath,key);
		ReadableByteChannel fcin = Channels.newChannel(gzipIn);
		
		FileOutputStream fileOut= new FileOutputStream(outFilePath);
		FileChannel fcout = fileOut.getChannel();

		
		ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}
		
		fcout.close();
		fcin.close();
	}

	public static GZIPInputStream unpackGZIP(String inFilePath, String key) throws IOException {

		return unpackGZIP(new FileInputStream(inFilePath),key);

	}
	
	public static GZIPInputStream unpackGZIP(InputStream  fileIn, String key) throws IOException {

		byte[] keyByte=getKeyBytes(key);
		int skip=keyByte.length;
		
		fileIn.skip(skip);
		
		GZIPInputStream gzipIn = new GZIPInputStream(fileIn);
		
		return gzipIn;

	}
	
	public static byte[] getKeyBytes(String key){
		
		if (key==null || key.length()<1){
			return new byte[]{};
		}
		
		byte[] keyBytes=null;
		try {
			keyBytes=key.getBytes("UNICODE");
		} catch (UnsupportedEncodingException e) {
			keyBytes=key.getBytes();
		}
		
		reverse(keyBytes);
		
		return keyBytes;
	}
	
	public static void reverse(byte[] array) {

		if (array == null || array.length<1) {
			return;
		}
		
		int sp=3;
		int start1=0;
		int end2=array.length;
		int end1=end2/sp;
		int start2=end1;
		
		start2++;
		
		for (int j = end1 - 1; j > start1; start1++) {
			byte tmp = array[j];
			array[j] = array[start1];
			array[start1] = tmp;
			j--;
		}
		
		for (int j = end2 - 1; j > start2; start2++) {
			byte tmp = array[j];
			array[j] = array[start2];
			array[start2] = tmp;
			j--;
		}

	}
	
}
