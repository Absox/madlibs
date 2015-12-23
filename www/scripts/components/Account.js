import React from 'react';
import ReactDOM from 'react-dom';

var Account = React.createClass({

	isLoggedIn: function() {

	},

	render : function() {
		return (
			<div className="wrapper">
		  		<h1>Account</h1>
		  		
		  		<a href="/signup">Sign up</a>
		  		<a href="/login">Log in</a>
		  		<form>
		  			
		  		</form>
		  	</div>
		)
	}
});

export default Account;