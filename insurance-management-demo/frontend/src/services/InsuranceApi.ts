import type { Insurance } from "../types/Insurance";

const API_URL = "http://localhost:8080/api/insurances";

export async function getInsurances(): Promise<Insurance[]> {
  const response = await fetch(API_URL);

  if (!response.ok) {
    throw new Error("Failed to load insurances");
  }

  const data = await response.json();

  return data.content;
}