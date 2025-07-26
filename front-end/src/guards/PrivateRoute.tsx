import type { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router';
import { useAuth } from '../context/auth/use-auth';
import { toast } from 'react-toastify';
import type { UserRole } from '../context/auth/AuthProvider';

type PrivateRouteProps = {
  children: ReactNode;
  allowedRoles?: UserRole[];
};

export default function PrivateRoute({
  children,
  allowedRoles,
}: PrivateRouteProps) {
  const { loggedInUser } = useAuth();
  const location = useLocation();

  if (!loggedInUser) {
    toast.warn('You have to be logged in to do to that');
    return <Navigate to="/login" replace state={{ from: location }} />;
  }

  if (allowedRoles && !allowedRoles.includes(loggedInUser.role)) {
    toast.warn('That action is not allowed for you');
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
}
