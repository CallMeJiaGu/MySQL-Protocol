package com.jiagu.mysql.protocol;

import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import com.jiagu.mysql.protocol.util.CharsetUtil;
import com.jiagu.mysql.protocol.util.HexUtil;
import org.junit.Test;

import com.jiagu.mysql.protocol.util.RandomUtil;

/**
 * 
 * <pre><b>test handshake packet.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 */
public class HandshakePacketTest {

	@Test
	public void produce() {
		byte[] rand1 = RandomUtil.randomBytes(8);
		byte[] rand2 = RandomUtil.randomBytes(12);
		byte[] seed = new byte[rand1.length + rand2.length];
		System.arraycopy(rand1, 0, seed, 0, rand1.length);
		System.arraycopy(rand2, 0, seed, rand1.length, rand2.length);//上面两步主要是生成一个随机种子

		HandshakePacket hs = new HandshakePacket();
		hs.packetId = 0; //包的序列号
		hs.protocolVersion = Versions.PROTOCOL_VERSION; //协议号
		hs.serverVersion = Versions.SERVER_VERSION; //版本号
		hs.threadId = 1000; //当前线程编号
		hs.seed = rand1;//用于后续账户密码验证
		hs.serverCapabilities = getServerCapabilities();//大概是 1111 1111.每一位1表示一个选择，最后做了与运算合在一起，类似于linux中的xwr、775.
		hs.serverCharsetIndex = (byte) (CharsetUtil.getIndex("utf8") & 0xff);//编码格式
		hs.serverStatus = 2;//用于表示服务器状态，比如是否是事务模式或者自动提交模式
		hs.restOfScrambleBuff = rand2;//用于后续账户密码验证

		ByteBuffer buffer = ByteBuffer.allocate(256);
		hs.write(buffer);
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes, 0, bytes.length);
		String result = HexUtil.Bytes2HexString(bytes);
		System.out.println(result);
		System.out.println(Integer.valueOf(result.substring(0, 2), 16));
		System.out.println(result.length() / 2 - 4);
		assertTrue(Integer.valueOf(result.substring(0, 2), 16) == result
				.length() / 2 - 4);
		HandshakePacket hs2 = new HandshakePacket();
		hs2.read(bytes);
		assertTrue(hs2.threadId == 1000);
		System.out.println(hs2.threadId);
		assertTrue(hs2.serverVersion.length == Versions.SERVER_VERSION.length);

	}

	protected int getServerCapabilities() {
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
