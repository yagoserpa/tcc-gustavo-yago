import React from "react";
import { useAuth } from "../contexts/AuthContext";
import SignedInHomePage from "./SignedInHomePage";

function HomePage() {
  const { signed } = useAuth();

  return <>{signed ? <SignedInHomePage /> : <p>Home</p>}</>;
}

export default HomePage;
