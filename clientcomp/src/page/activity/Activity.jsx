// Step 17.2 - create a new page for activity and add dummy content there
// Step 21 - editing time [table comp copied from portfolio for easiness]
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

const Activity = () => {
  return (
    <div className="p-5 lg:px-20 ">
      <h1 className="text-2xl font-bold pb-5 text-amber-300">Activity</h1>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="">Date & Time</TableHead>
            <TableHead>Trading pair</TableHead>
            <TableHead>Buy Price</TableHead>
            <TableHead>Sell Price</TableHead>
            <TableHead>Order Type</TableHead>
            <TableHead>Profit/Loss</TableHead>
            <TableHead className="text-right">VALUE</TableHead>
          </TableRow>
        </TableHeader>

        <TableBody>
          {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((asset, index) => (
            <TableRow key={index}>
              <TableCell className="text-gray-400">
                <p>2024-01-01</p>
                <p>16:00:32</p>
              </TableCell>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar className="-z-50">
                  <AvatarImage src="./src/assets/bitcoin.png" />
                </Avatar>
                <span>Bitcoin</span>
              </TableCell>
              <TableCell>$1.2B</TableCell>{" "}
              {/* you can get all details in currect at = api.coingecko.com/api/v3/coins/markets?vs_currency=usd(just for knowledge you can get it via beckend api); but here I used dummy value */}
              <TableCell>$200B</TableCell>
               <TableCell>BUY</TableCell>
              <TableCell className="text-green-500">+2.5%</TableCell>
              <TableCell className="text-right">$45,000</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Activity;
