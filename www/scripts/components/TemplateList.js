import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';

var API = require('../api');
var Auth = require('../auth');

var TemplateList = React.createClass({
	mixins: [History],

	getInitialState: function(){
		return {
			hasLoadedData: false,
			templates: []
		};
	},

	componentDidMount: function() {
		this.updateTemplateList();
	},

	updateTemplateList: function() {

		var self = this;

		var user = Auth.getCurrentUser();

		API.request("/madlibs/api/template/user/"+user, "GET", null, function(request, data) {

			for (var i = 0; i < data.templates.length; i++) { 	
			    data.templates[i].templatelink = "/template/"+data.templates[i].id;
			}

			self.setState({
				hasLoadedData: true,
				templates: data.templates
			});
		});
	},

	createTemplate: function() {

		var post_data = {
			"title" : "",
            "value" : ""
		};

		var self = this;

		API.request("/madlibs/api/template", "POST", post_data, function(request, data) {

			if(request.status != 200) return false;

			if(data.status=="success") {
				self.history.pushState(null, '/template/'+data.id);
			}

			self.updateTemplateList();
		});
	},

	render : function() {

		return (
			<div className="template-list-wrapper">
		  		<h1 className="template-list-title">Your templates</h1>

		  		<button onClick={this.createTemplate} className="create-template-button">+ Create Template</button>

		  		{!this.state.hasLoadedData ? <p>Loading</p> : ""}

		  		<ul className="template-list">
		  			{
						this.state.templates.map(function(template) {
							return <li key={template.id} className="template-list-item">
									<Link to={template.templatelink} className="template-list-item__link">
										<span className="template-list-item__title">{template.title}</span>
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