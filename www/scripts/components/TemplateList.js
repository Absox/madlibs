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
		h.request("http://104.236.225.1:3000/madlibs/templates/user/"+sam, "GET", null, function(request) {
			var data = JSON.parse(request.responseText);

			this.setState({templates: data});
		});
	},

	createTemplate: function() {

		var post_data = {
			"title" : "",
            "value" : ""
		};

		post_data = JSON.stringify(post_data);

		h.request("http://104.236.225.1:3000/madlibs/api/template", "POST", post_data, function(request) {

			var data = JSON.parse(request.responseText);

			if(request.statusCode != 200) return false;

			if(request.data.status=="success") {
				this.context.history.pushState(null, '/template/'+data.id);
			} else {
			}
		});
		
	},

	render : function() {
		console.log(this.props);

		return (
			<div className="wrapper">
		  		<h1>Your templates</h1>

		  		<button onClick={this.createTemplate}>+ Create Template</button>

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