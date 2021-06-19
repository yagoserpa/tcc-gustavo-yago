import React from "react";
import { Link } from "react-router-dom";

function FieldOfInterestList({ fieldOfInterests }) {
  return (
    <ul>
      {fieldOfInterests.map((field) => (
        <li key={field.id}>
          <Link to={`/field/${field.id}/users`}>
            <span>{field.name}</span>
            <span>{field.description}</span>
          </Link>
        </li>
      ))}
    </ul>
  );
}

export default FieldOfInterestList;
