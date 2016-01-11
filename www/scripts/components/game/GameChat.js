import React from 'react';
import ReactDOM from 'react-dom';

var GameChat = React.createClass({
	
	getDefaultProps: function() {
		return {
			chatlog: []
		}
	},

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

	handleSubmit: function(e) {
		e.preventDefault();

		this.props.ws.send(JSON.stringify({
			type : "chatSend",
            message: this.refs.chatInput.value
		}));

		this.refs.chatInput.value = "";
	},

	render : function() {
		return (
			<div className="game-chat">
				<ul className="game-chat__log">
					{
						this.props.chatLog.map(function(item) {
							return <li className="game-chat__log-item">
								<span className="author">{item.user}</span> {item.message}
							</li>;
						})
					} 
				</ul>
				<form onSubmit={this.handleSubmit}>
					<input type="text" ref="chatInput" className="game-chat__input" />
				</form>
			</div>
		)
	}
});

export default GameChat;