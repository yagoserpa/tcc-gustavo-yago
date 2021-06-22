import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import NavHeader from "./components/NavHeader";
import LoginPage from "./pages/LoginPage";
import PublicPage from "./pages/PublicPage";
import NotFoundPage from "./pages/NotFoundPage";
import HomePage from "./pages/HomePage";
import FieldOfInterestPage from "./pages/FieldOfInterestPage";
import UserPage from "./pages/UserPage";
import ProjectPage from "./pages/ProjectPage";

function App() {
  return (
    <AuthProvider>
      <Router>
        <div id="pagina">
          <NavHeader />
          <Switch>
            <Route exact path="/">
              <HomePage />
            </Route>
            <Route path="/login">
              <LoginPage />
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
            <Route path="/project/:id">
              <ProjectPage />
            </Route>
            <Route>
              <NotFoundPage />
            </Route>
          </Switch>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
