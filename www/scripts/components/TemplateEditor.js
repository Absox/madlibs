import React from 'react';
import ReactDOM from 'react-dom';
import { History, Link } from 'react-router';
var API = require('../api');

var TemplateEditor = React.createClass({

	getInitialState: function() {
		return {
			hasLoadedData: false,
			title: ""
		}
	},

	componentDidMount: function() {
		let { templateID } = this.props.params;
		var self = this;
		API.request("/madlibs/api/template/"+templateID, "GET", null, function(request, data) {
			self.refs.templateTitle.value = data.title;
			self.refs.templateContent.value = data.value;
			self.setState({
				title: data.title,
				hasLoadedData: true
			});
		});
	},

	handleTitleChange: function(e) {
		this.setState({
			title: this.refs.templateTitle.value
		});
	},

	saveTemplate: function(e) {
		e.preventDefault();

		if(!this.state.hasLoadedData) return false;

		var formdata = {
			title: this.refs.templateTitle.value,
			value: this.refs.templateContent.value
		};

		let { templateID } = this.props.params;

		var self = this;

		API.request("/madlibs/api/template/"+templateID, "PUT", formdata, function(request) {
			if (request.status != 200) self.setState({message: "Server error."});
		});
	},

	deleteTemplate: function(e) {

	},

	render : function() {
		return (
			<div className="template-editor-wrapper">
		  		<h1>{this.state.title != "" ? "Editing: "+this.state.title : "Editing template"}</h1>

		  		<form onSubmit={this.saveTemplate} className="template-editor-form">
			  		<input ref="templateTitle" type="text" placeholder="Specify a title" onKeyUp={this.handleTitleChange} />

			  		<textarea ref="templateContent" rows="30"></textarea>

			  		<input type="submit" value="Save template" />
			  		<input type="submit" className="warning-button" value="Delete template" />
		  		</form>

		  		<Link to="/templates/"><span className="icon">&larr;</span>Back to list</Link>
		  	</div>
		)
	}
});

export default TemplateEditor;