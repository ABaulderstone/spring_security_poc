import { createContext, useEffect, useState, type ReactNode } from 'react';
import { HttpClient } from '../../utils/http-client';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router';
interface SimpleUserResponse {
  id: number;
  role: string;
  email: string;
}
interface DefaultAuthContextValues {
  loggedInUser: SimpleUserResponse | null;
  httpClient: HttpClient | null;
  login: (email: string, password: string) => void;
  logout: () => void;
}

interface AuthContextProviderProps {
  children?: ReactNode;
}
export const AuthContext = createContext<DefaultAuthContextValues>({
  loggedInUser: null,
  httpClient: null,
  login: (a, b) => console.log(a + b),
  logout: () => console.log('logged out'),
});

export default function AuthProvider({ children }: AuthContextProviderProps) {
  const [loggedInUser, setLoggedInUser] = useState<SimpleUserResponse | null>(
    null
  );
  const navigate = useNavigate();
  const handleRedirect = () => {
    toast.error(
      "Looks like you're not logged in, sending you back to the login page"
    );
    navigate('/login');
  };
  const httpClient = new HttpClient(handleRedirect);

  useEffect(() => {
    httpClient.get<SimpleUserResponse>('/users/current').then(setLoggedInUser);
  }, []);

  const login = async (email: string, password: string) => {
    const user = await httpClient.post<SimpleUserResponse>(
      '/auth/login',
      { email, password },
      undefined,
      false
    );
    setLoggedInUser(user);
  };

  const logout = async () => {
    await httpClient.post('/auth/logout', null);
    setLoggedInUser(null);
  };

  return (
    <AuthContext value={{ loggedInUser, httpClient, login, logout }}>
      {children}
    </AuthContext>
  );
}
