import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router';

var Account = React.createClass({

	isLoggedIn: function() {

	},

	render : function() {
		var signup = <Link to="/signup" className="branded-button">Sign up</Link>;
		var login = <Link to="/login" className="branded-button">Log in</Link>

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