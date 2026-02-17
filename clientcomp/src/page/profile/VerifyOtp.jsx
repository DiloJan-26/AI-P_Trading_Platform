// Step 31 - create a new page for OTP verification and add dummy content there
import React from "react";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import {
  InputOTP,
  InputOTPGroup,
  InputOTPSeparator,
  InputOTPSlot,
} from "@/components/ui/input-otp";
import { ButtonIcon } from "@radix-ui/react-icons";
import { Card } from "@/components/ui/card";

const VerifyOtp = () => {
  const [value, setValue] = React.useState("");

  const handleSubmit = () => {
    // Here you can handle the OTP submission logic, such as sending the OTP to your backend for verification.
    console.log("Submitted OTP:", value);
    // You can also add error handling and success messages based on the response from your backend.
  };

  return (
    <div className="flex">
        <div className="py-5 flex gap-5 justify-center items-center">
          <div className="flex gap-3">
            <p>Email:</p>
            <p>dilojanrvinthirasaea@gmail.com</p>
          </div>
          <Dialog>
            <DialogTrigger>
              <Button>Send OTP</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Enter OTP</DialogTitle>
              </DialogHeader>
              <div className="py-5 flex gap-10 justify-center items-center">
                <InputOTP
                  value={value}
                  onChange={(value) => setValue(value)}
                  maxLength={6}
                >
                  <InputOTPGroup>
                    <InputOTPSlot index={0} />
                    <InputOTPSlot index={1} />
                    <InputOTPSlot index={2} />
                  </InputOTPGroup>
                  {/* <InputOTPSeparator /> */}
                  <InputOTPGroup>
                    <InputOTPSlot index={3} />
                    <InputOTPSlot index={4} />
                    <InputOTPSlot index={5} />
                  </InputOTPGroup>
                </InputOTP>
                <DialogClose>
                  <Button onClick={handleSubmit} className="w-40">
                    Submit
                  </Button>
                </DialogClose>
              </div>
            </DialogContent>
          </Dialog>
        </div>
    </div>
  );
};

export default VerifyOtp;
