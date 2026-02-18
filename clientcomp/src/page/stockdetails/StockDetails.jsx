// Step 17.6 - create a new page for stock details and add dummy content there
// Step 32 - editing stock details page (step 33 in AssetTable.jsx as onclick action)
// this is for whenever you click on the stock name in the asset table,
// it will navigate to this page and show the details of that stock. you can add more details as per your requirement.
// Step 34 - adding dialog component for trading in stock details page
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  BookmarkFilledIcon,
  BookmarkIcon,
  DotIcon,
} from "@radix-ui/react-icons";
import React from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import TradingForm from "./TradingForm";

const StockDetails = () => {
  return (
    <div className="p-5 mt-5">
      <div className="flex justify-between">
        <div className="flex gap-5 items-center">
          <div>
            <Avatar>
              <AvatarImage src="./src/assets/bitcoin.png" />
            </Avatar>
          </div>
          <div>
            <div className="flex items-center gap-2">
              <p>BTC</p>
              <DotIcon className="text-muted-foreground" />
              <p className="text-muted-foreground">Bitcoin</p>
            </div>
            <div className="flex items-end gap-2">
              <p className="text-xl font-bold">$42,000</p>
              <p className="text-green-400">
                <span>+2.5</span>
                <span>(-0.2456)</span>
              </p>
            </div>
          </div>
        </div>
        <div className="flex items-center gap-2">
          <Button>
            {true ? (
              <BookmarkFilledIcon className="h-6 w-6" />
            ) : (
              <BookmarkIcon className="h-6 w-6" />
            )}
          </Button>

          <Dialog>
            <DialogTrigger>
              <Button size='lg'>Tread</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>How much want to spend?</DialogTitle>
              
              </DialogHeader>
              <TradingForm />
            </DialogContent>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export default StockDetails;

// import React from "react"
// import { useParams } from "react-router-dom" // ✅ NEW
// import { Avatar, AvatarImage } from "@/components/ui/avatar"

// const StockDetails = () => {
//   const { coin } = useParams() // ✅ NEW (reads /market/:coin)

//   return (
//     <div className="p-5 mt-5">
//       {/* ✅ NEW: show which coin you navigated to */}
//       <h1 className="mb-4 text-2xl font-bold capitalize">{coin}</h1>

//       <div className="flex justify-between">
//         <div className="flex gap-5 items-center">
//           <div>
//             <Avatar>
//               {/* ✅ NEW: keep placeholder for now, later map coin -> image */}
//               <AvatarImage src="/src/assets/bitcoin.png" />
//             </Avatar>
//           </div>
//         </div>
//       </div>
//     </div>
//   )
// }

// export default StockDetails
