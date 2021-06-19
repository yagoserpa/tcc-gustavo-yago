import React, { useState } from "react";
import { TextField, Button, Container } from "@material-ui/core";
import FieldOfInterestList from "../components/FieldOfInterestList";
import { useEffect } from "react";
import { getFieldOfInterests } from "../service/api";

function PublicPage() {
  const [search, setSearch] = useState();
  const [fieldOfInterestList, setFieldOfInterestList] = useState([]);

  useEffect(() => {
    getFieldOfInterests(setFieldOfInterestList);
  }, [setFieldOfInterestList]);

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
      <FieldOfInterestList fieldOfInterests={fieldOfInterestList} />
    </Container>
  );
}

export default PublicPage;
