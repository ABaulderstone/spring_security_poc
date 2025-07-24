import type { ReactNode } from 'react';

interface PaperProps {
  children: ReactNode;
  className?: string;
}

export default function Paper({ children, className = '' }: PaperProps) {
  return (
    <div
      className={`bg-white dark:bg-gray-800 p-8 rounded-2xl shadow-lg transition-colors duration-300 ${className}`}
    >
      {children}
    </div>
  );
}
