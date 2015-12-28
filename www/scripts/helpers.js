let helpers = {
	request: function(url, method, body, callback) {
		var r = new XMLHttpRequest();
		r.open(method, url, true);
		r.onreadystatechange = function() {
			//response received state
			if(r.readyState == 4) {
				var data = r.responseText;
				callback(r, data);
			}
		};
		r.send(body);
		return r;
	},
	
	getCookie: function(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		
		for(var i=0; i<ca.length; i++) {
		    var c = ca[i];
		    while (c.charAt(0)==' ') c = c.substring(1);
		    if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
		}

		return "";
	},

	setCookie(cname, cvalue, expiry) {
	    var d = new Date();
	    d.setTime(expiry);
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	}
}

export default helpers;