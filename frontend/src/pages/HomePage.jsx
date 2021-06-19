import React from "react";
import { useHistory } from "react-router-dom";

function HomePage() {
  const history = useHistory();
  const token = localStorage.getItem("token");

  if (token != null) {
    history.push("/public");
  }

  return <p>Home</p>;
}

export default HomePage;
