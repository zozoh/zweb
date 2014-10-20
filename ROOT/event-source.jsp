<!DOCTYPE html>


<html>
<script src="/js/jquery.js"></script>
<body donload="registerSSE()">
	<script>
		function do_start() {
			window.myES = new EventSource('/event');
			myES.onmessage = function(e) {
				var div = $('<div>').html("<b style=color:green>" + e.type
						+ ":"
						+ e.lastEventId
						+ "</b>::"
						+ e.data);
				$("#result").append(div);
			};

			myES.addEventListener('-zozoh-', function(e) {
				var div = $('<div>').html("<b style=color:red>" + e.type
						+ ":"
						+ e.lastEventId
						+ "</b>::"
						+ e.data);
				$("#result").append(div);
			}, false);
			myES.onopen = function(e){
				var jq = $('<h1>ES Opened : ' + e.target.readyState + '</h1>');
				$("#result").append(jq);
			};
		}
		function do_stop() {
			if (window.myES) {
				myES.close();
			}
		}
		function do_clear() {
			document.getElementById("result").innerHTML = "";
		}
	</script>
	<div>
		<button onclick="do_start()">On-Event</button>
		<button onclick="do_stop()">Stop-Event</button>
		<button onclick="do_clear()">Clear</button>
	</div>
	<pre id="result" style="padding:20px;"></pre>

</body>
</html>