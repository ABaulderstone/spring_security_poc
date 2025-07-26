import { useEffect, useState } from 'react';
import { useAuth } from '../../../context/auth/use-auth';
import {
  type CohortResponse,
  getCohorts,
} from '../../../services/cohort-services';
import type { HttpClient } from '../../../utils/http-client';

export default function StudentCohortPage() {
  const [cohort, setCohort] = useState<CohortResponse | null>(null);
  const { httpClient } = useAuth();
  useEffect(() => {
    getCohorts(httpClient as HttpClient).then((res) => setCohort(res[0]));
  }, []);

  if (!cohort) {
    return null;
  }
  return (
    <>
      <h1>{cohort.name}</h1>
      <h2>{cohort.startDate}</h2>
    </>
  );
}
