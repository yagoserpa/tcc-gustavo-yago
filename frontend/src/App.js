import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import NavHeader from "./components/NavHeader";
import LoginPage from "./pages/LoginPage";
import PublicPage from "./pages/PublicPage";
import NotFoundPage from "./pages/NotFoundPage";
import HomePage from "./pages/HomePage";
import FieldOfInterestPage from "./pages/FieldOfInterestPage";
import LoggedInUser from "./data/user";
import UserPage from "./pages/UserPage";

function App() {
  const loggedInUser = new LoggedInUser();

  return (
    <Router>
      <div id="pagina">
        <NavHeader loggedInUser={loggedInUser} />
        <Switch>
          <Route exact path="/">
            <HomePage loggedInUser={loggedInUser} />
          </Route>
          <Route path="/login">
            <LoginPage loggedInUser={loggedInUser} />
          </Route>
          <Route path="/public">
            <PublicPage />
          </Route>
          <Route path="/field/:id/users">
            <FieldOfInterestPage />
          </Route>
          <Route path="/user/:id/fields">
            <UserPage />
          </Route>
          <Route>
            <NotFoundPage />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
