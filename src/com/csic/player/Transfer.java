package com.csic.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Transfer {
	
	public void opt(String path)throws Exception
	{
		File file = new File(path);
		
		try {
			RandomAccessFile  raf = new RandomAccessFile(file,"rw");
			long len = raf.length();
			byte []buffer = new byte[(int) len];
			raf.read(buffer);
			for(int i=0;i<buffer.length;i++)
			{
				buffer[i]=(byte)(~buffer[i]);
			}
			raf.seek(0);
			raf.write(buffer);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void halfopt(String path)throws Exception
	{
		File file = new File(path);
		
		try {
			RandomAccessFile  raf = new RandomAccessFile(file,"rw");
			long len = raf.length();
			long halflen = len/2;
			byte []buffer = new byte[(int) halflen];
			raf.read(buffer);
			for(int i=0;i<buffer.length;i++)
			{
				buffer[i]=(byte)(~buffer[i]);
			}
			raf.seek(0);
			raf.write(buffer);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
