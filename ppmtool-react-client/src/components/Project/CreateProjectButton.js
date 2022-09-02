import React from "react";
import { Link } from "react-router-dom";

//Genera boton que nos redirige a crear un proyecto
const CreateProjectButton = () => {
  return (
    <React.Fragment>
      <Link to="/addProject" className="btn btn-lg btn-info">
        Create a Project
      </Link>
    </React.Fragment>
  );
};

export default CreateProjectButton;
