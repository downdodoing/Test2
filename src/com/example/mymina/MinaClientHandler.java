package com.example.mymina;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

import android.util.Log;

public class MinaClientHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String s = (String) message;
		Log.i("���", "���Է���˵���Ϣ" + s);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

		Map<String, Object> aaa = new HashMap<String, Object>();
		aaa.put("userName", "qqq");
		aaa.put("Password", "www");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("methodName", "register");
		jo2.put("params", aaa);

		Log.i("���", "����������");
		session.write(jo2.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		Log.i("���", "����������");
	}

}
