import React from 'react';
import ReactDOM from 'react-dom';
import { History, Link, Router } from 'react-router';
import MediumEditor from 'react-medium-editor';

var API = require('../api');


var TemplateEditor = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {
			hasLoadedData: false,
			title: "",
			content: "",
			message: "",
			editorOptions: {
				toolbar: {
					buttons: [
						'bold',
						'italic',
						'underline',
						{
			                name: 'h1',
			                action: 'append-h2',
			                aria: 'header type 1',
			                tagNames: ['h2'],
			                contentDefault: '<b>H</b>',
			                classList: ['custom-class-h1'],
			                attrs: {
			                    'data-custom-attr': 'attr-value-h1'
			                }
			            }
					]
				},
				placeholder: false
			}
		}
	},

	componentDidMount: function() {
		let { templateID } = this.props.params;
		var self = this;

		console.log(this.refs.content.props.options);

		API.request("/madlibs/api/template/"+templateID, "GET", null, function(request, data) {
			self.setState({
				title: data.title,
				content: data.value,
				hasLoadedData: true
			});
		});

	},

	handleTitleChange: function(e) {
		this.setState({
			title: this.refs.title.value
		});
	},

	handleChange(text, medium) {

		//text = text.replace(/\[(.*)\]/g,"<span>$1</span>");

    	this.setState({
    		content: text
    	});

    	return text;
  	},
	
	handleSave: function(e) {
		e.preventDefault();

		if(!this.state.hasLoadedData) return false;

		var data = {
			title: this.state.title,
			value: this.state.content
		};

		let { templateID } = this.props.params;

		var self = this;

		API.request("/madlibs/api/template/"+templateID, "PUT", data, function(request) {
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
		  			<div className="template-editor">
				  		<input className="template-editor__title" ref="title" type="text" placeholder="Specify a title" onChange={this.handleTitleChange} value={this.state.title}/>

				  		<div className="template-editor__separator"></div>

			  			<MediumEditor
							tag="div"
							ref="content" className="template-editor__content"
							text={this.state.content}
							onChange={this.handleChange}
							options= {this.state.editorOptions}
						/>
			  		</div>


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