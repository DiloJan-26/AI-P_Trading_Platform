// Step - 10 - Home page

import { Button } from "@/components/ui/button";
import React from "react";
import AssetTable from "./AssetTable";
import StockChart from "./StockChart";

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
          <AssetTable/>
        </div>
        {/* Step 13 - Stockchart added */}
        <div className="hidden lg:w-[50%] lg:block p-5">
          <StockChart/>
        </div>
        {/* Step 13 ended */}
      </div>
    </div>
  );
};

export default Home;
