import React from 'react';
import ReactDOM from 'react-dom';
import { History, Link, Router } from 'react-router';
var API = require('../api');

var TemplateEditor = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {
			hasLoadedData: false,
			title: "",
			message: ""
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
	
	handleSave: function(e) {
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

	handleDelete: function(e) {
		e.preventDefault();

		let { templateID } = this.props.params;

		var please_delete = confirm("Are you sure you want to delete this template?");

		if(please_delete) {
			var self = this;

			API.request("/madlibs/api/template/"+templateID, "DELETE", null, function(request, data) {
				self.history.pushState(null, "/templates/");
			}, function(request) {
				self.setState({message: "Server error."});
			});
		}
	},

	render : function() {
		return (
			<div className="template-editor-wrapper">
		  		<h1>{this.state.title != "" ? "Editing: "+this.state.title : "Editing template"}</h1>

		  		<form className="template-editor-form">
			  		<input ref="templateTitle" type="text" placeholder="Specify a title" onKeyUp={this.handleTitleChange} />

			  		<textarea ref="templateContent" rows="30"></textarea>

			  		<input type="submit" value="Save template" onClick={this.handleSave}/>
			  		<button type="submit" className="warning-button" onClick={this.handleDelete}>Delete template</button>
			  		<span className="message">{this.state.message}</span>
		  		</form>

		  		<Link to="/templates/"><span className="icon">&larr;</span>Back to list</Link>
		  	</div>
		)
	}
});

export default TemplateEditor;