// Step 12 - stockchart component and intergrate it with home page
import { Button } from "@/components/ui/button";
import React from "react";
import ReactApexChart from "react-apexcharts";

// related to step 12.2
const timeSeries = [
  {
    keyword: "DIGITAL_CURRENCY_DAILY",
    key: "Time Series (Daily)",
    label: "1 Day",
    value: 1,
  },
  {
    keyword: "DIGITAL_CURRENCY_WEEKLY",
    key: "Time Series (Weekly)",
    label: "1 Week",
    value: 7,
  },
  {
    keyword: "DIGITAL_CURRENCY_MONTHLY",
    key: "Time Series (Monthly)",
    label: "1 Month",
    value: 30,
  },
];

// related to step 12.1
const StockChart = () => {
  // step 12.3
  const [activeLabel, setActiveLabel] = React.useState(timeSeries[0].label);

  // for historical chart data you can get it from api.coingecko.com/api/v3/coins/{id}/market_chart?vs_currency=usd&days=30 [into {id} replace the exact coin name]
  const series = [
    {
      data: [
        [1768568460960, 95401.61606126779],
        [1768572081615, 95408.7574991596],
        [1768575626880, 95197.56242965137],
        [1768579321123, 94564.21877756322],
        [1768582965958, 94774.67422759128],
        [1768586439354, 94942.20146316924],
        [1768590142355, 94931.61056338983],
        [1768593739034, 94928.20118854292],
        [1768597355914, 95444.53552680004],
        [1768600948776, 95498.90205903104],
        [1768604510815, 95457.21107120276],
        [1768608133274, 95519.80018265622],

        [1768611720240, 95427.46021175089],
        [1768615293655, 95354.08132328994],
        [1768618863255, 95324.28619981442],
        [1768622415255, 95339.45598221631],
        [1768626087364, 95272.31447750068],
        [1768629753118, 95270.07300155646],
        [1768633381629, 95178.15210477532],
        [1768637025814, 95020.13689435081],
        [1768640468239, 95210.1313151349],
        [1768644137174, 95213.03611517425],
        [1768647754280, 95059.78333700741],
        [1768651339104, 95274.03509478235],
      ],
    },
  ];

  const options = {
    chart: {
      id: "area-datetime",
      type: "area",
      height: 350,
      zoom: {
        autoScaleYaxis: true,
      },
    },
    dataLabels: {
      enabled: false,
    },
    xaxis: {
      type: "datetime",
      tickAmount: 6,
    },
    colors: ["#758AA2"],
    markers: {
      colors: ["#fff"],
      strokeColor: "#fff",
      size: 0,
      strokeWidth: 1,
      style: "hollow",
      radius: 2,
    },
    tooltip: {
      theme: "dark",
    },
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.7,
        opacityTo: 0.9,
        stops: [0, 100],
      },
    },
    grid: {
      borderColor: "#47535E",
      strokeDashArray: 4,
      show: true,
    },
  };

  return (
    // to add the chart go to https://apexcharts.com/docs/react-charts/ and follow the instruction to add the chart in your project and then you can add the chart in this component
    <div>
      <div className="space-x-3">
        {" "}
        {/* related to step 12.2, 12.3 */}
        {timeSeries.map((time) => (
          <Button
            key={time.label}
            variant={activeLabel === time.label ? "default" : "outline"}
            onClick={() => setActiveLabel(time.label)}
          >
            {time.label}
          </Button>
        ))}
      </div>
      <div id="chart-timelines">
        <ReactApexChart
          options={options}
          series={series}
          type="area"
          height={450}
        />
      </div>
    </div>
  );
};

export default StockChart;
