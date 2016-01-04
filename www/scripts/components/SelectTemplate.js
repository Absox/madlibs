import React from 'react';
import { History } from 'react-router';
import ReactDOM from 'react-dom';

var API = require('../api');
var Auth = require('../auth');

var SelectTemplate = React.createClass({
	mixins: [History],

	getInitialState: function(){
		return {
			publicTemplates: [],
			privateTemplates: []
		};
	},

	componentDidMount: function() {
		this.populateList();
	},

	startGame: function(e) {
		e.preventDefault();

		var post_body = {
			templateId: this.refs.selectedTemplate.value
		};

		var self = this;

		API.request("/madlibs/api/session", "POST", post_body, function(r, data) {
			self.history.pushState(null, '/game/'+data.sessionId);
		});
	},

	populateList: function() {
		var self = this;

		var user = Auth.getCurrentUser();

		API.request("/madlibs/api/template/user/"+user, "GET", null, function(request, data) {

			self.setState({
				privateTemplates: data.templates
			});
		});

		var templates = [
			{
				id: 2,
				title: "test"
			},
			{
				id: 3,
				title: "asdasdasd"
			}
		];

		self.setState({
			publicTemplates: templates,
		});
	},

	render : function() {
		var signup = <a href="/signup" className="branded-button">Sign up</a>;
		var login = <a href="/login" className="branded-button">Log in</a>;

		return (
			<div className="template-select-wrapper">
				<div className="template-select">
			  		<h1>Select a template</h1>
			  		
			  		<form onSubmit={this.startGame}>
				  		<select ref="selectedTemplate" className="template-select__select">
				  			<optgroup label="Public templates">
					  			{
									this.state.publicTemplates.map(function(template) {
										return <option key={template.id} value={template.id}>{template.title}</option>
									})
								} 
							</optgroup>

							<optgroup label="Your templates">
					  			{
									this.state.privateTemplates.map(function(template) {
										return <option key={template.id} value={template.id}>{template.title}</option>
									})
								} 
							</optgroup>
				  		</select>

				  		<label className="template-select__private">
				  			<input type="checkbox" ref="private" />
				  			Invite only?
				  		</label>

				  		<button type="submit" className="template-select__start">Start!</button>
			  		</form>
		  		</div>
		  	</div>
		)
	}
});

export default SelectTemplate;