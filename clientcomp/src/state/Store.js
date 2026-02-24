// stc 3 - State Management with Redux

import { thunk } from 'redux-thunk';
import { createStore, combineReducers, applyMiddleware, legacy_createStore } from 'redux';
import authReducer from './auth/Reducer';

const rootReducer = combineReducers({
  // stc 8 - combine the authReducer with other reducers if needed (e.g., productReducer, cartReducer) to create the root reducer for the Redux store.
  auth:authReducer
});

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));

// stc 9 - go to auth/signup.jsx