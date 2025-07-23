import AuthContextProvider from './context/AuthContextProvider';

function App() {
  return (
    <AuthContextProvider>
      <h1 className="text-3xl font-bold underline">Hello world!</h1>
    </AuthContextProvider>
  );
}

export default App;
