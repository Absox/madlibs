import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';

var GameIntro = React.createClass({
	mixins: [History],

	componentDidMount: function(){
		//Auto focus "Start a new game"
		ReactDOM.findDOMNode(this.refs.startButton).focus(); 
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
					<Link to="/select-template/" ref="startButton" className="home-options__option">Start a new game</Link>
					
					<em>- or -</em>

					<button onClick={this.joinRandomGame} className="home-options__option">Join a random game</button>

					<div ref="joinRandomButton" className="stats">8,287 games in progress</div>
				</div>
			</div>
		)
	}
});

export default GameIntro;