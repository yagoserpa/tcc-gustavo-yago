import React from "react";
import { Container, Typography } from "@material-ui/core";
import LoginForm from "../components/LoginForm";

function LoginPage() {
  return (
    <Container component="article" maxWidth="sm">
      <Typography variant="h3" component="h1" align="center">
        Login
      </Typography>
      <LoginForm />
    </Container>
  );
}

export default LoginPage;
