import React from 'react';
import ReactDOM from 'react-dom';
import { Link, History } from 'react-router';
var Auth = require('../auth');


var Header = React.createClass({
	mixins: [History],

	getInitialState: function() {
		return {
			username: Auth.getCurrentUser() ? Auth.getCurrentUser() : "anon"
		}
	},

	handleAccountToggle: function(e) {
		var button = e.currentTarget;

		button.classList.toggle('open');
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
			            <ul className="site-nav__list">
			                <li className="site-nav__item">
			                	<Link className="site-nav__link" to="/templates/">Templates</Link>
			                </li>

			                <li className="site-nav__item">
			                	<Link className="site-nav__link" to="/account/">
			                		Welcome <span className="current-user">{this.state.username}</span>
				                	<img src="/images/member.svg"/>
			                	</Link>
			                </li>
			            </ul>
			        </nav>
			    </div>
			</header>
		)
	}
});

export default Header;