import React from 'react';
import ReactDOM from 'react-dom';

var GameMeta = React.createClass({
	render: function() {
		return (
			<div className="game-bar">
	  			<PlayerInvite gameURL={this.props.gameURL} />

	  			<PlayersConnected users={this.props.users} />
  			</div>
		);
	}
})



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

export default GameMeta;