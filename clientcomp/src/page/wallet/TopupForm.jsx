// Step 23 - create a topup form component and add it to the wallet page
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { DotFilledIcon } from "@radix-ui/react-icons";
import React from "react";

const TopupForm = () => {
  const [amount, setAmount] = React.useState("");
  const [paymentMethod, setPaymentMethod] = React.useState("RAZORPAY");

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handlePaymentMethodChange = (value) => {
    setPaymentMethod(value);
  };

  const handleSubmit = () => {
    // Logic to handle the top-up process based on the selected payment method and amount
    console.log(`Top-up Amount: ${amount}, Payment Method: ${paymentMethod}`);
  };

  return (
    <div>
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          placeholder="$99999"
          onChange={handleChange}
          value={amount}
          className="py-7 text-lg"
        />
      </div>

      <div>
        <h1 className="pb-1 mt-5">Select Payment Method</h1>
        <RadioGroup
          onValueChange={(value) => handlePaymentMethodChange(value)}
          className="flex"
          defaultValue="RAZORPAY"
        >
          <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
            <RadioGroupItem
              value="RAZORPAY"
              icon={DotFilledIcon}
              className="h-9 w-9"
              id="r1"
            />

            <Label htmlFor="r1">
                <div className="bg-white rounded-md px-1 py-1 w-20">
                    <img src="./src/assets/Razorpay.png" alt="Razorpay" className=""/>
                </div>

            </Label>
          </div>

           <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
            <RadioGroupItem
              value="STRIPE"
              icon={DotFilledIcon}
              className="h-9 w-9"
              id="r2"
            />

            <Label htmlFor="r2">
                <div className="bg-white rounded-md px-2 py-2 w-15">
                    <img src="./src/assets/Stripe.png" alt="Stripe" className=""/>
                </div>

            </Label>
          </div>
        </RadioGroup>
      </div>
      <Button className='w-full py-5 mt-5' onClick={handleSubmit}>
        <span className="text-xl">Submit</span>
      </Button>
    </div>
  );
};

export default TopupForm;
