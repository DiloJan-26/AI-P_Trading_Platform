// step 17.11 - create a new page for not found and add dummy content there
import React from "react";
import { Navigate } from "react-router-dom";

const NotFound = () => {
  return <Navigate to="/signin" replace />;
};

export default NotFound;
