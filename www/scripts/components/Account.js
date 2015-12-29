import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router';

var Account = React.createClass({

	isLoggedIn: function() {

	},

	render : function() {

		return (
			<div className="wrapper">
		  		<h1>Account</h1>

		  		<form>
		  			<label>Username</label>
		  			<input ref="user" />

		  			<label>Password</label>
		  			<input ref="password" />
		  		</form>
		  	</div>
		)
	}
});

export default Account;