import type { HttpClient } from '../utils/http-client';

export type CohortResponse = {
  id: number;
  name: string;
  startDate: string;
  createdAt: string;
  updatedAt: string;
};

export const getCohorts = (client: HttpClient): Promise<CohortResponse[]> => {
  return client.get<CohortResponse[]>('/cohorts');
};
