import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';

var API = require('../api');
var Auth = require('../auth');

var Account = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {
			username: Auth.getCurrentUser()
		}
	},

	componentDidMount: function() {
		if(!Auth.isLoggedIn()) {
			this.history.pushState(null, "/login/");
		}
	},

	handleSave: function(e) {
		e.preventDefault();

		API.request
	},

	render : function() {

		return (
			<div className="account-wrapper">
				<h1>Account</h1>

		  		<h3>Hello {this.state.username}, you can change your account details with the form below.</h3>

		  		<form onSubmit={this.handleSave}>

		  			<label>Password</label>
		  			<input ref="password" type="password" />

		  			<button type="submit">Save details</button>

		  		</form>
		  	</div>
		)
	}
});

export default Account;