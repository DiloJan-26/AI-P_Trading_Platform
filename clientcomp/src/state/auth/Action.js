// stc 6 - this step focuses on creating actions for authentication (register, login, get user profile) using axios to make API calls to the backend server. These actions will be dispatched from the React components to update the Redux store based on the API responses.

// before install axios - 'npm i axios --legacy-peer-deps' (where npm i axios gives error due to react version mismatch at eslint-plugin-react-hooks)

import axios from "axios";
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

export const register = (userData) => async (dispatch) => {
  dispatch({ type: REGISTER_REQUEST });

  const baseURL = "http://localhost:5454"; // Update with your backend URL

  try {
    const response = await axios.post(`${baseURL}/auth/signup`, userData);
    const user = response.data;
    console.log("Registration successful:", user);
    dispatch({ type: REGISTER_SUCCESS, payload: user.jwt });
    localStorage.setItem("jwt", user.jwt); // Store JWT in localStorage for later use
  } catch (error) {
    dispatch({ type: REGISTER_FAILURE, payload: error.message });
    console.error(
      "Registration failed:",
      error.response ? error.response.data : error.message,
    );
  }
};

export const login = (userData) => async (dispatch) => {
  dispatch({ type: LOGIN_REQUEST });

  const baseURL = "http://localhost:5454"; // Update with your backend URL

  try {
    const response = await axios.post(`${baseURL}/auth/signin`, userData);
    const user = response.data;
    console.log("Login successful:", user);
    dispatch({ type: LOGIN_SUCCESS, payload: user.jwt });
    localStorage.setItem("jwt", user.jwt); // Store JWT in localStorage for later use
  } catch (error) {
    dispatch({ type: LOGIN_FAILURE, payload: error.message });
    console.error(
      "Login failed:",
      error.response ? error.response.data : error.message,
    );
  }
};

export const getUser = (jwt) => async (dispatch) => {
  dispatch({ type: GET_USER_REQUEST });

  const baseURL = "http://localhost:5454"; // Update with your backend URL

  try {
    const response = await axios.get(`${baseURL}/api/users/profile`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    const user = response.data;
    console.log("Get user successful:", user);
    dispatch({ type: GET_USER_SUCCESS, payload: user });
  } catch (error) {
    dispatch({ type: GET_USER_FAILURE, payload: error.message });
    console.error(
      "Get user failed:",
      error.response ? error.response.data : error.message,
    );
  }
};
