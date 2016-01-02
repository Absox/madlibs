let helpers = {
	request: function(url, method, body, success, failure) {
		var r = new XMLHttpRequest();
		r.open(method, url, true);
		r.onload = function() {
			if(r.readyState == 4 && r.status == 200 && success != null) {
				success(r, r.responseText);
			}

			if(r.readyState == 4 && r.status != 200 && failure != null) {
				failure(r);
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