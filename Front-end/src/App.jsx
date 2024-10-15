import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import BookingForm from './components/BookingForm';
import SignIn from './components/SignIn';
import NotFound from './pages/NotFound';
import Home from './pages/Home';
import TrainList from './components/TrainList';
import Payment from './pages/Payment';
import Signup from './components/Signup';
import ForgetPassword from './components/ForgetPassword';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/" element={isAuthenticated ? <Navigate to="/home" /> : <SignIn setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/home" element={isAuthenticated ? <Home /> : <SignIn setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/trainlist" element={isAuthenticated ? <TrainList /> : <SignIn setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/booking" element={isAuthenticated ? <BookingForm /> : <SignIn setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/payment" element={isAuthenticated ? <Payment /> : <SignIn setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/signup" element={isAuthenticated ? <Home /> : <Signup setIsAuthenticated={setIsAuthenticated} />} />
        <Route path="/forget" element={isAuthenticated ? <Home /> : <ForgetPassword />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
}

export default App;
