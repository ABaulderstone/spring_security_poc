import { ToastContainer } from 'react-toastify';
import AuthProvider from './context/auth/AuthProvider';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router';
import LoginPage from './pages/LoginPage';
import Layout from './Layout';
import PrivateRoute from './guards/PrivateRoute';
import StudentCohortPage from './pages/student/StudentCohortPage';

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Layout>
          <Routes>
            <Route path="/" element={<h1>Hello world</h1>} />
            <Route path="/login" element={<LoginPage />} />

            {/* student routes */}
            <Route
              path="/students"
              element={
                <PrivateRoute allowedRoles={['STUDENT']}>
                  <Outlet />
                </PrivateRoute>
              }
            >
              <Route path="cohort" element={<StudentCohortPage />} />
            </Route>
          </Routes>
        </Layout>
        <ToastContainer position="bottom-right" />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
