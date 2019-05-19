package com.youdian.soundeffects.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author hkq
 */
public class FileExploreRequestAction extends RemotePCDroidAction
{
	public String directory;
	public String file;
	
	public FileExploreRequestAction(String directory, String file)
	{
		this.directory = directory;
		this.file = file;
	}
	
	public static FileExploreRequestAction parse(DataInputStream dis) throws IOException
	{
		String directory = dis.readUTF();
		String file = dis.readUTF();
		
		return new FileExploreRequestAction(directory, file);
	}
	
	@Override
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(FILE_EXPLORE_REQUEST);
		dos.writeUTF(this.directory);
		dos.writeUTF(this.file);
	}
}
