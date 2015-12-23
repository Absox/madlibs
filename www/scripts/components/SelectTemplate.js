import React from 'react';
import ReactDOM from 'react-dom';

var SelectTemplate = React.createClass({

	getInitialState: function(){
		return {templates: []};
	},

	isLoggedIn: function() {

	},

	getTemplates: function() {
		var self = this;

		h.request("/madlibs/api/template/user/", "GET", null, function(request) {

			templates = ['']
			self.setState({templates: templates});
		});

	},

	render : function() {
		var signup = <a href="/signup" className="branded-button">Sign up</a>;
		var login = <a href="/login" className="branded-button">Log in</a>;

		return (
			<div className="template-select-wrapper">
				<div>
			  		<h1>Select a template</h1>
			  		
			  		<select>

			  		</select>
		  		</div>
		  	</div>
		)
	}
});

export default SelectTemplate;