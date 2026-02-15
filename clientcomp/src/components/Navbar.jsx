// Step 4 - NavBar component is created 

import React from "react";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Button } from "./ui/button";
import { DragHandleHorizontalIcon, MagnifyingGlassIcon } from "@radix-ui/react-icons";
import { Avatar, AvatarFallback, AvatarImage } from "./ui/avatar";
import SideBar from "./SideBar";



const Navbar = () => {
  return (
    <div className="px-2 py-3 border-b z-50 bg-background bg-opacity-0 sticky top-0 left-0 right-0 flex justify-between items-center">
      <div className="flex items-center gap-3">
        {/* Step 5 - with Sheet and SideBar integration */}
        <Sheet>
          <SheetTrigger>
            <Button
              variant="ghost"
              size="icon"
              className="rounded-full h-11 w-11"
            >
              <DragHandleHorizontalIcon className="h-7 w-7" />
            </Button>
          </SheetTrigger>
          <SheetContent
            className="w-72 border-r-0 flex flex-col justify-center"
            side="left"
          >
            <SheetHeader>
              <SheetTitle>
                <div className="text-3xl flex justify-center items-center gap-1">
                  <Avatar>
                    <AvatarImage src="./src/assets/Company_Logo2.png" />
                  </Avatar>
                  <div>
                    <span className="font-bold text-cyan-700">DJ </span>
                    <span className="font-bold text-slate-500">Trad</span>
                  </div>
                </div>
              </SheetTitle>
            </SheetHeader>
            <SideBar/>
          </SheetContent>
        </Sheet>
        {/* Step 5 - with Sheet and SideBar integration step ended */}

        {/* Step 8 - top bar components (name tag, search icon) */}
        <p className="text-sm lg:text-base cursor-pointer">
          DJ Trad
        </p>
        <div className="p-0 ml-9">
          <Button variant="outline" className="flex items-center gap-3">
            <MagnifyingGlassIcon/>
            <span>Search</span>
          </Button>
        </div>
        {/* Step 8 - top bar components (name tag, search icon) step ended */}
      </div>
      {/* Step 9 -  top bar components (avatar)*/}
      <div>
        <Avatar>
          <AvatarFallback>D</AvatarFallback>
        </Avatar>
      </div>
      {/* Step 9 -  top bar components (avatar) step ended */}

    </div>
  );
};

export default Navbar;
