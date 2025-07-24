import { ToastContainer } from 'react-toastify';
import AuthProvider from './context/auth/AuthProvider';
import { BrowserRouter, Route, Routes } from 'react-router';
import LoginPage from './pages/LoginPage';

function App() {
  return (
    <div className="min-h-screen bg-gray-100 text-gray-900 dark:text-gray-100 dark:bg-gray-900">
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            <Route path="/" element={<h1>Hello world</h1>} />
            <Route path="/login" element={<LoginPage />} />
          </Routes>
          <ToastContainer position="bottom-right" />
        </AuthProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
