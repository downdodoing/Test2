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
				// ����һ���������̨���������������
				NioSocketConnector connector = new NioSocketConnector();
				// �����������ݹ�����
				DefaultIoFilterChainBuilder chain = connector.getFilterChain();
				// ���������������һ��һ�ж�ȡ����
				chain.addLast("codec", new ProtocolCodecFilter(
						new TextLineCodecFactory(Charset.forName("UTF-8"),
								LineDelimiter.WINDOWS.getValue(),
								LineDelimiter.WINDOWS.getValue())));

				// ���ÿͻ�����Ϣ������
				connector.setHandler(new MinaClientHandler());
				// ��������������ʱ
				connector.setConnectTimeout(30);
				// ���ӷ�����
				ConnectFuture cf = connector.connect(new InetSocketAddress(
						"192.168.191.1", 6666));
				// ������������ֻ�е�write���̲߳��ܼ���ִ��
				cf.awaitUninterruptibly();
				// �ȴ����ӶϿ�
				cf.getSession().getCloseFuture().awaitUninterruptibly();
				connector.dispose();
			}
		}).start();

	}
}
