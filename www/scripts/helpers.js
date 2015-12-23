let helpers = {
	request: function(url, method, body, callback) {
		var r = new XMLHttpRequest();
		r.open(method, url, true);
		r.onreadystatechange = function() {
			if(r.readyState == 4) {
				callback(r)
			}
		};
		r.send(body);
		return r;
	}
}

export default helpers;