// Step 36 - creating auth page with 3 views (signin, signup, forgot password) and intergrate it with navbar
// and get the connection at APP.jsx page (Step 37)

import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Signin from "./Signin";
import Signup from "./Signup";
import ForgotPassword from "./ForgotPassword";

const Auth = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const path = location.pathname;

  const view =
    path === "/signup"
      ? "signup"
      : path === "/forgot-password"
        ? "forgot"
        : "signin";

  let content = null;
  if (view === "signin") {
    content = (
      <Signin
        onSignup={() => navigate("/signup")}
        onForgot={() => navigate("/forgot-password")}
      />
    );
  }
  if (view === "signup") {
    content = <Signup onSignin={() => navigate("/signin")} />;
  }
  if (view === "forgot") {
    content = <ForgotPassword onSignin={() => navigate("/signin")} />;
  }

  return (
    <div className="relative min-h-svh overflow-hidden bg-background text-foreground">
      <div className="absolute inset-0 bg-[radial-gradient(45%_60%_at_15%_20%,rgba(244,175,36,0.25),transparent)]" />
      <div className="absolute inset-0 bg-[radial-gradient(40%_55%_at_80%_10%,rgba(14,116,144,0.2),transparent)]" />
      <div className="absolute inset-0 bg-[radial-gradient(50%_50%_at_80%_80%,rgba(120,60,20,0.25),transparent)]" />
      <div className="relative z-10 flex min-h-svh items-center justify-center px-4 py-12">
        {content}
      </div>
    </div>
  );
};

export default Auth;
