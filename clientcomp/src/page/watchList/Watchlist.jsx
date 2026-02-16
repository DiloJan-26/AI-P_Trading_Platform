// Step 17.7 - create a new page for watchlist and add dummy content there
// Step 2o - editing time [table comp copied from portfolio for easiness]
import React from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { BookmarkFilledIcon } from "@radix-ui/react-icons";


const Watchlist = () => {


  const handleRemoveFromWatchlist = (value) => {
    // Logic to remove the item from the watchlist based on the provided id
    console.log(`Removing item with id: ${value} from watchlist`);
  };


  return (
    <div className="p-5 lg:px-20 ">
      <h1 className="text-2xl font-bold pb-5 text-amber-300">Watchlist</h1>
      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="">COIN</TableHead>
            <TableHead>SYMBOL</TableHead>
            <TableHead>VOLUME</TableHead>
            <TableHead>MARKET CAP</TableHead>
            <TableHead>24h</TableHead>
            <TableHead>PRICE</TableHead>
            <TableHead className="text-right text-red-600">Remove</TableHead>
          </TableRow>
        </TableHeader>

        <TableBody>
          {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((asset, index) => (
            <TableRow key={index}>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar className="-z-50">
                  <AvatarImage src="./src/assets/bitcoin.png" />
                </Avatar>
                <span>Bitcoin</span>
              </TableCell>
              <TableCell>BTC</TableCell>
              <TableCell>$1.2B</TableCell>{" "}
              {/* you can get all details in currect at = api.coingecko.com/api/v3/coins/markets?vs_currency=usd(just for knowledge you can get it via beckend api); but here I used dummy value */}
              <TableCell>$200B</TableCell>
              <TableCell className="text-green-500">+2.5%</TableCell>
              <TableCell className="">$45,000</TableCell>

              <TableCell className="text-right">
                <Button onClick={() => handleRemoveFromWatchlist(index)} size='icon' variant="ghost" className="h-10 w-10 text-red-600 hover:text-red-700">
                  <BookmarkFilledIcon className="w-8 h-8"/>
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Watchlist;
