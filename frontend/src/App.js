import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Cabecalho from "./components/Cabecalho";
import LoginPage from "./pages/LoginPage";
import Contador from "./components/Contador";
import Planetas from "./components/Planetas";

function App() {
  return (
    <Router>
      <div id="pagina">
        <Cabecalho />
        <Switch>
          <Route exact path="/">
            Home
          </Route>
          <Route path="/login">
            <LoginPage />
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
  );
}

export default App;
