import React from 'react';
import ReactDOM from 'react-dom';
import { History } from 'react-router';
var API = require('../api');

var LogIn = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {message: ""};
	},

	setMessage: function(message) {
		this.setState({message: message});
	},

	login: function(e) {
		e.preventDefault();

		var formdata = {
			user: this.refs.username.value,
			password: this.refs.password.value
		};

		var self = this;

		API.request("/madlibs/api/login", "POST", formdata, function(request, data) {
			if (request.status != 200) self.setState({message: "Server error."});

		  	if(data.status == "success") {
	    		self.history.pushState(null, '/account');
		  	}

			if(data.status == "failure") {
				self.setMessage(data.why);
			}
		});
	},

	render : function() {
		return (
			<div className="login-wrapper">
				<div className="login-content">
					<h2>Log in to your Mad Libs account</h2>

				  	<form className="login-form" onSubmit={this.login}>
				  		<label>Username</label>
				  		<input ref="username" type="text" required/>
				  		
	 
				  		<label>Password</label>
				  		<input ref="password" type="password" required/>

				  		<input type="submit" defaultValue="Log In" /> <span className="message">{this.state.message}</span>
				  	</form>
			  	</div>
		  	</div>
		)
	}
});

export default LogIn;