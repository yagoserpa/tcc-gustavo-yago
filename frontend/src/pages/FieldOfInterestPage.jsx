import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useHistory } from "react-router-dom";
import { Container, Typography } from "@material-ui/core";
import UserList from "../components/UserList";
import { apiGet } from "../service/api";

function FieldOfInterestPage() {
  let history = useHistory();
  const { id } = useParams();
  const [fieldOfInterestUserList, setFieldOfInterestUserList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    function onListLoaded(data) {
      setFieldOfInterestUserList(data);
      setLoading(false);
    }

    apiGet(`/field/${id}/users`, onListLoaded).catch(() => {
      history.push("/404");
    });
  }, [id, setFieldOfInterestUserList, setLoading, history]);

  return (
    <Container component="article">
      <Typography variant="h3" component="h1" align="center">
        Professores da Área de Interesse
      </Typography>
      <UserList userList={fieldOfInterestUserList} loading={loading} />
    </Container>
  );
}

export default FieldOfInterestPage;
