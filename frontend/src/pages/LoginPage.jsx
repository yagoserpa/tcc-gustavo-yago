import React from "react";
import { Container, Typography } from "@material-ui/core";
import FormularioLogin from "../components/FormularioLogin";

function LoginPage() {
  return (
    <Container component="article" maxWidth="sm">
      <Typography variant="h3" component="h1" align="center">
        Login
      </Typography>
      <FormularioLogin />
    </Container>
  );
}

export default LoginPage;
