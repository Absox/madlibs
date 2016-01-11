import React from 'react';
import ReactDOM from 'react-dom';
var Articles = require('Articles');

var GameUI = React.createClass({

	getInitialState: function() {
		return {};
	},

	render : function() {
		var self = this;

		return (
			<div className="game-ui">
				{
					(this.props.currentPlayer == this.props.currentTurn) ?
					<ResponsePrompt ws={this.props.ws} currentPrompt={this.props.currentPrompt} /> :
					<OtherPlayerChoosing currentPrompt={this.props.currentPrompt} currentTurn={this.props.currentTurn} />
				}

				<NextPlayerInfo currentPlayer={this.props.currentPlayer} nextTurn={this.props.nextTurn} />
	  		</div>
		)
	}
});

var ResponsePrompt = React.createClass({

	componentDidMount: function() {
		this.refs.playerResponse.focus();
	},

	submitWord: function(e) {
		e.preventDefault();

		this.props.ws.send(JSON.stringify({
			type : "responseSubmit",
            id : this.props.gameID,
            user : this.props.currentPlayer,
            value : this.refs.playerResponse.value
		}));

		this.refs.playerResponse.value = "";
	},

	render : function() {
		var prompt = this.props.currentPrompt;
		prompt = prompt.replace(/[\[\]]+/g, "");//remove [ ]
		var article = Articles.find(prompt);

		return (
			<div className="response-prompt">
				<div className="response-prompt__title">Please specify {article} <span className="response-prompt__prompt">{this.props.currentPrompt}</span></div>

		  		<form onSubmit={this.submitWord}>
		  			<input className="response-prompt__input" ref="playerResponse" type="text" />
		  		</form>
		  	</div>
		)
	}
});

var OtherPlayerChoosing = React.createClass({
	render : function() {
		return (
			<div className="player-choosing">
				<div className="player-choosing__title"><span className="player-choosing__player-name" data-player-name={this.props.currentTurn}>{this.props.currentTurn}</span> is picking a <span className="player-choosing__prompt">{this.props.currentPrompt}</span></div>
				<div className="player-choosing__ellipsis">
					<div className="player-choosing__ellipsis-dot"/>
					<div className="player-choosing__ellipsis-dot"/>
					<div className="player-choosing__ellipsis-dot"/>
				</div>
			</div>
		)
	}
});


var NextPlayerInfo = React.createClass({
	render : function() {
		return (
			<div className="next-player-info">{this.props.nextTurn == this.props.currentPlayer ? "You are" : this.props.nextTurn + " is"} up next.</div>
		)
	}
});



export default GameUI;