import React from 'react';
import ReactDOM from 'react-dom';
import { History } from 'react-router';
var API = require('../api');

var SignUp = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {
			message: ""
		}
	},

	signup: function(e) {
		e.preventDefault();

		this.setState({message:""});

		var post_body = {
			user: this.refs.username.value,
			password: this.refs.password.value
		};

		var self = this;

		API.request("/madlibs/api/register", "POST", post_body, function(request, data) {

			if(data.status == "failure") {
				self.setMessage(data.why);
			}

			if(data.status == "success") {
	    		self.history.pushState(null, '/account/');
		  	}
		});
	},

	setMessage: function(message) {
		this.setState({message: message});
	},

	render : function() {
		return (
			<div className="signup-wrapper">
				<div className="signup-content">
					<h2>Sign up for a Mad Libs account</h2>
					<h4>This allows you to create templates and stuff.</h4>

				  	<form className="signup-form" onSubmit={this.signup}>

				  		<label>Username</label>
				  		<input ref="username" type="text" />
				  		
	 
				  		<label>Password</label>
				  		<input ref="password" type="password" />

				  		<input type="submit" defaultValue="Sign up" /> <span className="message">{this.state.message}</span>
				  	</form>
				</div>
		  	</div>
		)
	}
});

export default SignUp;