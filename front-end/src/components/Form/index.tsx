import type { ReactNode } from 'react';
import type { FieldValues, UseFormReturn } from 'react-hook-form';
import Input from './Input';
import Field from './Field';

interface FormProps<T extends FieldValues> {
  methods: UseFormReturn<T>;
  onSubmit: (data: T) => unknown;
  children: ReactNode;
  className?: string;
}

function Form<T extends FieldValues>({
  methods,
  onSubmit,
  children,
  className = 'space-y-6',
}: FormProps<T>) {
  return (
    <form className={className} onSubmit={methods.handleSubmit(onSubmit)}>
      {children}
    </form>
  );
}

Form.Input = Input;
Form.Field = Field;
export default Form;
