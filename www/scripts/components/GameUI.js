import React from 'react';
import ReactDOM from 'react-dom';

var GameUI = React.createClass({
	
	componentDidMount: function(){
		this.connectToServer();
	},

	/**
	* Connect to websocket server
	*/
	connectToServer: function() {
		let { gameId } = this.props.params;
	},

	render : function() {
		return (
		  	<div className="game-wrapper" ref="game-wrapper">
		  		<div className="give-me">Give me a <span></span></div>

		  		<input className="word-input" placeholder="Enter word"/>

		  		<div className="game-bar">
		  			<div className="game-bar__invite">
		  				Invite URL: {this.GameURL}
		  			</div>
		  		</div>
		  	</div>
		)
	}
});

export default GameUI;