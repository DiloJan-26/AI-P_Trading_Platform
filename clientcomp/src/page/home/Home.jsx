// Step - 10 - Home page

import { Button } from "@/components/ui/button";
import React from "react";
import AssetTable from "./AssetTable";
import StockChart from "./StockChart";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Cross1Icon, DotIcon } from "@radix-ui/react-icons";
import { MessageCircle } from "lucide-react";

const Home = () => {
  const [category, setCategory] = React.useState("All");

  const handleCategory = (cat) => {
    setCategory(cat);
  };

  return (
    <div className="relative">
      <div className="lg:flex">
        <div className="lg:w-[50%] lg:border-r">
          <div className="p-3 flex items-center gap-4">
            <Button
              variant={category === "All" ? "default" : "outline"}
              className="rounded-full"
              onClick={() => handleCategory("All")}
            >
              All
            </Button>

            <Button
              variant={category === "top50" ? "default" : "outline"}
              className="rounded-full"
              onClick={() => handleCategory("top50")}
            >
              Top50
            </Button>

            <Button
              variant={category === "topgainers" ? "default" : "outline"}
              className="rounded-full"
              onClick={() => handleCategory("topgainers")}
            >
              Top Gainers
            </Button>

            <Button
              variant={category === "toplosers" ? "default" : "outline"}
              className="rounded-full"
              onClick={() => handleCategory("toplosers")}
            >
              Top Losers
            </Button>
          </div>
          <AssetTable />
        </div>
        {/* Step 13 - Stockchart added */}
        <div className="hidden lg:w-[50%] lg:block p-5">
          <StockChart />

          <div className="flex gap-5 items-center">
            <div>
              <Avatar>
                <AvatarImage src="./src/assets/bitcoin.png" />
              </Avatar>
            </div>
            <div>
              <div className="flex items-center gap-2">
                <p>BTC</p>
                <DotIcon className="text-gray-400" />
                <p className="text-gray-400">Bitcoin</p>
              </div>
              <div className="flex items-end gap-2">
                <p className="text-xl font-bold">5464</p>
                <p className="text-red-500">
                  <span>-132534734</span>
                  <span>(-2.34%)</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        {/* Step 13 ended */}
      </div>
      <section className="absolute bottom-5 right-5 z-40 flex flex-col justify-end items-end gap-2">

        {/* // Step 14 - Chat bot panel */}
        <div className="rounded-md w-[20rem] md:w-100 lg:w-100 h-[70vh] bg-slate-900">
          <div className="flex items-center justify-between border-b px-6 h-[12%]">
            <p>Chat Bot</p>
            <Button variant="ghost" size="icon">
              <Cross1Icon/>
            </Button>
          </div>
        </div>
        {/* // Step 14 ended */}

        <div className="relative w-40 cursor-pointer group">
          <Button className="w-full h-12 gap-2 items-center">
            <MessageCircle size={30} className="fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]" />
            <span className="text-2xl">Chat Bot</span>
          </Button>
        </div>
      </section>
    </div>
  );
};

export default Home;
