import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useAuth } from '../../context/auth/use-auth';
import { useNavigate } from 'react-router';

const schema = z.object({ email: z.email(), password: z.string().min(1) });
export type LoginFormData = z.infer<typeof schema>;

export default function LoginPage() {
  const { handleSubmit, register } = useForm<LoginFormData>({
    resolver: zodResolver(schema),
  });
  const { login } = useAuth();
  const navigate = useNavigate();
  const onSubmit = async (data: LoginFormData) => {
    await login(data.email, data.password);
    navigate('/');
  };
  return (
    <form className="flex flex-col gap-4" onSubmit={handleSubmit(onSubmit)}>
      <div className="flex flex-col gap-2">
        <label>Email</label>
        <input type="email" {...register('email')} />
      </div>
      <div className="flex flex-col gap-2">
        <label>Password</label>
        <input type="password" {...register('password')} />
      </div>
      <button>Login</button>
    </form>
  );
}
