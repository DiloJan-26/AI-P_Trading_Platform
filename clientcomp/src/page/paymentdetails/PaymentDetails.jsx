// step 17.5 - create a new page for payment details and add dummy content there
{
  /* Step 27 - editing started */
}
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import React from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import PaymentDetailsForm from "./PaymentDetailsForm";
import { Button } from "@/components/ui/button";
import PaymentDetailsDialog from "./PaymentDetailsDialog";

const PaymentDetails = () => {
  return (
    <div className="px-20">
      <h1 className="text-3xl font-bold py-10">Payment Details :</h1>

      <Card>
        <CardHeader>
          <CardTitle>Yes Bank</CardTitle>
          <CardDescription>A/C No : *********2362</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex items-center">
            <p className="w-32">A/C holder :</p>
            <p className="text-gray-400">DJ comp</p>
          </div>
          <div className="flex items-center">
            <p className="w-32">IFSC :</p>
            <p className="text-gray-400">YESB0001234</p>
          </div>
        </CardContent>
      </Card>

      <Dialog>
        <DialogTrigger asChild>
          <Button className="py-6">Add Payment Details</Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Payment Details</DialogTitle>
          </DialogHeader>
          {/* <PaymentDetailsForm /> */}
          <PaymentDetailsDialog />
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default PaymentDetails;
