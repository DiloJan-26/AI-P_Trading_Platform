// Step 38 - creating signin component for auth page (step 36 in Auth.jsx as default view) and then create signup and forgot password component later
import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { useDispatch } from "react-redux";
import { login } from "@/state/auth/Action";

const Signin = ({ onSignup, onForgot }) => {
  // stc 10 - in the signin component, we will create a form to capture user credentials (email and password) and dispatch the login action when the user submits the form. The login action will make an API call to the backend server to authenticate the user and update the authentication state in the Redux store based on the response from the server.
  // stc 11 - this is about refine cors error so go to backend > Appconfig.java
  const dispatch = useDispatch();
  const [form, setForm] = useState({ email: "", password: "" });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleLogin = () => {
    dispatch(login(form));
  };

  return (
    <Card className="w-full max-w-md border-border/50 bg-card/80 shadow-2xl backdrop-blur">
      <CardHeader className="space-y-2 text-center">
        <CardTitle className="flex justify-center items-center gap-1 text-3xl font-semibold tracking-tight">
          <Avatar>
            <AvatarImage src="./src/assets/Company_Logo2.png" />
          </Avatar>
          <div>
            <span className="font-bold text-cyan-700">DJ </span>
            <span className="font-bold text-slate-500">Trad</span>
          </div>
        </CardTitle>
        <CardDescription className="text-base">Login</CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="space-y-3">
          <Input name="email" type="email" placeholder="enter your email" onChange={handleChange} />
          <Input name="password" type="password" placeholder="Enter your password" onChange={handleChange} />
        </div>
        <Button className="w-full" size="lg" type="button" onClick={handleLogin}>
          Login
        </Button>
        <Button
          className="w-full"
          variant="outline"
          type="button"
          onClick={onForgot}
        >
          Forgot Password ?
        </Button>
      </CardContent>
      <CardFooter className="justify-center gap-2 text-sm text-muted-foreground">
        <span>already have account ?</span>
        <Button
          className="px-2"
          variant="link"
          type="button"
          onClick={onSignup}
        >
          signup
        </Button>
      </CardFooter>
    </Card>
  );
};

export default Signin;
