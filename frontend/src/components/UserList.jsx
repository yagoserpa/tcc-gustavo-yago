import React from "react";
import { Link } from "react-router-dom";
import {
  TableContainer,
  Paper,
  TableBody,
  TableRow,
  TableCell,
  Table,
  TableHead,
  LinearProgress,
} from "@material-ui/core";

function UserList({ userList, loading }) {
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
            <TableCell>Nome</TableCell>
            <TableCell align="right">Página pessoal</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {userList.map((user) => (
            <TableRow key={user.id}>
              <TableCell component="th" scope="row">
                <Link to={`/user/${user.id}/fields`}>{user.name}</Link>
              </TableCell>
              <TableCell align="right">{user.user_profile}</TableCell>
            </TableRow>
          ))}
          {showLoadingCell()}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default UserList;
