import React from "react";
import { Button } from "@material-ui/core";
import { Link, useHistory } from "react-router-dom";

function NavHeader() {
  const history = useHistory();
  const token = localStorage.getItem("token");

  function logout() {
    localStorage.clear();
    history.push("/");
  }

  // TODO: mostar so se tiver logado
  function showLogoutButton() {
    if (token != null) {
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
      <div>
        <Link to="/login">Login</Link>
      </div>
      <div>
        <Link to="/public">Área Pública</Link>
      </div>
      {showLogoutButton()}
    </nav>
  );
}

export default NavHeader;
