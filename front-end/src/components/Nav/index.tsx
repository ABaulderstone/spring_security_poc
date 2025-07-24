import { Link } from 'react-router';
import { useAuth } from '../../context/auth/use-auth';

const Navbar = () => {
  const { loggedInUser: user, logout } = useAuth();

  const getNavLinks = () => {
    if (!user) return [{ label: 'Login', to: '/login' }];

    const links: { label: string; to: string }[] = [];

    if (['ADMIN', 'TALENT', 'COACH'].includes(user.role)) {
      links.push(
        { label: 'Cohorts', to: '/cohorts' },
        { label: 'Create Cohort', to: '/cohorts/create' }
      );
    } else if (user.role === 'STUDENT') {
      links.push({ label: 'My Cohort', to: '/my-cohort' });
    }

    return links;
  };

  const navLinks = getNavLinks();

  return (
    <nav className="bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        <div className="text-gray-900 dark:text-white font-semibold">
          Welcome {user?.email ?? 'Guest'}
        </div>
        <div className="flex gap-4 items-center">
          {navLinks.map(({ label, to }) => (
            <Link
              key={to}
              to={to}
              className="text-indigo-600 hover:text-indigo-800 font-medium"
            >
              {label}
            </Link>
          ))}
          {user && (
            <button
              onClick={logout}
              className="text-indigo-600 hover:text-indigo-800 font-medium"
            >
              Logout
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
