import { ToastContainer } from 'react-toastify';
import AuthContextProvider from './context/AuthContextProvider';

function App() {
  return (
    <AuthContextProvider>
      <h1 className="text-3xl font-bold underline">Hello world!</h1>
      <ToastContainer position="bottom-right" />
    </AuthContextProvider>
  );
}

export default App;
