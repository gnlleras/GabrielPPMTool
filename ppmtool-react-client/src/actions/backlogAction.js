import axios from "axios";
import { GET_ERRORS } from "./types";

export const addProjectTask =
  (backlog_id, project_task, history) => async (dispatch) => {
    try {
      await axios.post(`/api/backlog/${backlog_id}`, project_task);
      history.push(`/projectBoard/${backlog_id}`);
      dispatch({
        //aca se toman los errores desde la BD y se cargan al payload
        type: GET_ERRORS,
        payload: {},
      });
    } catch (err) {
      dispatch({
        //aca se toman los errores desde la BD y se cargan al payload
        type: GET_ERRORS,
        payload: err.response.data,
      });
    }
  };
