// Step 40 - creating forgot password component for auth page (step 36 in Auth.jsx)
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

const ForgotPassword = ({ onSignin }) => {
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
          Where do you want to get the code?
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <Input type="email" placeholder="enter your email" />
        <Button className="w-full" size="lg" type="button">
          Send OTP
        </Button>
        <Button className="w-full" variant="outline" type="button">
          Try Using Mobile Number
        </Button>
      </CardContent>
      <CardFooter className="justify-center gap-2 text-sm text-muted-foreground">
        <span>Back To Login ?</span>
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

export default ForgotPassword;
