import React from 'react';
import { History } from 'react-router';
import ReactDOM from 'react-dom';
var h = require('../helpers');

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

		var selectedTemplate = this.refs.selectedTemplate.value;

		var self = this;

		h.request("http://madlibs.samjarv.is/madlibs/api/session", "GET", null, function(request) {
			self.history.pushState(null, '/game/asdasd/');
		});
	},

	populateList: function() {
		var self = this;

		h.request("http://madlibs.samjarv.is/madlibs/api/template/user/sam", "GET", null, function(request) {

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
				privateTemplates: templates
			});
			
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
										return <option key={template.id}>{template.title}</option>
									})
								} 
							</optgroup>

							<optgroup label="Public templates">
					  			{
									this.state.privateTemplates.map(function(template) {
										return <option key={template.id}>{template.title}</option>
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