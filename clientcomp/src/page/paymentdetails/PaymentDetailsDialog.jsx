// Demo purpose this only shows the form in a dialog, but you can use the form standalone on the page as well
// this is alternate of PaymentDetailsForm.jsx, you can choose either one, but not both
// In official page there are no form insteed can use field but i did old form instalation for simpler implementation
import React from "react"
import { useForm, Controller } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { DialogClose, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import {
  Field,
  FieldLabel,
  FieldDescription,
  FieldError,
} from "@/components/ui/field"

const schema = z
  .object({
    accountHolderName: z.string().min(3, "Name must be at least 3 characters."),
    ifsc: z
      .string()
      .regex(/^[A-Z]{4}0[A-Z0-9]{6}$/, "IFSC must look like HDFC0ABC1234.")
      .transform((v) => v.toUpperCase()),
    accountNumber: z
      .string()
      .regex(/^\d{8,18}$/, "Account number must be 8–18 digits."),
    confirmAccountNumber: z
      .string()
      .regex(/^\d{8,18}$/, "Confirm account number must be 8–18 digits."),
    bankName: z.string().min(2, "Bank name is required."),
  })
  .refine((data) => data.accountNumber === data.confirmAccountNumber, {
    path: ["confirmAccountNumber"],
    message: "Account numbers do not match.",
  })

export default function PaymentDetailsDialog() {
  const form = useForm({
    resolver: zodResolver(schema),
    defaultValues: {
      accountHolderName: "",
      ifsc: "",
      accountNumber: "",
      confirmAccountNumber: "",
      bankName: "",
    },
  })

  const onSubmit = (values) => {
    console.log("Payment Details:", values)
    // TODO: call API
  }

  return (
    <div className="text-foreground">
      
      {/* Form */}
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="mt-4 space-y-5"
        noValidate
      >
        <Controller
          control={form.control}
          name="accountHolderName"
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Account holder name</FieldLabel>
              <Input
                {...field}
                placeholder="e.g. DJ comp"
                className="h-12"
              />
              {fieldState.error && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />

        <Controller
          control={form.control}
          name="ifsc"
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>IFSC Code</FieldLabel>
              <Input
                {...field}
                onChange={(e) => field.onChange(e.target.value.toUpperCase())}
                placeholder="YESB0000009"
                className="h-12"
              />
              <FieldDescription>Format: ABCD0XXXXXX</FieldDescription>
              {fieldState.error && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />

        <Controller
          control={form.control}
          name="accountNumber"
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Account Number</FieldLabel>
              <Input
                {...field}
                inputMode="numeric"
                placeholder="Enter account number"
                className="h-12"
              />
              {fieldState.error && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />

        <Controller
          control={form.control}
          name="confirmAccountNumber"
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Confirm Account Number</FieldLabel>
              <Input
                {...field}
                inputMode="numeric"
                placeholder="Confirm account number"
                className="h-12"
              />
              {fieldState.error && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />

        <Controller
          control={form.control}
          name="bankName"
          render={({ field, fieldState }) => (
            <Field data-invalid={fieldState.invalid}>
              <FieldLabel>Bank Name</FieldLabel>
              <Input
                {...field}
                placeholder="YES Bank"
                className="h-12"
              />
              {fieldState.error && <FieldError errors={[fieldState.error]} />}
            </Field>
          )}
        />

        <Button type="submit" className="h-12 w-full">
          SUBMIT
        </Button>
      </form>
    </div>
  )
}
