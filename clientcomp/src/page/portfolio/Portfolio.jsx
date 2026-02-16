// Step 17.1 - create a new page for portfolio and add dummy content there
// Step 19 - editing time [table comp copied from asset table at home page for easiness]
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

const Portfolio = () => {
  return (
    <div className="p-5 lg:px-20 ">
      <h1 className="text-2xl font-bold pb-5 text-amber-300">Portfolio</h1>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="">ASSET</TableHead>
            <TableHead>PRICE</TableHead>
            <TableHead>UNIT</TableHead>
            <TableHead>CHANGE</TableHead>
            <TableHead>CHANGE(%)</TableHead>
            <TableHead className="text-right">VALUE</TableHead>
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
              <TableCell className="text-right">$45,000</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Portfolio;
