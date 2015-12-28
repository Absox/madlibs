/**
* API 
* All requests made to the API have a JSON body
*
*/

import h from './helpers';

let API = {
	details: {
		host: 'madlibs.samjarv.is',
		ssl: false
	},

	/**
	* request
	* Make request to API, 
	* body must be valid JSON or null
	* return XMLHTTPRequest r
	*/

	request: function(uri, method, body, callback) {
		var url = this.ssl ? "https://" : "http://" + this.details.host + uri;

		if(body != null) {
			var authToken = localStorage.getItem('authToken');

			if(authToken) {
				body.authToken = authToken;
			}

			body = JSON.stringify(body);
		}

		var r = h.request(url, method, body, function(r, data) {

			try {
			   var json = JSON.parse(data);
			}
			catch(e)
			{
			   alert('Invalid json received');
			}

			if(typeof json.authToken != 'undefined') {
				localStorage.setItem('authToken', json.authToken);
			}

			callback(r, json);
		});

		return r;
	}
}

export default API;