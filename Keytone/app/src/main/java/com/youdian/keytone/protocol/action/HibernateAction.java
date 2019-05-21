package com.youdian.keytone.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author hkq
 */
public class HibernateAction extends RemotePCDroidAction
{
	public HibernateAction()
	{
	}
	
	@Override
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(HIBERNATE_SERVER);
	}
	
	public static HibernateAction parse(DataInputStream dis) throws IOException
	{
		return new HibernateAction();
	}
}
