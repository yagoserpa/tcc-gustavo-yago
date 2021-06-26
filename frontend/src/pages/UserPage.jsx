import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useHistory } from "react-router-dom";
import { apiGet } from "../service/api";
import User from "../components/User";

function UserPage() {
  let history = useHistory();
  const { id } = useParams();
  const [user, setUser] = useState({});

  useEffect(() => {
    apiGet(`user/${id}`, setUser).catch(() => {
      history.push("/404");
    });
  }, [id, setUser, history]);

  return <User user={user} />;
}

export default UserPage;
