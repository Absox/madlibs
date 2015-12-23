import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';
var h = require('../helpers');

var TemplateList = React.createClass({
	mixins: [History],

	getInitialState: function(){
		return {
			templates: []
		};
	},

	getTemplates: function() {
		h.request("http://104.236.225.1:3000/madlibs/templates/user/absox", "POST", post_body, function(request) {
			var data = JSON.parse(request.responseText);

			this.setState({templates: data});
		});
	},

	render : function() {
		return (
			<div className="wrapper">
		  		<h1>Your templates</h1>

		  		<ul>
		  			{
						this.state.templates.map(function(template) {
							return <li key={template.id}>{template.title}</li>;
						})
					} 
		  		</ul>
		  	</div>
		)
	}
});

export default TemplateList;