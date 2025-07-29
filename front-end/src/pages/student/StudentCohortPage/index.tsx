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
    <div className="flex flex-col justify-center items-center gap-4">
      <h1 className="text-3xl">{cohort.name}</h1>
      <h2 className="text-xl">{cohort.startDate}</h2>
    </div>
  );
}
