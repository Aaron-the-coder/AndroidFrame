;(function() {
	console.log("entered");
	if (window.WebViewJavascriptBridge) { return }
	console.log("WebViewJavascriptBridge not ready and initalizing");
	var messagingIframe;

	function _createQueueReadyIframe(doc) {
		messagingIframe = doc.createElement('iframe');
		messagingIframe.style.display = 'none';
		doc.documentElement.appendChild(messagingIframe);
	}
	function init() {
	}	
	
	function callHandler(name, data, callback){
		callbackStr = callback? callback.toString():"default";
		AndroidWebViewJavascriptBridge.callJavaHandler(name, JSON.stringify(data), callbackStr);
	}
	function getBridgeReady(){
		console.log("WebViewJavascriptBridge initializing");
		window.WebViewJavascriptBridge = {
			init: init,
			callHandler: callHandler,
		};
		var doc = document;
		_createQueueReadyIframe(doc);
		var readyEvent = doc.createEvent('Events');
		readyEvent.initEvent('WebViewJavascriptBridgeReady');
		readyEvent.bridge = WebViewJavascriptBridge;
		doc.dispatchEvent(readyEvent);
	}
	if (window.AndroidWebViewJavascriptBridge) {
		getBridgeReady();
	} else {
		document.addEventListener('AndroidWebViewJavascriptBridgeReady', function() {
			console.log("AndroidWebViewJavascriptBridgeReady notified");
			getBridgeReady();
		}, false);
	}
	console.log("jsbridge_init done");
	
})();
