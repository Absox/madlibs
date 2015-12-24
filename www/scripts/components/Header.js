import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';


var Header = React.createClass({
	mixins: [History],

	render : function() {
		var templates = <li><Link to="/templates/">Templates</Link></li>;

		return (
			<header className="site-header" id="site-header">
				<div className="wrapper">
			        <Link to="/" className="site-logo-link">
			            <span className="site-logo">M</span>
			            <span className="site-title">Mad Libs</span>
			        </Link>

			        <nav className="site-nav">
			            <ul>
			                {templates}
			                <li><Link to="/account/"><img src="/css/images/member.svg"/></Link></li>
			                <li><Link to="/login/">Login</Link></li>
			                <li><Link to="/signup/">Signup</Link></li>
			            </ul>
			        </nav>
			    </div>
			</header>
		)
	}
});

export default Header;