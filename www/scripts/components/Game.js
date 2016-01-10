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
			currentPlayer: Auth.getCurrentUser(),
			nextPlayer: null,
			currentPrompt: "",
			users: [],
			hasEnded: false,
			hasJoined: false,
			hasReceivedState: false,
			connectionMessage: "Connecting!"
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
				'id': self.state.gameID
			};

			if(self.state.currentPlayer != null) {
				data.user = self.state.currentPlayer;
			}

			data = JSON.stringify(data);

			this.send(data);
		}

		ws.onmessage = function(e) {
			var data = JSON.parse(e.data);

			console.log(data);

			if(!data.type) return false;

			switch(data.type) {
				case "userJoinedUpdate":
					console.log(data.user + " joined!");

					break;

				case "userLeftUpdate":
					console.log(data.user + " left!");

					break;

				case "gameJoinResponse":
					if(data.status == "failure") {
						self.setState({
			        		connectionMessage: data.why
				        });
					}

					if(data.status == "success") {
						self.setState({
			        		currentPlayer: data.user,
			        		hasJoined: true
				        });
					}

					break;

			    case "gameStateUpdate":
			        self.setState({
			        	currentPrompt: data.currentPrompt,
						users: data.users,
						currentTurn: data.currentTurn,
						nextTurn: data.nextTurn,
						hasReceivedState: true
			        });

			        break;

			    case "chatReceive":

			        break;

			   	case "responseSubmitFailure":

			   		break;

			    case "gameEnd": 

			    	break;

			   	case "sessionComplete":
			   		self.setState({
			   			hasEnded: true
			   		});

			   		break;

			    default:
			        console.error("Unhandled message type received:"+data.type);
			}
		}

		return ws;
	},

	render : function() {

		var mainui;

		if(this.state.hasJoined && this.state.hasReceivedState) {
			if(this.state.hasEnded) {
				mainui = <GameEnd />;
			} else {
				mainui = <GameUI ws={this.state.ws} gameID={this.state.gameID} currentPrompt={this.state.currentPrompt} currentPlayer={this.state.currentPlayer} currentTurn={this.state.currentTurn} nextTurn={this.state.nextTurn} />;
			}
		} else {
			mainui = <GameNotConnected message={this.state.connectionMessage}/>;
		}

		return (
		  	<div className="game-wrapper">
		  		
		  		{mainui}

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

		return (
			<div className="response-prompt">
				<div className="response-prompt__title">Please specify a <span className="response-prompt__request">{this.props.currentPrompt}</span></div>

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
				<div className="player-choosing__title"><span className="response-prompt__player-name" data-player-name={this.props.currentTurn}>{this.props.currentTurn}</span> is picking a <span className="response-prompt__request">{this.props.currentPrompt}</span></div>
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
		var user_list = this.props.users.join(" | ");

		return (
			<div className="game-bar__connected" title={user_list}>
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











var GameEnd = React.createClass({
	render : function() {
		return (
			<div className="game-end-wrapper">Game end!</div>
		)
	}
});


var GameNotConnected = React.createClass({
	render : function() {
		return (
			<div className="not-connected-wrapper">{this.props.message}</div>
		)
	}
});


export default Game;