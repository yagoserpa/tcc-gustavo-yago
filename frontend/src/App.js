import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import NavHeader from "./components/NavHeader";
import LoginPage from "./pages/LoginPage";
import PublicPage from "./pages/PublicPage";
import Contador from "./components/Contador";
import Planetas from "./components/Planetas";

function App() {
  return (
    <Router>
      <div id="pagina">
        <NavHeader />
        <Switch>
          <Route exact path="/">
            Home
          </Route>
          <Route path="/login">
            <LoginPage />
          </Route>
          <Route path="/public">
            <PublicPage />
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
