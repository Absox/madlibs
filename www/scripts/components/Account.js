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
		  			<input ref="user" />

		  			<input ref="password" />
		  		</form>
		  	</div>
		)
	}
});

export default Account;