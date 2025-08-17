import type { FieldValues, Path, UseFormRegister } from 'react-hook-form';

export interface InputProps<T extends FieldValues = FieldValues>
  extends React.InputHTMLAttributes<HTMLInputElement> {
  name: Path<T>;
  register: UseFormRegister<T>;
  error?: string;
}

export default function Input<T extends FieldValues = FieldValues>({
  name,
  register,
  error,
  ...props
}: InputProps<T>) {
  return (
    <input
      id={name}
      {...register(name)}
      {...props}
      className={`w-full px-4 py-2 rounded-md border bg-white dark:bg-gray-700 text-gray-900 dark:text-white border-gray-300 dark:border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 ${
        error ? 'border-red-500 focus:ring-red-500' : ''
      }`}
    />
  );
}
