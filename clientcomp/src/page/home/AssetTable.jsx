// Step 11 - asset table component and intergrate it with home page
import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import React from 'react'

const AssetTable = () => {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="w-25">COIN</TableHead>
          <TableHead>SYMBOL</TableHead>
          <TableHead>VOLUME</TableHead>
          <TableHead>MARKET CAP</TableHead>
          <TableHead>24h</TableHead>
          <TableHead className="text-right">PRICE</TableHead>
        </TableRow>
      </TableHeader>

      <TableBody>
        {[1,1,1,1,1,1,1,1,1,1,1].map((asset, index) =>  <TableRow key={index}>
          <TableCell className="font-medium flex items-center gap-2">
            <Avatar className="-z-50">
              <AvatarImage src="./src/assets/bitcoin.png"/>
            </Avatar>
             <span>Bitcoin</span>
          </TableCell>
          <TableCell>BTC</TableCell> 
          <TableCell>$1.2B</TableCell> {/* you can get all details in currect at = api.coingecko.com/api/v3/coins/markets?vs_currency=usd(just for knowledge you can get it via beckend api); but here I used dummy value */ }
          <TableCell>$200B</TableCell>
          <TableCell className="text-green-500">+2.5%</TableCell>
          <TableCell className="text-right">$45,000</TableCell>
        </TableRow>
      )}
      </TableBody>
    </Table>
  )
}

export default AssetTable