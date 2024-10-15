import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import BookingForm from './components/BookingForm'
import SignIn from './components/SignIn';
import NotFound from './pages/NotFound';
import Home from './pages/Home';
import TrainList from'./components/TrainList'
import Payment from './pages/Payment';

function App() {
  const [isAuthenticate,setIsAuthenticated]=useState(true);

  useState(()=>{
    const token=localStorage.getItem("token");
    setIsAuthenticated(!!token);
  },[]);

  return (
  <Router>
        
         <Routes>
         <Route  path="/" element={  isAuthenticate ? <Navigate to="/home" /> : <SignIn /> }/>
          <Route  path="/home" element={  isAuthenticate ? <Home/>: <SignIn/>}/>
         <Route  path="/trainlist" element={ isAuthenticate? <TrainList/> : <SignIn />}/>
         <Route  path="/booking" element={ isAuthenticate ? <BookingForm/> : <SignIn /> }/>
         <Route  path="/payment" element={ isAuthenticate ? <Payment/> : <SignIn /> }/>
         <Route path='*' element={<NotFound />} />
         </Routes>
  </Router>
  )
}

export default App
