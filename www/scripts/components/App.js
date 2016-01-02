import React from 'react';
import ReactDOM from 'react-dom';
var h = require('../helpers');

//components
import Header from '../components/Header';
import Footer from '../components/Footer';

var App = React.createClass({

	getInitialState: function() {
		return {
			currentUser: {
				username: 'anon'
			}
		};
	},

	/**
	*	Get identity from server
	*/

	componentDidMount: function() {

	},

	isLoggedIn: function() {

	},

	render: function() {

		return (
			<div>
			  	<Header currentUser={this.state.currentUser} />

			  	<div className="site-content">
					{this.props.children}
				</div>

				<Footer />
			</div>
		)
	}
});

export default App;