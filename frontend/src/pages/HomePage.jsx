import React from "react";
import { useHistory } from "react-router-dom";

function HomePage({ loggedInUser }) {
  const history = useHistory();

  if (loggedInUser.user != null) {
    history.push("/public");
  }

  return <p>Home</p>;
}

export default HomePage;
