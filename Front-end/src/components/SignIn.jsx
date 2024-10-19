import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import authservice from '../services/api'
import {motion} from "framer-motion"
import Signup from './Signup'
const SignIn = ({setIsAuthenticated}) => {

    const [email,setEmail]=useState("")
    const [password,setPassword]=useState("")
    const[error,setError]=useState("")
    const navigator=useNavigate();
    const handleSubmit= async (e)=>{
      e.preventDefault();
      console.log(email,password);
      try{
       const response=await    authservice.signIn({email,password})
       console.log(response.data.jwt);
       localStorage.setItem("token",response.data.jwt);
       const token=localStorage.getItem("token");
       setIsAuthenticated(!!token)
       navigator("/home")
       window.location.reload();
      }catch(e){
        
        if (e.response) {
          // The request was made and the server responded with a status code
          if (e.response.status === 401) {
            setError("Unauthorized: Invalid credentials. Please try again.");
          }
           else if (e.response.status === 403) {
            setError("Forbidden: You do not have access to this resource.");
          }
           else if (e.response.status >= 500){
            setError("An error occurred Server Error. Please try again later.");
          }
        
        } else {
        
          setError("An error occurred: " + e.message);
        }
       
        }
   }
  return (
    
    <div className='flex justify-center items-center min-h-screen bg-gradient-to-tl from-yellow-300 to-purple-200 p-4'>
    
      <motion.div 
      className='bg-white p-8  rounded-xl shadow-2lg w-full max-w-md' 
       initial={{ scale: 0.8, opacity: 0 }}
        animate={{ scale: .9, opacity: 1 }}
        transition={{ duration: 1 }}>



          <h2 className='  text-3xl font-extrabold mb-6 text-gray-800 text-center'>Sign In</h2>
        <form onSubmit={handleSubmit} className="space-y-6">



          <div className='mb-4'>
          <label className='block text-gray-900' >Email</label>
          <input  id="email" type='email' value={email} onChange={(e)=>setEmail(e.target.value)} required 
          className='w-full  block p-2 border border-gray-400   shadow-sm placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-violet-500 focus:border-violet-500"rounded mt-1 focus:ring-2 focus:ring-blue-500' placeholder="Enter your email">
            
          </input>
          </div>




            <div className='mb-4'><label className='block'>Password</label>
            <input type='password' value={password} onChange={(e)=> setPassword(e.target.value)} required 
            className='w-full p-2   border border-gray-400 mt-1 focus:outline-none shadow-black focus:ring-2 focus:ring-violet-600' placeholder="Enter your password"></input></div>
            {error && <p className="text-red-500 text-sm m-3">{error}</p>}
            {/* <a href='/siginup' className='block mb-0 text-blue-600'>Create an new Account</a> */}
            <div className='flex space-x-3'>
            <p>Don't have an account? </p>
            <a  className='block mb-0 text-blue-600'> <Link to={'/signup'}>Sign up</Link></a>
            </div>
           
              <button className='mb-1 p-3 bg-violet-600 text-white rounded-xl  focus:outline-none hover:bg-blue-700 shadow-md hover:shadow-lg'>Submit</button>
        </form>
      </motion.div>
    </div>
  )
}

export default SignIn
