import type { Insurance } from "../types/Insurance";

interface InsuranceTableProps {
  insurances: Insurance[];
}

export function InsuranceTable({
  insurances,
}: InsuranceTableProps) {
  return (
    <table>
      <thead>
        <tr>
          <th>Contract number</th>
          <th>Customer</th>
          <th>Product</th>
          <th>Start date</th>
          <th>Premium</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        {insurances.map((insurance) => (
          <tr key={insurance.id}>
            <td>{insurance.contractNumber}</td>
            <td>{insurance.customerName}</td>
            <td>{insurance.productName}</td>
            <td>{insurance.startDate}</td>
            <td>{insurance.premium}</td>
            <td>{insurance.status}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}