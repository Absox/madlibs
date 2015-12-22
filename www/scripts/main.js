var React = require('react');
var ReactDOM = require('react-dom');
var ReactRouter = require('react-router');
var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var Navigation = ReactRouter.Navigation;
var createBrowserHistory = require('history/lib/createBrowserHistory');

import GameIntro from './components/GameIntro';
import GameUI from './components/GameUI';
import NotFound from './components/NotFound';


var routes = (
	<Router history={createBrowserHistory()}>
		<Route path="/" component={GameIntro} />
		<Route path="/game/:gameId" component={GameUI} />
    	<Route path="*" component={NotFound}/>
	</Router>
);

ReactDOM.render(
	routes
, document.getElementById('game-root'));
