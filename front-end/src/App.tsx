import { ToastContainer } from 'react-toastify';
import AuthProvider from './context/auth/AuthProvider';
import { BrowserRouter, Route, Routes } from 'react-router';
import LoginPage from './pages/LoginPage';
import Layout from './Layout';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Layout>
          <Routes>
            <Route path="/" element={<h1>Hello world</h1>} />
            <Route path="/login" element={<LoginPage />} />
          </Routes>
        </Layout>
        <ToastContainer position="bottom-right" />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
