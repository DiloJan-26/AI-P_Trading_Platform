// Step 39 - creating signup component for auth page (step 36 in Auth.jsx) and then create forgot password component later

import React from "react";
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

const Signup = ({ onSignin }) => {
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
          <Input type="text" placeholder="Enter your full name" />
          <Input type="email" placeholder="enter your email" />
          <Input type="password" placeholder="Enter your password" />
        </div>
        <Button className="w-full" size="lg" type="button">
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
