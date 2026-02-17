// Step 24 - create a withdrawal form component and add it to the wallet page
import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import React from "react";

const WithdrawalForm = () => {
  const [amount, setAmount] = React.useState("");

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handleSubmit = () => {
    // Logic to handle the withdrawal process based on the entered amount
    console.log(`Withdrawal Amount: ${amount}`);
  };

  return (
    <div className="pt-10 space-y-5">
      <div className="flex justify-between items-center rounded-md bg-slate-900 text-xl font-bold px-5 py-4">
        <p>Available balance</p>
        <p>$90000</p>
      </div>
      <div className="flex flex-col items-center">
        <h1>Enter withdrawal amount</h1>
        <div className="flex items-center justify-center">
          <Input
            onChange={handleChange}
            value={amount}
            className="withdrawalInput py-7 border-none outline-none focus:outline-none px-0 text-2xl text-center"
            placeholder="0.00"
            type="number"
          />
        </div>
      </div>
      <div>
        <p className="pb-2">Transfer to</p>
        <div className="flex items-center gap-5 border px-5 py-2 rounded-md">
          <img src="./src/assets/bank.png" alt="bank" className="w-8 h-8" />
          <div>
            <p className="text-xl font-bold">YeS Bank</p>
            <p className="text-xs">******1234</p>
          </div>
        </div>
      </div>
      <DialogClose className="w-full">
        <Button className="w-full py-7 text-xl" onClick={handleSubmit}>
          Withdraw
        </Button>
      </DialogClose>
    </div>
  );
};

export default WithdrawalForm;
