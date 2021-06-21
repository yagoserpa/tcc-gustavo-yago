import React from "react";
import { Button } from "@material-ui/core";
import { Link, useHistory } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

function NavHeader() {
  const { signed, logout } = useAuth();
  const history = useHistory();

  function doLogout() {
    logout(onLogout);
  }

  function onLogout() {
    history.push("/");
  }

  function showLoginLink() {
    if (!signed) {
      return (
        <div>
          <Link to="/login">Login</Link>
        </div>
      );
    }
  }

  function showLogoutButton() {
    if (signed) {
      return (
        <Button variant="contained" color="primary" onClick={doLogout}>
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
