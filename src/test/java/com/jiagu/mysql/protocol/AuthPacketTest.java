package com.jiagu.mysql.protocol;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.jiagu.mysql.protocol.util.HexUtil;
import com.jiagu.mysql.protocol.util.SecurityUtil;
/**
 * 
 * <pre><b>test auth packet.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 */
public class AuthPacketTest {

	private final static byte[] hex = "0123456789ABCDEF".getBytes();

	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	@Test
	public void produce() {
		// handshake packet's rand1 and rand2
//		byte[] rand1 = RandomUtil.randomBytes(8);
//		byte[] rand2 = RandomUtil.randomBytes(12);
		byte[] rand1 = {0x01,0x1f,0x76,0x7a,0x57,0x63,0x12,0x14};
		byte[] rand2 = {0x66,0x72,0x4a,0x2d,0x4f,0x25,0x03,0x59,0x66,0x14,0x4b,0x59};

		byte[] seed = new byte[rand1.length + rand2.length];
		System.arraycopy(rand1, 0, seed, 0, rand1.length);
		System.arraycopy(rand2, 0, seed, rand1.length, rand2.length);

		AuthPacket auth = new AuthPacket();
		auth.packetId = 1;
		auth.clientFlags = getClientCapabilities();
		auth.maxPacketSize = 1024 * 1024 * 1024;
		auth.user = "root";
		try {
			auth.password = SecurityUtil
					.scramble411("tdlab401".getBytes(), seed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		auth.database = "data";
        
		ByteBuffer buffer = ByteBuffer.allocate(256);
		auth.write(buffer);
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes, 0, bytes.length);
		String result = HexUtil.Bytes2HexString(bytes);
		System.out.println(result);
		assertTrue(Integer.valueOf(result.substring(0, 2), 16) == result
				.length() / 2 - 4);
		
		AuthPacket auth2 = new AuthPacket();
		auth2.read(bytes);
		assertTrue(auth2.database.equals("data"));
	}

	public byte[] produceT() {
		byte[] rand1 = {0x01,0x1f,0x76,0x7a,0x57,0x63,0x12,0x14};
		byte[] rand2 = {0x66,0x72,0x4a,0x2d,0x4f,0x25,0x03,0x59,0x66,0x14,0x4b,0x59};

		byte[] seed = new byte[rand1.length + rand2.length];
		System.arraycopy(rand1, 0, seed, 0, rand1.length);
		System.arraycopy(rand2, 0, seed, rand1.length, rand2.length);

		AuthPacket auth = new AuthPacket();
		auth.packetId = 1;
		auth.clientFlags = getClientCapabilities();
		auth.maxPacketSize = 1024 * 1024 * 1024;
		auth.user = "root";
		try {
			auth.password = SecurityUtil
					.scramble411("tdlab401".getBytes(), seed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		auth.database = "data";

		ByteBuffer buffer = ByteBuffer.allocate(256);
		auth.write(buffer);
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes, 0, bytes.length);
		return bytes;
	}

	@Test
	public void sendMessage() throws Exception{
		InputStream inputStreams = null;
		OutputStream outputStream = null;
		//三次握手建立连接
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("192.168.43.97", 3306));

		//接受handshake包
		inputStreams = socket.getInputStream();
		byte[] bytesTemp = new byte[5000];

		int len = inputStreams.read(bytesTemp);
		byte[] bytes = new byte[len];
		System.arraycopy(bytesTemp,0,bytes,0,len);
		System.out.println(Bytes2HexString(bytes));

		//发送auth packet
		outputStream = socket.getOutputStream();
		outputStream.write(produceT());
		socket.shutdownOutput();
	}

	protected int getClientCapabilities() {
		int flag = 0;
		flag |= Capabilities.CLIENT_LONG_PASSWORD;
		flag |= Capabilities.CLIENT_FOUND_ROWS;
		flag |= Capabilities.CLIENT_LONG_FLAG;
		flag |= Capabilities.CLIENT_CONNECT_WITH_DB;
		flag |= Capabilities.CLIENT_ODBC;
		flag |= Capabilities.CLIENT_IGNORE_SPACE;
		flag |= Capabilities.CLIENT_PROTOCOL_41;
		flag |= Capabilities.CLIENT_INTERACTIVE;
		flag |= Capabilities.CLIENT_IGNORE_SIGPIPE;
		flag |= Capabilities.CLIENT_TRANSACTIONS;
		flag |= Capabilities.CLIENT_SECURE_CONNECTION;
		return flag;
	}
}
