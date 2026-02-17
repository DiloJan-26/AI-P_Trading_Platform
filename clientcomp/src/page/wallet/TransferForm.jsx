// Step 25 - create a transfer form component and add it to the wallet page
import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import React from "react";

const TransferForm = () => {
  const [FormData, setFormData] = React.useState({
    amount: "",
    walletId: "",
    purpose: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...FormData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = () => {
    // Logic to handle the transfer process based on the entered amount, wallet ID, and purpose
    console.log(
      `Transfer Amount: ${FormData.amount}, Wallet ID: ${FormData.walletId}, Purpose: ${FormData.purpose}`,
    );
  };

  return (
    <div className="pt-10 space-y-5">
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          name="amount"
          placeholder="$99999"
          className="py-7"
          onChange={handleChange}
          value={FormData.amount}
        />
      </div>

      <div>
        <h1 className="pb-1">Wallet Id</h1>
        <Input
          name="walletId"
          placeholder="Enter Wallet Id"
          className="py-7"
          onChange={handleChange}
          value={FormData.walletId}
        />
      </div>

      <div>
        <h1 className="pb-1">Purpose</h1>
        <Input
          name="purpose"
          placeholder="Enter Purpose"
          className="py-7"
          onChange={handleChange}
          value={FormData.purpose}
        />
      </div>
      <DialogClose className="w-full">
        <Button className="w-full py-7" onClick={handleSubmit}>
          Submit
        </Button>
      </DialogClose>
    </div>
  );
};

export default TransferForm;
