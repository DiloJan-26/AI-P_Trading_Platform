import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import { BrowserRouter } from "react-router-dom";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    {/* Step 16 - wrap the <App /> component with <BrowserRouter> */}
    <BrowserRouter> 
      <App />
    </BrowserRouter>
  </StrictMode>,
);
