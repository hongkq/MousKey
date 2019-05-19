package com.youdian.soundeffects.protocol.tcp;

import com.youdian.soundeffects.protocol.RemotePCDroidConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author hkq
 */
public class RemotePCDroidConnectionTcp extends RemotePCDroidConnection
{
	public final static int DEFAULT_PORT = 6666;
	
	private Socket socket;
	
	public RemotePCDroidConnectionTcp(Socket socket) throws IOException
	{
		super(socket.getInputStream(), socket.getOutputStream());
		
		this.socket = socket;
		this.socket.setPerformancePreferences(0, 2, 1);
		this.socket.setTcpNoDelay(true);
		this.socket.setReceiveBufferSize(1024 * 1024);
		this.socket.setSendBufferSize(1024 * 1024);
	}
	
	public static RemotePCDroidConnectionTcp create(String host, int port) throws IOException
	{
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, port), 1000);
		
		RemotePCDroidConnectionTcp connection = new RemotePCDroidConnectionTcp(socket);
		
		return connection;
	}
	
	public InetAddress getInetAddress()
	{
		return this.socket.getInetAddress();
	}
	
	public int getPort()
	{
		return this.socket.getPort();
	}
	
	@Override
	public void close() throws IOException
	{
		this.socket.shutdownInput();
		this.socket.shutdownOutput();
		super.close();
		this.socket.close();
	}
}
