import React from 'react';
import ReactDOM from 'react-dom';

var API = require('../api');
var Auth = require('../auth');

var Game = React.createClass({
	
	getInitialState: function(){

		var ws = this.connectToServer();

		let { gameID } = this.props.params;

		var gameURL = location.protocol + "//" + location.hostname + "/game/" + gameID;

		return {
			gameID: gameID,
			gameURL: gameURL,
			ws: ws,
			currentUser: Auth.getCurrentUser(),
			users: []
		};
	},

	componentDidMount: function(){
	},

	componentWillUnmount: function() {
		this.state.ws.close();
	},
	/**
	* Connect to websocket server
	*/
	connectToServer: function() {
		let { gameID } = this.props.params;

		var ws = new WebSocket("ws://" + "madlibs.samjarv.is" + (location.port ? ':' + location.port : '') + "/madlibs/api/websocket");

		var self = this;


		ws.onopen = function() {
			var data = {
				'type': 'gameJoin',
				'id': self.state.gameID,
				'user': self.state.currentUser
			};

			data = JSON.stringify(data);

			this.send(data);
		}

		ws.onmessage = function(e) {
			var data = JSON.parse(e.data);

			console.log(data);

			if(!data.type) return false;

			switch(data.type) {
				case "userJoinedUpdate":
					var users = self.state.users;
					users = users.concat(data.user);

					self.setState({
						users: users
					});

					break;

				case "userLeftUpdate":
					var users = self.state.users;
					var user_to_remove = data.user;

					for(var i = users.length - 1; i >= 0; i--) {
					    if(self.state.users[i] === user_to_remove) {
							users.splice(i, 1);
					    }
					}

					self.setState({
						users: users
					});

					break;

				case "gameJoinResponse":

					if(data.status == "failure") {

					}

					if(data.status == "success") {
					}

					break;

			    case "gameStateUpdate":
			        self.setState({
			        	currentPrompt: data.currentPrompt,
						currentTurn: data.currentTurn,
						users: data.users
			        });

			        break;

			    case "chatReceive":

			        break;

			   	case "responseSubmitFailure":

			   		break;

			    case "gameEnd": 

			    	break;

			    default:
			        console.error("Unhandled message type received:"+data.type);
			}
		}

		return ws;
	},

	render : function() {
		return (
		  	<div className="game-wrapper">
		  		<GameUI ws={this.state.ws} gameID={this.state.gameID} currentUser={this.state.currentUser} />

		  		<div className="wrapper">
		  			<div className="game-bar">
			  			<PlayerInvite gameURL={this.state.gameURL} />

			  			<PlayersConnected users={this.state.users} />
		  			</div>
		  		</div>

		  		<Chat ws={this.state.ws} />
		  	</div>
		)
	}
});


var GameUI = React.createClass({


	submitWord: function(e) {
		e.preventDefault();

		var submittedWord = this.refs.wordInput.value;

		this.props.ws.send(JSON.stringify({
			type : "responseSubmit",
            id : this.props.gameID,
            user : this.props.currentUser,
            value : submittedWord
		}));

		this.refs.wordInput.value = "";
	},


	render : function() {
		return (
			<div className="game-ui">
		  		<div className="give-me">Give me a <span></span></div>

		  		<form onSubmit={this.submitWord}>
		  			<input className="word-input" ref="wordInput" type="text" />
		  		</form>
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

	getDefaultProps: function() {
		return {
			users: []
		}
	},

	render : function() {

		return (
			<div className="game-bar__connected">
  				{this.props.users.length} connected
  			</div>
		)
	}
});

var Chat = React.createClass({
	
	componentDidMount: function() {
	},

	sendMessage: function(msg) {

		if(this.props.ws.readyState == 1) {
			this.props.ws.send(
				JSON.stringify({
					type: 'chatSend',
					message: msg
				})
			);
		}
	},

	handleClick: function() {
		this.sendMessage("test");
	},

	render : function() {
		return (
			<div className="chat-wrapper" onClick={this.handleClick}></div>
		)
	}
});

export default Game;