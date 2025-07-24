import type { ReactNode } from 'react';
import Navbar from './components/Nav';

interface LayoutProps {
  children: ReactNode;
}
export default function Layout({ children }: LayoutProps) {
  return (
    <div className='flex flex-col min-h-screen bg-gray-100 text-gray-900 dark:text-gray-100 dark:bg-gray-900"'>
      <Navbar />
      <main className="flex-1 flex items-center justify-center p-4">
        {children}
      </main>
    </div>
  );
}
