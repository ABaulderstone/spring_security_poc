import type { ReactNode } from 'react';
import React from 'react';
import Input, { type InputProps } from './Input';

interface FieldProps {
  name: string;
  label?: string;
  error?: string;
  children: ReactNode;
}

export default function Field({ name, label, error, children }: FieldProps) {
  const childrenWithProps = React.Children.map(children, (child) => {
    if (React.isValidElement(child) && child.type === Input) {
      return React.cloneElement(child as React.ReactElement<InputProps<any>>, {
        error,
      });
    }
    return child;
  });

  return (
    <div className="space-y-2">
      {label && (
        <label
          htmlFor={name}
          className="block text-sm font-medium text-gray-700 dark:text-gray-300"
        >
          {label}
        </label>
      )}
      {childrenWithProps}
      {error && <p className="text-sm text-red-600">{error}</p>}
    </div>
  );
}
