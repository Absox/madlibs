import React from 'react';
import ReactDOM from 'react-dom';
import { History } from 'react-router';

var GameIntro = React.createClass({
	mixins: [History],

	componentDidMount: function(){
		//Auto focus "Start a new game"
		ReactDOM.findDOMNode(this.refs.startButton).focus(); 
	},

	/**
	* Requests a new game session be created and handles the output.
	* @param {Event} e
	* @return 
	*/
	startGame: function(e) {
		e.preventDefault();
	    
	    this.history.pushState(null, '/game/'+'asdasd');
	},

	/**
	* Requests the ID of an existing game session and handles the output.
	* @param {Event} e
	* @return 
	*/
	joinRandomGame: function(e) {
	    this.history.pushState(null, '/game/'+'asdasd');
	},

	render : function() {
		return (
		  	<div className="home-screen">
				<div className="home-options">
					<button ref="startButton" onClick={this.startGame}>Start a new game</button>

					<em>- or -</em>

					<button onClick={this.joinRandomGame}>Join a random game</button>

					<div ref="joinRandomButton" className="stats">8,287 games in progress</div>
				</div>
			</div>
		)
	}
});

export default GameIntro;