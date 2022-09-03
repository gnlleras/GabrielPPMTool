import React, { Component } from "react";
import CreateProjectButton from "./Project/CreateProjectButton";
import ProjectItem from "./Project/ProjectItem";
import PropTypes from "prop-types";
import { connect } from "react-redux"; //para conectarnos con la store
import { getProjects } from "../actions/projectAction";

//Menu de proyectos
class Dashboard extends Component {
  componentDidMount() {
    //cuando el dashboard se monta(didMount), nos trae los projects
    this.props.getProjects();
  }

  render() {
    const { projects } = this.props.project;

    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />

              <CreateProjectButton />

              <br />
              <hr />
              {projects.map((project) => (
                <ProjectItem key={project.id} project={project} />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  getProjects: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project, //project nombre que aparece en el index, por eso trae todos los proyectos
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
