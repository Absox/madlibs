var React = require('react');
var ReactDOM = require('react-dom');
var ReactRouter = require('react-router');
var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var Navigation = ReactRouter.Navigation;
var createBrowserHistory = require('history/lib/createBrowserHistory');

import GameIntro from './components/GameIntro';
import Game from './components/Game';
import TemplateList from './components/TemplateList';
import TemplateEditor from './components/TemplateEditor';
import Account from './components/Account';
import SignUp from './components/SignUp';
import LogIn from './components/LogIn';
import NotFound from './components/NotFound';

var routes = (
	<Router history={createBrowserHistory()}>
		<Route path="/" component={GameIntro} />
		<Route path="/game/:gameID" component={Game} />
		<Route path="/templates" component={TemplateList} />
		<Route path="/templates/:templateID" component={TemplateEditor} />
		<Route path="/account" component={Account} />
		<Route path="/signup" component={SignUp} />
		<Route path="/login" component={LogIn} />
    	<Route path="*" component={NotFound}/>
	</Router>
);

ReactDOM.render(
	routes
, document.getElementById('site-root'));
