import React from 'react';
import ReactDOM from 'react-dom';
var h = require('../helpers');

//components
import Header from '../components/Header';
import Footer from '../components/Footer';

var App = React.createClass({

	getInitialState: function() {
		return {};
	},

	isLoggedIn: function() {

	},

	render: function() {

		return (
			<div>
			  	<Header />

			  	<div className="site-content">
					{this.props.children}
				</div>

				<Footer />
			</div>
		)
	}
});

export default App;