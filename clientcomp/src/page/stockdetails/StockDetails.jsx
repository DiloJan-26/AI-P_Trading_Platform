// Step 17.6 - create a new page for stock details and add dummy content there
// Step 32 - editing stock details page (step 33 in AssetTable.jsx as onclick action)
// this is for whenever you click on the stock icon in the asset table(didn't done until step 35 so have to do later),
// it will navigate to this page and show the details of that stock. you can add more details as per your requirement.
// Step 34 - adding dialog component for trading in stock details page
// Trendy and alternate version of StockDetails.jsx
// You can keep it as default
import React from "react";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  DotIcon,
  BookmarkFilledIcon,
  BookmarkIcon,
} from "@radix-ui/react-icons";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import StockChart from "../home/StockChart";
import TradingForm from "./TradingForm";

const StockDetailsx = () => {
  const isBookmarked = true; // later: state

  return (
    <div className="min-h-svh bg-background text-foreground">
      <div className="mx-auto w-full max-w-9xl px-4 py-6">
        {/* Header */}
        <div className="flex items-start justify-between gap-4">
          {/* Left: coin info */}
          <div className="flex items-center gap-4">
            <Avatar className="h-10 w-10">
              <AvatarImage src="/src/assets/bitcoin.png" />
            </Avatar>

            <div>
              <div className="flex items-center gap-2">
                <p className="font-semibold">BTC</p>
                <DotIcon className="text-muted-foreground" />
                <p className="text-muted-foreground">Bitcoin</p>
              </div>

              <div className="flex items-end gap-2">
                <p className="text-2xl font-bold">$3689.97</p>
                <p className="text-green-400 text-sm font-medium">
                  1887310531{" "}
                  <span className="text-green-400/80">(0.4275%)</span>
                </p>
              </div>
            </div>
          </div>

          {/* Right: actions */}
          <div className="flex items-center gap-2">
            <Button variant="outline" size="icon" className="h-10 w-10">
              {isBookmarked ? (
                <BookmarkFilledIcon className="h-5 w-5" />
              ) : (
                <BookmarkIcon className="h-5 w-5" />
              )}
            </Button>

            <Dialog>
              <DialogTrigger asChild>
                <Button size="lg" className="h-10 px-6">
                  TRADE
                </Button>
              </DialogTrigger>

              <DialogContent className="max-h-[85vh] overflow-y-auto">
                <DialogHeader>
                  <DialogTitle>How much want to spend?</DialogTitle>
                </DialogHeader>
                <TradingForm />
              </DialogContent>
            </Dialog>
          </div>
        </div>

        {/* Chart card container (matches your screenshot vibe) */}
        <div className="mt-6 rounded-2xl border border-border bg-background/60 p-4 backdrop-blur supports-backdrop-filter:bg-background/50">
          <StockChart />
        </div>
      </div>
    </div>
  );
};

export default StockDetailsx;
