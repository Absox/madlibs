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

	handleAccountToggle: function(e) {
		var button = e.currentTarget;
		console.log(button);

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
			                <li className="site-nav__item"><Link className="site-nav__link" to="/templates/">Templates</Link></li>

			                <li className="site-nav__item">
			                	<button className="site-nav__button" onClick={this.handleAccountToggle}>
				                	Welcome <span className="current-user">{this.props.currentUser}</span>
				                	<img src="/images/member.svg"/>
			                	</button>

			                	<ul className="site-nav-dropdown">
			                		<li className="site-nav-dropdown__item">
			                			<Link className="site-nav-dropdown__link" to="/signup/">Sign up</Link>
			                		</li>

			                		<li className="site-nav-dropdown__item">
			                			<Link className="site-nav-dropdown__link" to="/login/">Log in</Link>
			                		</li>

			                		<li className="site-nav-dropdown__item">
			                			<Link className="site-nav-dropdown__link" to="/account/">Your account</Link>
			                		</li>
			                	</ul>
			                </li>
			            </ul>
			        </nav>
			    </div>
			</header>
		)
	}
});

export default Header;