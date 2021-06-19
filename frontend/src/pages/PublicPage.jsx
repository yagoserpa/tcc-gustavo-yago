import React, { useState } from "react";
import { TextField, Button, Container } from "@material-ui/core";

function PublicPage() {
  const [search, setSearch] = useState();

  return (
    <Container>
      <form
        onSubmit={(event) => {
          event.preventDefault();
        }}
      >
        <TextField
          id="search"
          name="search"
          label="Busca"
          variant="outlined"
          margin="normal"
          value={search}
          onChange={(event) => {
            setSearch(event.target.value);
          }}
        />
        <Button variant="contained" color="primary" type="submit">
          Buscar
        </Button>
      </form>
    </Container>
  );
}

export default PublicPage;
