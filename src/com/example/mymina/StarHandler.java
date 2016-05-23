package com.example.mymina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class StarHandler {
	public static void start() {
		new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				// 创建一个负责向后台发送数据请求对象
				NioSocketConnector connector = new NioSocketConnector();
				// 创建接受数据过滤器
				DefaultIoFilterChainBuilder chain = connector.getFilterChain();
				// 设置这个过滤器将一行一行读取数据
				chain.addLast("codec", new ProtocolCodecFilter(
						new TextLineCodecFactory(Charset.forName("UTF-8"),
								LineDelimiter.WINDOWS.getValue(),
								LineDelimiter.WINDOWS.getValue())));

				// 设置客户端消息处理类
				connector.setHandler(new MinaClientHandler());
				// 设置连接主机超时
				connector.setConnectTimeout(30);
				// 连接服务器
				ConnectFuture cf = connector.connect(new InetSocketAddress(
						"192.168.191.1", 6666));
				// 是阻塞方法，只有当write完线程才能继续执行
				cf.awaitUninterruptibly();
				// 等待连接断开
				cf.getSession().getCloseFuture().awaitUninterruptibly();
				connector.dispose();
			}
		}).start();

	}
}
