import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import Contador from './components/Contador';
import Planetas from './components/Planetas';

function App() {
  return (
    <Router>
      <div id="pagina">
        <nav>
          <div className="logo">YAGO</div>
          <div>
            <Link to="/">Home</Link>
          </div>
          <div>
            <Link to="/contador">Contador</Link>
          </div>
          <div>
            <Link to="/planetas">Planetas</Link>
          </div>
        </nav>
        <Switch>
          <Route path="/" exact>
            Home
          </Route>
          <Route path="/contador">
            <Contador />
          </Route>
          <Route path="/planetas">
            <Planetas title="Título" />
          </Route>
        </Switch>
      </div>
    </Router>
  )
}

export default App;
