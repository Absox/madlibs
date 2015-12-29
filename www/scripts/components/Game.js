import React from 'react';
import ReactDOM from 'react-dom';

var Game = React.createClass({
	
	getInitialState: function(){
		let { gameID } =this.props.params;

		return {
			gameID: gameID,
			gameURL: "http://madlibs.com/game/"+gameID
		};
	},

	componentDidMount: function(){
		this.connectToServer();
	},

	submitWord: function(e) {
		e.preventDefault();

		/**
		* Send this via websocket to game session
		*/

		var submittedWord = this.refs.wordInput.value;

		this.refs.wordInput.value = "";
	},

	/**
	* Connect to websocket server
	*/
	connectToServer: function() {
		let { gameID } = this.props.params;
	},

	render : function() {
		return (
		  	<div className="game-wrapper">

		  		<div className="game-ui">
			  		<div className="give-me">Give me a <span></span></div>

			  		<form onSubmit={this.submitWord}>
			  			<input className="word-input" ref="wordInput" type="text" />
			  		</form>
		  		</div>

		  		<div className="wrapper">
		  			<div className="game-bar">
			  			<PlayerInvite gameURL={this.state.gameURL} />

			  			<PlayersConnected />
		  			</div>
		  		</div>
		  	</div>
		)
	}
});

var PlayerInvite = React.createClass({

	selectURL: function(){
		var element = this.refs.inviteURL;
		var range = document.createRange();
        range.selectNode(element);
        window.getSelection().addRange(range);
    },

	render : function() {
		return (
			<div className="game-bar__invite">
  				Invite URL: <span ref="inviteURL" className="game-bar__invite-url" onClick={this.selectURL}>{this.props.gameURL}</span>
  			</div>
		)
	}
});



var PlayersConnected = React.createClass({

	getInitialState: function() {
		return {
			connectedUsers: 0
		}
	},

	render : function() {
		return (
			<div className="game-bar__connected">
  				{this.state.connectedUsers} connected
  			</div>
		)
	}
});


export default Game;