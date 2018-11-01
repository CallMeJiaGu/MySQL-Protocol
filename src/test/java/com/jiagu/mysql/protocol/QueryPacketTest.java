package com.jiagu.mysql.protocol;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.jiagu.mysql.protocol.util.HexUtil;

/**
 * 
 * <pre><b>test query packet.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 */
public class QueryPacketTest {

	public void produce() {
		QueryPacket query = new QueryPacket();
		query.flag = 3;//查询的标记，3为query
		query.message = "SELECT count(*) FROM `paper`;".getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(256);
		query.write(buffer);//将包的大小、packedId(包的序列号)，flag,message，写到Buffer中
		buffer.flip();//写模式转为读模式
		byte[] bytes = new byte[buffer.remaining()];//buffer.remaining表示buffer中可读的为多少
		buffer.get(bytes, 0, bytes.length);//最后将请求的参数的二进制放在了bytes里

		String result = HexUtil.Bytes2HexString(bytes);//将二进制转为16进制
		System.out.println(result);

		assertTrue(Integer.valueOf(result.substring(0, 2), 16) == result
				.length() / 2 - 4);

		QueryPacket query2 = new QueryPacket();
		query2.read(bytes);
		assertTrue(new String(query2.message).equals("SELECT count(*) FROM `paper`;"));
	}

	public byte[] produceByte() {
		QueryPacket query = new QueryPacket();
		query.flag = 3;//查询的标记，3为query
		query.message = "SELECT count(*) FROM `paper`;".getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(256);
		query.write(buffer);//将包的大小、packedId(包的序列号)，flag,message，写到Buffer中
		buffer.flip();//写模式转为读模式
		byte[] bytes = new byte[buffer.remaining()];//buffer.remaining表示buffer中可读的为多少
		buffer.get(bytes, 0, bytes.length);//最后将请求的参数的二进制放在了bytes里

		String result = HexUtil.Bytes2HexString(bytes);//将二进制转为16进制
		System.out.println(result);
		return result.getBytes();

	}
	@Test
	public void sendMessage() throws Exception{
		InputStream inputStreams = null;
		OutputStream outputStream = null;

		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("192.168.43.97", 3306));
		System.out.println("发送数据");
		//写入数据
		outputStream = socket.getOutputStream();
		outputStream.write(produceByte());
		socket.shutdownOutput();

		System.out.println("接受回应");
		inputStreams = socket.getInputStream();
		byte[] bytes = new byte[5000];
		StringBuilder stringBuilder = new StringBuilder();
		int len = 0;
		while((len = inputStreams.read(bytes))!=-1){
			for(int i=0 ;i<len;i++){
				System.out.print(bytes[i]);
			}
			System.out.println();
			stringBuilder.append(new String(bytes,0,len));
		}
		System.out.println("客户端收到响应："+stringBuilder);
	}
}
