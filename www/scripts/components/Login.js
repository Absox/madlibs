import React from 'react';
import ReactDOM from 'react-dom';
import { History } from 'react-router';
var h = require('../helpers');

var LogIn = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {message: ""};
	},

	login: function(e) {
		e.preventDefault();

		var formdata = {
			user: this.refs.username.value,
			password: this.refs.password.value
		};

		var post_body = JSON.stringify(formdata);

		var self = this;

		h.request("http://104.236.225.1:3000/madlibs/api/login", "POST", post_body, function(request) {
			if (request.status != 200) self.setState({message: "Server error."});
		  	var data = JSON.parse(request.responseText);
		  	if(data.status == "success") {
	    		self.history.pushState(null, '/account');
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