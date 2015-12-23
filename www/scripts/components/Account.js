import React from 'react';
import ReactDOM from 'react-dom';

var Account = React.createClass({

	isLoggedIn: function() {

	},

	render : function() {
		var signup = <a href="/signup" className="branded-button">Sign up</a>;
		var login = <a href="/login" className="branded-button">Log in</a>

		return (
			<div className="wrapper">
		  		<h1>Account</h1>
		  		
		  		{login}
		  		{signup}

		  		<form>
		  			
		  		</form>
		  	</div>
		)
	}
});

export default Account;