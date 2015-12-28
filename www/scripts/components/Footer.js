import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router';

var Footer = React.createClass({

	render : function() {

		return (
			<footer className="site-footer">
			    <div className="wrapper">
			        Built by <a href="/">some people</a>.
			    </div>
			</footer>
		)
	}
});

export default Footer;

