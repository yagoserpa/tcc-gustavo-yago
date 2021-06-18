import React from "react";
import { useState } from "react";
import { TextField, Button } from "@material-ui/core";

function FormularioLogin() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

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
        value={senha}
        onChange={(event) => {
          setSenha(event.target.value);
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

export default FormularioLogin;
