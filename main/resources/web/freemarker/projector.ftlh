<!doctype html>
<html>
<head>
	<link rel="stylesheet" media="screen" href="/web/style.css"/>
	<link rel="icon" type="image/x-icon" href="/web/favicon.ico">
</head>
<body>

<div id="error-hook"></div>

<script>
function createSocket() {
	if(typeof ws !== 'undefined' && ws.readyState === WebSocket.OPEN) {
		// console.log("connected: " + new Date());
		return;
	}
	// console.log("connecting " + new Date());
	ws = new WebSocket('ws://localhost:8000/web/socket');

	ws.onopen = function() {
		document.title = 'connected!';
		// console.log('connected');
	};
	ws.onmessage = function(e){
		if(e.data == 'reload') {
			location.reload();
		} else {
			document.title = e.data + '';
		}
	};
	ws.onclose = function() {
		document.title = 'disconnected!';
	};
}
createSocket();

setInterval(createSocket, 5000);

window.onerror = function(msg, url, line) {
	html = 'line:' + line + '<br/>' + msg;
	document.getElementById('error-hook').innerHTML = html;
}

</script>

<script src="/web/jquery.js"></script>
${body?no_esc}

</body>
</html>
