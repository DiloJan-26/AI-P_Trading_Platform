// Step 35 - creating trading form component for stock details page (step 54 in StockDetails.jsx as dialog content)
import React from "react";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";
import { DotIcon } from "@radix-ui/react-icons";

const TradingForm = () => {
  const [side, setSide] = React.useState("buy"); // "buy" | "sell"
  const [amount, setAmount] = React.useState("");

  // dummy data (replace later with real coin + wallet data)
  const coin = {
    symbol: "BTC",
    name: "Bitcoin",
    price: 3689.97,
    changePct: 0.4275,
    changeAbs: 1887310531,
    image: "/src/assets/bitcoin.png", // replace with eth icon later
  };

  const wallet = {
    availableCash: 4400.53,
    availableQty: 0.0265,
  };

  // simple computed preview
  const numericAmount = Number(amount || 0);
  const estQty = coin.price > 0 ? numericAmount / coin.price : 0;

  return (
    <div className="space-y-5">
      {/* Amount row */}
      <div className="grid grid-cols-3 gap-3">
        <div className="col-span-2">
          <Input
            value={amount}
            onChange={(e) => setAmount(e.target.value.replace(/[^\d.]/g, ""))}
            placeholder="enter amount..."
            className="h-12 bg-background/40"
            inputMode="decimal"
          />
        </div>

        <div className="h-12 rounded-md border border-border bg-background/40 px-3 flex items-center justify-center">
          <span className="text-sm font-semibold">
            {amount ? estQty.toFixed(6) : "0"}
          </span>
        </div>
      </div>
      {/* the warning for crossing the limit */}
      <div>
        {amount && estQty > wallet.availableQty && side === "buy" && (
          <p className="text-sm text-red-500 text-center">
            You don't have enough cash to buy {estQty.toFixed(6)} {coin.symbol}
          </p>
        )}
      </div>

      {/* Coin header */}
      <div className="flex items-center gap-3">
        <Avatar className="h-8 w-8">
          <AvatarImage src={coin.image} />
        </Avatar>

        <div className="flex-1">
          <div className="flex items-center gap-2">
            <p className="font-semibold">{coin.symbol}</p>
            <DotIcon className="text-muted-foreground" />
            <p className="text-muted-foreground">{coin.name}</p>
          </div>

          <div className="flex items-end gap-2">
            <p className="text-lg font-bold">{coin.price.toFixed(2)}</p>
            <p className="text-green-400 text-sm">
              {coin.changeAbs}{" "}
              <span className="text-green-400/80">({coin.changePct}%)</span>
            </p>
          </div>
        </div>
      </div>

      <Separator className="bg-border/60" />

      {/* Order type row */}
      <div className="flex items-center justify-between text-sm">
        <p className="text-muted-foreground">Order Type</p>
        <p className="font-medium">Market Order</p>
      </div>

      {/* Available row (switch label depending on buy/sell like your screenshot) */}
      <div className="flex items-center justify-between text-sm">
        <p className="text-muted-foreground">
          {side === "buy" ? "Available Cash" : "Available Quantity"}
        </p>
        <p className="font-semibold">
          {side === "buy"
            ? `$ ${wallet.availableCash.toFixed(2)}`
            : wallet.availableQty.toFixed(4)}
        </p>
      </div>

      {/* Big action button */}
      <Button
        type="button"
        className={[
          "h-12 w-full font-medium",
          side === "sell"
            ? "bg-red-700/80 hover:bg-red-700 text-white"
            : "bg-green-600/70 hover:bg-green-600 text-white",
        ].join(" ")}
      >
        {side === "sell" ? "SELL" : "BUY"}
      </Button>

      {/* Toggle */}
      <div className="text-center text-sm">
        <button
          type="button"
          onClick={() => setSide((s) => (s === "buy" ? "sell" : "buy"))}
          className="text-muted-foreground hover:text-foreground underline underline-offset-4"
        >
          {side === "buy" ? "Or Sell" : "Or Buy"}
        </button>
      </div>
    </div>
  );
};

export default TradingForm;
