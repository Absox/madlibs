import React from 'react';
import ReactDOM from 'react-dom';
var h = require('../helpers');

var TemplateEditor = React.createClass({

	getInitialState: function() {
		return {
			hasLoadedData: false
		}
	},

	componentDidMount: function() {
		let { templateID } = this.props.params;
		var self = this;
		h.request("http://104.236.225.1:3000/madlibs/api/template/"+templateID, "GET", null, function(request) {
			if (request.status != 200) self.setState({message: "Server error."});
		  	var data = JSON.parse(request.responseText);
			self.refs.templateTitle.value = data.title;
			self.refs.templateContent.value = data.value;
			self.setState({hasLoadedData: true });
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

		var post_body = JSON.stringify(formdata);

		var self = this;

		h.request("http://104.236.225.1:3000/madlibs/api/template/"+templateID, "PUT", post_body, function(request) {
			if (request.status != 200) self.setState({message: "Server error."});
		});
	},

	render : function() {
		return (
			<div className="wrapper">
		  		<h1>Template editor</h1>

		  		<form onSubmit={this.saveTemplate}>
			  		<input ref="templateTitle" type="text" placeholder="Specify a title" />

			  		<textarea ref="templateContent" rows="30"></textarea>

			  		<input type="submit" value="Save template" />
		  		</form>
		  	</div>
		)
	}
});

export default TemplateEditor;