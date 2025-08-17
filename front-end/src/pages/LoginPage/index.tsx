import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { useAuth } from '../../context/auth/use-auth';
import Paper from '../../components/Paper';
import Button from '../../components/Button';
import Form from '../../components/Form';

import { z } from 'zod';

export const schema = z.object({
  email: z.email(),
  password: z.string().min(1),
});

export type LoginFormData = z.infer<typeof schema>;

export default function LoginPage() {
  const methods = useForm<LoginFormData>({
    resolver: zodResolver(schema),
  });

  const {
    formState: { errors },
    register,
  } = methods;
  const [loginError, setLoginError] = useState<string | null>(null);
  const { login } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  const from = location.state?.from?.pathname || '/';

  const onSubmit = async (data: LoginFormData) => {
    try {
      await login(data.email, data.password);
      navigate(from, { replace: true });
    } catch (err) {
      setLoginError('Invalid email or password.');
    }
  };

  return (
    <div className="w-full max-w-sm">
      <Paper className="w-full max-w-md space-y-6">
        <h2 className="text-2xl font-bold text-center text-gray-800 dark:text-white">
          Login
        </h2>

        {loginError && (
          <div className="text-red-600 bg-red-100 dark:bg-red-900 dark:text-red-300 px-4 py-2 rounded-md text-sm">
            {loginError}
          </div>
        )}

        <Form methods={methods} onSubmit={onSubmit}>
          <Form.Field name="email" label="Email" error={errors.email?.message}>
            <Form.Input
              type="email"
              register={register}
              name="email"
              placeholder="Enter email"
            />
          </Form.Field>

          <Form.Field
            name="password"
            label="Password"
            error={errors.password?.message}
          >
            <Form.Input
              type="password"
              register={register}
              name="password"
              placeholder="Enter password"
            />
          </Form.Field>
          <Button type="submit">Login</Button>
        </Form>
      </Paper>
    </div>
  );
}
