// stc 7 - this step focuses on creating a reducer to handle the authentication state based on the actions defined in Action.js. The reducer will update the state in the Redux store based on the dispatched actions from the React components.

import {
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  REGISTER_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  GET_USER_FAILURE,
} from "./ActionTypes";

const initialState = {
  loading: false,
  user: null,
  error: null,
  jwt: null,
};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case GET_USER_REQUEST:
      return { ...state, loading: true, error: null };


    case REGISTER_SUCCESS:
    case LOGIN_SUCCESS:
      return { ...state, loading: false, error: null, jwt: action.payload };


    case GET_USER_SUCCESS:
      return { ...state, loading: false, user: action.payload, error: null };


    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case GET_USER_FAILURE:
      return { ...state, loading: false, error: action.payload };


    default:
      return state;
  }
};

export default authReducer;
