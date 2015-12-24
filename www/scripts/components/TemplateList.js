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

	componentDidMount: function() {
		this.updateTemplateList();
	},

	updateTemplateList: function() {

		var self = this;
		h.request("http://104.236.225.1:3000/madlibs/api/template/user/sam", "GET", null, function(request) {
			var data = JSON.parse(request.responseText);

			for (var i = 0; i < data.templates.length; i++) { 
			    data.templates[i].templatelink = "/template/"+data.templates[i].id;
			}

			self.setState({templates: data.templates});
		});
	},

	createTemplate: function() {

		var post_data = {
			"title" : "",
            "value" : ""
		};

		post_data = JSON.stringify(post_data);

		var self = this;

		h.request("http://104.236.225.1:3000/madlibs/api/template", "POST", post_data, function(request) {

			var data = JSON.parse(request.responseText);

			if(request.status != 200) return false;

			if(data.status=="success") {
				self.history.pushState(null, '/template/'+data.id);
			}

			self.updateTemplateList();
		});
	},

	render : function() {

		return (
			<div className="wrapper">
		  		<h1>Your templates</h1>

		  		<button onClick={this.createTemplate}>+ Create Template</button>

		  		<ul>
		  			{
						this.state.templates.map(function(template) {
							return <li key={template.id}>
									<Link to={template.templatelink}>
										{template.title ? template.title : "No title specified"}
									</Link>
								</li>;
						})
					} 
		  		</ul>
		  	</div>
		)
	}
});

export default TemplateList;