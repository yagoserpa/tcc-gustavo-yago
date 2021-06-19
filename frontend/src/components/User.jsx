import React from "react";

function User({ user }) {
  return (
    <>
      <span>{user.name}</span>
      <span>{user.position}</span>
      <span>{user.user_profile}</span>
    </>
  );
}

export default User;
