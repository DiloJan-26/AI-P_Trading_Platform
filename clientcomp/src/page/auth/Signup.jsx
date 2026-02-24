// Step 39 - creating signup component for auth page (step 36 in Auth.jsx) and then create forgot password component later

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
import { register } from "@/state/auth/Action";

const Signup = ({ onSignin }) => {

  // stc 9 - in the signup component, we will create a form to capture user details (full name, email, password) and dispatch the register action when the user submits the form. The register action will make an API call to the backend server to create a new user account and update the authentication state in the Redux store based on the response from the server.
  const dispatch = useDispatch();
  const [form, setForm] = useState({ fullName: "", email: "", password: "" });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleRegister = () => {
    dispatch(register(form));
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
        <CardDescription className="text-base">
          Create New Account
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="space-y-3">
          <Input name="fullName" type="text" placeholder="Enter your full name" onChange={handleChange} />
          <Input name="email" type="email" placeholder="enter your email" onChange={handleChange} />
          <Input name="password" type="password" placeholder="Enter your password" onChange={handleChange} />
        </div>
        <Button className="w-full" size="lg" type="button" onClick={handleRegister}>
          Register
        </Button>
      </CardContent>
      <CardFooter className="justify-center gap-2 text-sm text-muted-foreground">
        <span>already have account ?</span>
        <Button
          className="px-2"
          variant="link"
          type="button"
          onClick={onSignin}
        >
          signin
        </Button>
      </CardFooter>
    </Card>
  );
};

export default Signup;
