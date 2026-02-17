// 17.4 - create a new page for withdrawal and add dummy content there
// Step 29 -editing started
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

const Withdrawal = () => {
  return (
    // copy pasted from activity page and changed the content as per requirement
    <div className="p-5 lg:px-20 ">
      <h1 className="text-2xl font-bold pb-5 text-amber-300">Withdraw</h1>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="">Date</TableHead>
            <TableHead>Method</TableHead>
            <TableHead>Amount</TableHead>
            <TableHead className="text-right">Status</TableHead>
          </TableRow>
        </TableHeader>

        <TableBody>
          {[1, 1, 1, 1, 1, 1].map((asset, index) => (
            <TableRow key={index}>
              <TableCell className="text-gray-400">
                <p>2024-01-01</p>
              </TableCell>
              <TableCell>Bank Account</TableCell>
              {/* you can get all details in currect at = api.coingecko.com/api/v3/coins/markets?vs_currency=usd(just for knowledge you can get it via beckend api); but here I used dummy value */}
              <TableCell>$200B</TableCell>
              <TableCell className="text-right">
                {true?(<span className="bg-green-600 rounded-md px-2 py-1 text-xs">
                  SUCCESS
                </span>):(<span className="bg-red-600 rounded-md px-2 py-1 text-xs">
                  FAILED
                </span>)}
                
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default Withdrawal;
