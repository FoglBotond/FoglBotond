export type InsuranceStatus =
  | "ACTIVE"
  | "INACTIVE"
  | "CANCELLED";

export interface Insurance {
  id: number;
  contractNumber: string;
  customerName: string;
  productName: string;
  startDate: string;
  endDate: string | null;
  premium: number;
  status: InsuranceStatus;
}