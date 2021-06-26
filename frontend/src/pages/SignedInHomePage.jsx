import React, { useState, useEffect } from "react";
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
import { apiGet } from "../service/api";
import { useAuth } from "../contexts/AuthContext";

function SignedInHomePage() {
  const { user } = useAuth();
  const [projectsList, setProjectsList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    function onListLoaded(data) {
      setProjectsList(data);
      setLoading(false);
    }

    apiGet("user/projects", onListLoaded);
  }, [setProjectsList, setLoading]);

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
            <TableCell align="right">Página pessoal {user.userType}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {projectsList.map((project) => (
            <TableRow key={project.id}>
              <TableCell component="th" scope="row">
                <Link to={`/project/${project.id}`}>{project.title}</Link>
              </TableCell>
              <TableCell align="right">{project.subject}</TableCell>
            </TableRow>
          ))}
          {showLoadingCell()}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default SignedInHomePage;
