<!doctype html>
<html>
<head>
	<link rel="stylesheet" media="screen" href="/web/style.css"/>
</head>
<body>

<div id="push" ></div>

<script>
ws = new WebSocket('ws://localhost:8000/web/socket');

ws.onopen = function() {
	document.getElementById('push').innerHTML = 'connected!';
};
ws.onmessage = function(e){
	document.getElementById('push').innerHTML = e.data + '';
};
ws.onclose = function() {
	document.getElementById('push').innerHTML = 'closed!';
};
</script>

</body>
</html>
