import React from "react";

function User({ user }) {
  return (
    <>
      <span>{user.name}</span>
      <span>{user.position}</span>
      <span>{user.userProfile}</span>
    </>
  );
}

export default User;
