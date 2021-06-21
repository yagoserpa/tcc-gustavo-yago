import React from "react";
import { useHistory } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

function HomePage() {
  const { signed } = useAuth();
  const history = useHistory();

  if (signed) {
    history.push("/public");
  }

  return <p>Home</p>;
}

export default HomePage;
