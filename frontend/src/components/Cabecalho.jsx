import React from "react";
import { Link } from "react-router-dom";

function Cabecalho() {
  return (
    <nav>
      <div className="logo">YAGO PAI</div>
      <div>
        <Link to="/">Home</Link>
      </div>
      <div>
        <Link to="/login">Login</Link>
      </div>
      <div>
        <Link to="/contador">Contador</Link>
      </div>
      <div>
        <Link to="/planetas">Planetas</Link>
      </div>
    </nav>
  );
}

export default Cabecalho;
