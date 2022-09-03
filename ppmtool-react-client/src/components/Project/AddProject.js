import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../actions/projectAction";
import classnames from "classnames";

class AddProject extends Component {
  constructor() {
    super();
    //Estado inicial de los campos del proyecto.
    this.state = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      start_date: "",
      end_date: "",
      errors: {},
    };
    //Operaciones que permiten el cambio de estado de los campos.
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  //Life cycle hooks. Almacena los errors en el state para que pueda ser utilizado en el render.
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  //Setea valores en cada campo en particular.
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  //Crea un nuevo objeto con los valores ingresados.
  onSubmit(e) {
    e.preventDefault();
    const newProject = {
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      start_date: this.state.start_date,
      end_date: this.state.end_date,
    };
    this.props.createProject(newProject, this.props.history);
  }
  //Devuelve los campos con valores actualizados en ejecucion.
  render() {
    const { errors } = this.state;
    return (
      <div>
        <div className="container">
          <div className="project">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Create Project form</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.projectName, //cuando haya errores en projectName se cambia el msj
                      })}
                      placeholder="Project Name"
                      name="projectName"
                      value={this.state.projectName}
                      onChange={this.onChange}
                    />
                    {errors.projectName && ( //error desde BD
                      //style bootstrap
                      <div className="invalid-feedback">
                        {errors.projectName}
                      </div>
                    )}
                  </div>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.projectIdentifier, //cuando haya errores en projectName se cambia el msj
                      })}
                      placeholder="Unique Project ID"
                      name="projectIdentifier"
                      value={this.state.projectIdentifier}
                      onChange={this.onChange}
                    />
                    {errors.projectIdentifier && ( //error desde BD
                      //style bootstrap
                      <div className="invalid-feedback">
                        {errors.projectIdentifier}
                      </div>
                    )}
                  </div>

                  <div className="form-group">
                    <textarea
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.description, //cuando haya errores en projectName se cambia el msj
                      })}
                      placeholder="Project Description"
                      name="description"
                      value={this.state.description}
                      onChange={this.onChange}
                    ></textarea>
                    {errors.description && ( //error desde BD
                      //style bootstrap
                      <div className="invalid-feedback">
                        {errors.description}
                      </div>
                    )}
                  </div>
                  <h6>Start Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="start_date"
                      value={this.state.start_date}
                      onChange={this.onChange}
                    />
                  </div>
                  <h6>Estimated End Date</h6>
                  <div className="form-group">
                    <input
                      type="date"
                      className="form-control form-control-lg"
                      name="end_date"
                      value={this.state.end_date}
                      onChange={this.onChange}
                    />
                  </div>

                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

AddProject.propTypes = {
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired, //componentWillReceiveProps pregunta por estas props
};

const mapStateToProps = (state) => ({
  errors: state.errors, //trae la data desde la State(Store) y la guarda en props. Se usa en componentWillReceiveProps
});

export default connect(mapStateToProps, { createProject })(AddProject);
