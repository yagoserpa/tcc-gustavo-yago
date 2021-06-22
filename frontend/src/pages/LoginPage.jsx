import React from "react";
import { useHistory } from "react-router-dom";
import { Container, Typography } from "@material-ui/core";
import LoginForm from "../components/LoginForm";
import { useAuth } from "../contexts/AuthContext";

function LoginPage() {
  const { signed, login } = useAuth();
  let history = useHistory();

  if (signed) {
    history.push("/");
  }

  function doLogin(email, password) {
    login(email, password).catch(() => {
      history.push("/404");
    });
  }

  return (
    <Container component="article" maxWidth="sm">
      <Typography variant="h3" component="h1" align="center">
        Login
      </Typography>
      <LoginForm onSubmitClicked={doLogin} />
    </Container>
  );
}

export default LoginPage;
