import React from 'react';
import ReactDOM from 'react-dom';

import GameUI from './GameUI';
import GameMeta from './GameMeta';
import GameChat from './GameChat';

var API = require('../../api');
var Auth = require('../../auth');

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
			chatLog: [],
			hasEnded: false,
			hasJoined: false,
			hasConnection: false,
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

		var time;

		ws.onopen = function() {
			var data = {
				'type': 'gameJoin',
				'id': self.state.gameID
			};

			if(self.state.currentPlayer != null) {
				data.user = self.state.currentPlayer;
			}

			data = JSON.stringify(data);

			time = new Date();

			this.send(data);

			self.setState({
				hasConnection: true
			});

			//ping every 4 minutes to keep connection up
			setInterval(function() {
				ws.send({
					'type': 'keepalive'
				});
			}, 20000000);
		}

		ws.onmessage = function(e) {
			var data = JSON.parse(e.data);

			console.log(data);

			if(!data.type) return false;

			switch(data.type) {
				case "userJoinedUpdate":

					break;

				case "userLeftUpdate":

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

			    	var chatLog = self.state.chatLog;

			    	chatLog.push({
			    		"user": data.user,
			    		"message": data.message,
			    		"time": data.time
			    	});

			    	self.setState({
			    		chatLog: chatLog
			    	});

			        break;

			   	case "responseSubmitFailure":

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

		ws.onclose = function() {
			self.setState({
	   			hasConnection: false,
	   			connectionMessage: "Connection to the server has been lost."
	   		});
		}

		return ws;
	},

	render : function() {

		var mainui;

		if(this.state.hasConnection && this.state.hasJoined && this.state.hasReceivedState) {
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

		  		{this.state.hasConnection && this.state.hasJoined ? <GameChat ws={this.state.ws} chatLog={this.state.chatLog} /> : null}

		  		<div className="wrapper">
		  			<GameMeta gameURL={this.state.gameURL} users={this.state.users} />
		  		</div>

		  	</div>
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