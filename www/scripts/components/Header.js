import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';


var Header = React.createClass({
	mixins: [History],

	getDefaultProps: function() {
		return {
			currentUser: "anon"
		};
	},

	render : function() {

		return (
			<header className="site-header" id="site-header">
				<div className="wrapper">
			        <Link to="/" className="site-logo-link">
			            <span className="site-logo">M</span>
			            <span className="site-title">Mad Libs</span>
			        </Link>

			        <nav className="site-nav">
			            <ul>
			                <li><Link to="/templates/">Templates</Link></li>

			                <li><Link to="/account/">
			                	Welcome, <span class="current-user">{this.props.currentUser}</span>
			                	<img src="/images/member.svg"/>
			                </Link></li>
			            </ul>
			        </nav>
			    </div>
			</header>
		)
	}
});

export default Header;