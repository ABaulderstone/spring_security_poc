import type { ButtonHTMLAttributes, ReactNode } from 'react';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  className?: string;
  children: ReactNode;
  variant?: 'primary' | 'secondary' | 'danger';
}

const variantClasses: Record<NonNullable<ButtonProps['variant']>, string> = {
  primary:
    'bg-indigo-300 hover:bg-indigo-400 dark:bg-indigo-600 dark:hover:bg-indigo-500',
  secondary:
    'bg-gray-300 hover:bg-gray-400 dark:bg-gray-600 dark:hover:bg-gray-500',
  danger: 'bg-red-400 hover:bg-red-500 dark:bg-red-600 dark:hover:bg-red-500',
};

export default function Button({
  className = '',
  children,
  variant = 'primary',
  ...props
}: ButtonProps) {
  const baseClasses =
    'w-full py-2 rounded-full text-white font-semibold transition-colors duration-200';

  const finalClassName = `${baseClasses} ${variantClasses[variant]} ${className}`;

  return (
    <button className={finalClassName} {...props}>
      {children}
    </button>
  );
}
