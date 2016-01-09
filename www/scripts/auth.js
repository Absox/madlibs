import API from './api';

let Auth = {
	

	/**
	* getAuthToken
	* Get stored authtoken
	* return object
	*/

	getAuthToken: function() {
		var authtoken = JSON.parse(localStorage.getItem('authToken'));

		return authtoken;
	},

	/**
	* isLoggedIn
	* Check locally for authtoken
	* return boolean
	*/

	isLoggedIn: function() {
		var authtoken = this.getAuthToken();

		return (authtoken != null) ? true : false;
	},

	/**
	* currentUser
	* Get current user from authtoken
	* return string
	*/

	getCurrentUser: function() {
		var authtoken = this.getAuthToken();

		return authtoken ? authtoken.username : null;
	}
}

export default Auth;