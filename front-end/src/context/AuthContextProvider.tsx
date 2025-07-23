import { createContext, useEffect, useState, type ReactNode } from 'react';
import { HttpClient } from '../utils/http-client';
import { toast } from 'react-toastify';
interface SimpleUserResponse {
  id: number;
  role: string;
  email: string;
}
interface DefaultAuthContextValues {
  loggedInUser: SimpleUserResponse | null;
  httpClient: HttpClient | null;
}

interface AuthContextProviderProps {
  children?: ReactNode;
}
export const AuthContext = createContext<DefaultAuthContextValues>({
  loggedInUser: null,
  httpClient: null,
});

export default function AuthContextProvider({
  children,
}: AuthContextProviderProps) {
  const [loggedInUser, setLoggedInUser] = useState<SimpleUserResponse | null>(
    null
  );
  const httpClient = new HttpClient(() =>
    toast.error("Oops, you're not logged in")
  );
  useEffect(() => {
    httpClient.get<SimpleUserResponse>('/users/current').then(setLoggedInUser);
  }, []);

  return (
    <AuthContext value={{ loggedInUser, httpClient }}>{children}</AuthContext>
  );
}
