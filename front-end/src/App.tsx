import { ToastContainer } from 'react-toastify';
import AuthProvider from './context/auth/AuthProvider';
import { BrowserRouter, Route, Routes } from 'react-router';
import LoginPage from './pages/LoginPage';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<h1>Hello world</h1>} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
        <ToastContainer position="bottom-right" />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
