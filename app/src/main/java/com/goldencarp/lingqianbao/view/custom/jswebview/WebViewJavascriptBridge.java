package com.goldencarp.lingqianbao.view.custom.jswebview;



public interface WebViewJavascriptBridge {
	
	public void send(String data);
	public void send(String data, CallBackFunction responseCallback);
	
	

}
