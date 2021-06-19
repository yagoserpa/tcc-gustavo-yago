import React from "react";
import { useState } from "react";
import { TextField, Button } from "@material-ui/core";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [pass, setPass] = useState("");

  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
      }}
    >
      <TextField
        id="email"
        name="email"
        label="Email"
        type="email"
        variant="outlined"
        margin="normal"
        value={email}
        onChange={(event) => {
          setEmail(event.target.value);
        }}
        required
        fullWidth
      />
      <TextField
        id="senha"
        name="senha"
        label="Senha"
        type="password"
        variant="outlined"
        margin="normal"
        value={pass}
        onChange={(event) => {
          setPass(event.target.value);
        }}
        required
        fullWidth
      />
      <Button variant="contained" color="primary" type="submit" fullWidth>
        Entrar
      </Button>
    </form>
  );
}

export default LoginForm;
