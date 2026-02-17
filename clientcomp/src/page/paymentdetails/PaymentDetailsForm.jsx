// Step 28 - create a payment details form component and add it to the payment details page
// there are no form components in officail shadcn ui site but I did older method as 'npx shadcn@latest add form ' for easiness but make sure to use the latest method for form component in future (PaymentDetailsDialog.jsx)
import { Form } from "@/components/ui/form";
import React from "react";

const PaymentDetailsForm = () => {
  const form = useForm({
    resolver: "",
    defaultValues: {
      accountHolderName: "",
      ifsc: "",
      accountNumber: "",
      bankName: "",
    },
  });

  const onSubmit = (values) => {
    console.log("Payment Details:", values);
    // TODO: send to API
  };

  return (
    <div className="px-10 py-2">
      <Form {...form}>
        
      </Form>
    </div>
  );
};

export default PaymentDetailsForm;
