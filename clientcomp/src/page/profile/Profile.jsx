// step 17.8 - create a new page for profile and add dummy content there
// Step 30 - editing profile page
import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import VerifyOtp from "./VerifyOtp";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import StockDetails from "../stockdetails/StockDetails";

function InfoRow({ label, value }) {
  return (
    <div className="flex items-center gap-4">
      <p className="w-36 text-sm font-medium text-foreground/90">{label} :</p>
      <p className="text-sm text-muted-foreground">{value}</p>
    </div>
  );
}

export default function Profile() {
  // dummy data (replace with your API/user state)
  const user = {
    email: "djan@gmail.com",
    fullName: "DJ Comp",
    dob: "25/09/2000",
    nationality: "Srilankan",
    address: "DJ Comp",
    city: "Colombo",
    postcode: "345020",
    country: "Srilanka",
    twoStepEnabled: true,
    accountStatus: "pending",
    mobile: "+94 766789922",
  };

  return (
    <div className="mx-auto w-full max-w-6xl space-y-6 p-6">
      {/* YOUR INFORMATION */}
      <Card className="bg-background/70 backdrop-blur supports-backdrop-filter:bg-background/60">
        <CardHeader>
          <CardTitle className="text-2xl text-amber-300">
            Your Information
          </CardTitle>
        </CardHeader>

        <CardContent>
          <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
            <div className="space-y-4">
              <InfoRow label="Email" value={user.email} />
              <InfoRow label="Full Name" value={user.fullName} />
              <InfoRow label="Date Of Birth" value={user.dob} />
              <InfoRow label="Nationality" value={user.nationality} />
            </div>

            <div className="space-y-4">
              <InfoRow label="Address" value={user.address} />
              <InfoRow label="City" value={user.city} />
              <InfoRow label="Postcode" value={user.postcode} />
              <InfoRow label="Country" value={user.country} />
            </div>
          </div>
        </CardContent>
      </Card>

      {/* 2 STEP VERIFICATION */}
      <Card className="bg-background/70 backdrop-blur supports-backdrop-filter:bg-background/60">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-lg">2 Step Verification</CardTitle>

          <Badge variant={user.twoStepEnabled ? "enabled" : "secondary"}>
            {user.twoStepEnabled ? "Enabled" : "Disabled"}
          </Badge>
        </CardHeader>

        <Dialog>
          <DialogTrigger asChild>
            <CardContent className="space-y-4">
              <Button variant="" className="w-full sm:w-auto">
                Enable Two Step Verification
              </Button>
            </CardContent>
          </DialogTrigger>
          <DialogContent className="max-h-[85vh] overflow-y-auto">
            <DialogHeader>
              <DialogTitle>Verify OTP</DialogTitle>
            </DialogHeader>
            <VerifyOtp />
          </DialogContent>
        </Dialog>
      </Card>

     
    </div>
  );
}

//  {/* BOTTOM GRID: CHANGE PASSWORD + ACCOUNT STATUS */}
//       <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
//         {/* CHANGE PASSWORD */}
//         <Card className="bg-background/70 backdrop-blur supports-backdrop-filter:bg-background/60">
//           <CardHeader>
//             <CardTitle className="text-lg">Change Password</CardTitle>
//           </CardHeader>

//           <CardContent className="space-y-4">
//             <InfoRow label="Email" value={user.email} />

//             <div className="flex items-center gap-4">
//               <p className="w-36 text-sm font-medium text-foreground/90">
//                 Password :
//               </p>
//               <Button variant="outline" className="h-9">
//                 Change Password
//               </Button>
//             </div>

//             <Separator className="my-2" />

//             <p className="text-xs text-muted-foreground">
//               Tip: Use at least 12 characters with numbers and symbols.
//             </p>
//           </CardContent>
//         </Card>

//         {/* ACCOUNT STATUS */}
//         <Card className="bg-background/70 backdrop-blur supports-backdrop-filter:bg-background/60">
//           <CardHeader className="flex flex-row items-center justify-between">
//             <CardTitle className="text-lg">Account Status</CardTitle>

//             <Badge
//               className={
//                 user.accountStatus === "pending"
//                   ? "bg-orange-500/20 text-orange-300 border border-orange-500/30"
//                   : ""
//               }
//               variant="secondary"
//             >
//               {user.accountStatus}
//             </Badge>
//           </CardHeader>

//           <CardContent className="space-y-4">
//             <InfoRow label="Email" value={user.email} />
//             <InfoRow label="Mobile" value={user.mobile} />

//             <Separator className="my-2" />

//             <div className="flex gap-2">
//               <Button variant="" className="w-auto">
//                 Verify Account
//               </Button>
//               <Button variant="secondary" className="w-auto p-4">
//                 Update Info
//               </Button>
//             </div>
//           </CardContent>
//         </Card>
//       </div>
