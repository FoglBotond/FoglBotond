import { useEffect, useState } from "react";
import type { Insurance } from "./types/Insurance";
import { getInsurances } from "./services/InsuranceApi";
import { InsuranceTable } from "./components/InsuranceTable";
import "./App.css";

function App() {
  const [insurances, setInsurances] = useState<Insurance[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getInsurances()
      .then(setInsurances)
      .catch(() => setError("Could not load insurances"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <main>
      <h1>Insurance Management</h1>

      <InsuranceTable insurances={insurances} />
    </main>
  );
}

export default App;