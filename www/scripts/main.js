import React  from 'react';
import ReactDOM  from 'react-dom';
import { Router, Route, Navigation, IndexRoute } from 'react-router';
import history from './history'

import App from './components/App';
import GameIntro from './components/GameIntro';
import Game from './components/Game';
import SelectTemplate from './components/SelectTemplate';
import TemplateList from './components/TemplateList';
import TemplateEditor from './components/TemplateEditor';
import Account from './components/Account';
import SignUp from './components/SignUp';
import LogIn from './components/LogIn';
import NotFound from './components/NotFound';


var routes = (
	<Router history={history}>
		<Route path="/" component={App}>
			<IndexRoute component={GameIntro}/>
			<Route path="/select-template/" components={SelectTemplate} />
			<Route path="/game/:gameID" component={Game} />
			<Route path="/templates" component={TemplateList} />
			<Route path="/template/:templateID" component={TemplateEditor} />
			<Route path="/account" component={Account} />
			<Route path="/signup" component={SignUp} />
			<Route path="/login" component={LogIn} />
	    	<Route path="*" component={NotFound}/>
	    </Route>
	</Router>
);

ReactDOM.render(
	routes
, document.getElementById('app-root'));