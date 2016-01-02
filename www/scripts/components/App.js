import React from 'react';
import ReactDOM from 'react-dom';

//components
import Header from './Header';
import Footer from './Footer';

var App = React.createClass({

	getInitialState: function() {
		return {
			currentUser: {
				username: 'anon'
			}
		};
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