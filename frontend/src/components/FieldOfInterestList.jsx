import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  TableContainer,
  Paper,
  TableBody,
  TableRow,
  TableCell,
  Table,
  TableHead,
  TextField,
  LinearProgress,
} from "@material-ui/core";

function FieldOfInterestList({ fieldOfInterests, loading }) {
  const [search, setSearch] = useState("");

  function showLoadingCell() {
    if (loading) {
      return (
        <TableRow>
          <TableCell colSpan={2}>
            <LinearProgress />
          </TableCell>
        </TableRow>
      );
    }
  }

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell colSpan={2}>
              <TextField
                id="search"
                name="search"
                label="Buscar"
                variant="outlined"
                margin="dense"
                value={search}
                onChange={(event) => {
                  setSearch(event.target.value);
                }}
                fullWidth
              />
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell>Nome</TableCell>
            <TableCell align="right">Descrição</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {fieldOfInterests
            .filter(
              (i) =>
                i.name.toLowerCase().includes(search) ||
                i.description.toLowerCase().includes(search)
            )
            .map((field) => (
              <TableRow key={field.id}>
                <TableCell component="th" scope="row">
                  <Link to={`/field/${field.id}/users`}>{field.name}</Link>
                </TableCell>
                <TableCell align="right">{field.description}</TableCell>
              </TableRow>
            ))}
          {showLoadingCell()}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default FieldOfInterestList;
