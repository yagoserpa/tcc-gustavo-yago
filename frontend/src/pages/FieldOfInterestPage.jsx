import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useHistory } from "react-router-dom";
import UserList from "../components/UserList";
import { getFieldOfInterestUsers } from "../service/api";

function FieldOfInterestPage() {
  let history = useHistory();
  const { id } = useParams();
  const [fieldOfInterestUserList, setFieldOfInterestUserList] = useState([]);

  useEffect(() => {
    getFieldOfInterestUsers(id, setFieldOfInterestUserList).catch(() => {
      history.push("/404");
    });
  }, [id, setFieldOfInterestUserList, history]);

  return <UserList userList={fieldOfInterestUserList} />;
}

export default FieldOfInterestPage;
