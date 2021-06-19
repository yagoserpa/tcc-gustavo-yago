import React from "react";
import { Link } from "react-router-dom";

function UserList({ userList }) {
  return (
    <ul>
      {userList.map((user) => (
        <li key={user.id}>
          <Link to={`/user/${user.id}/fields`}>{user.name}</Link>
        </li>
      ))}
    </ul>
  );
}

export default UserList;
