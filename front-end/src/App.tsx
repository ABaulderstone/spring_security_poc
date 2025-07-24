import { ToastContainer } from 'react-toastify';
import AuthContextProvider from './context/AuthContextProvider';
import { BrowserRouter, Route, Routes } from 'react-router';

function App() {
  return (
    <BrowserRouter>
      <AuthContextProvider>
        <Routes>
          <Route path="/" element={<h1>Hello world</h1>} />
          <Route path="/login" element={<h1>Login page</h1>} />
        </Routes>
        <ToastContainer position="bottom-right" />
      </AuthContextProvider>
    </BrowserRouter>
  );
}

export default App;
