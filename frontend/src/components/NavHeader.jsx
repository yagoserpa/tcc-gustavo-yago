import React from "react";
import { Button } from "@material-ui/core";
import { Link, useHistory } from "react-router-dom";
import { useEffect } from "react";
import { useState } from "react";

function NavHeader({ loggedInUser }) {
  const history = useHistory();
  const [user, setUser] = useState();

  useEffect(() => {
    function handleLoggedInUserChanged(newUser) {
      setUser(newUser);
    }

    loggedInUser.subscribe(handleLoggedInUserChanged);

    return function cleanup() {
      loggedInUser.unsubscribe(handleLoggedInUserChanged);
    };
  }, [loggedInUser]);

  function logout() {
    loggedInUser.clearLoggedInUser();
    history.push("/");
  }

  function showLoginLink() {
    if (user == null) {
      return (
        <div>
          <Link to="/login">Login</Link>
        </div>
      );
    }
  }

  function showLogoutButton() {
    if (user != null) {
      return (
        <Button variant="contained" color="primary" onClick={logout}>
          Sair
        </Button>
      );
    }
  }

  return (
    <nav>
      <div className="logo">GeProFi</div>
      <div>
        <Link to="/">Home</Link>
      </div>
      {showLoginLink()}
      <div>
        <Link to="/public">Área Pública</Link>
      </div>
      {showLogoutButton()}
    </nav>
  );
}

export default NavHeader;
