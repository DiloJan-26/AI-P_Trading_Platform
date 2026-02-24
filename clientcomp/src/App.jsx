// Step 6: Navbar component is imported and rendered in App.jsx

import React from "react";
import Navbar from "./components/Navbar";
import Home from "./page/home/Home";
import { Route, Routes } from "react-router-dom";
import Portfolio from "./page/portfolio/Portfolio";
import Activity from "./page/activity/Activity";
import Wallet from "./page/wallet/Wallet";
import Withdrawal from "./page/withdrawal/Withdrawal";
import PaymentDetails from "./page/paymentdetails/PaymentDetails";
import StockDetails from "./page/stockdetails/StockDetails";
import Watchlist from "./page/watchList/Watchlist";
import Profile from "./page/profile/Profile";
import SearchCoin from "./page/search/SearchCoin";
import NotFound from "./page/notfound/NotFound";
import Auth from "./page/auth/Auth";

function App() {
  return (
    <>
      {/* Step 37 - connection of auth page with app.jsx */}
      {false ? (
        <div>
          <Navbar />
          {/* Step 17 - define all the page routes */}{" "}
          {/* Step 18 - go to sidebar.jsx */}
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/portfolio" element={<Portfolio />} />
            <Route path="/activity" element={<Activity />} />
            <Route path="/wallet" element={<Wallet />} />
            <Route path="/withdrawal" element={<Withdrawal />} />
            <Route path="/payment-details" element={<PaymentDetails />} />
            <Route path="/market/:id" element={<StockDetails />} />
            <Route path="/watchlist" element={<Watchlist />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/search" element={<SearchCoin />} />
            <Route path="/signin" element={<Auth />} />
            <Route path="/signup" element={<Auth />} />
            <Route path="/forgot-password" element={<Auth />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>
      ) : (
        <Auth />
      )}
    </>
  );
}

export default App;
