package com.youdian.soundeffects.remotepcdroidserver;

import com.youdian.soundeffects.RemotePCDroidServerApp;
import com.youdian.soundeffects.protocol.tcp.RemotePCDroidConnectionTcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hkq
 */
public class RemotePCDroidServerTcp extends RemotePCDroidServer implements Runnable
{
	private ServerSocket serverSocket;
	
	public RemotePCDroidServerTcp(RemotePCDroidServerApp application) throws IOException
	{
		super(application);
		
		int port = this.application.getPreferences().getInt("port", RemotePCDroidConnectionTcp.DEFAULT_PORT);
		this.serverSocket = new ServerSocket(port);
		
		(new Thread(this)).start();
	}
	
	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				Socket socket = this.serverSocket.accept();
				RemotePCDroidConnectionTcp connection = new RemotePCDroidConnectionTcp(socket);
				new RemotePCDroidServerConnection(this.application, connection);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void close()
	{
		try
		{
			this.serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
