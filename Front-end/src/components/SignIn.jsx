import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import authservice from '../services/api'
import {motion} from "framer-motion"
const SignIn = () => {

    const [email,setEmail]=useState("tharunraj2023@gmail.com")
    const [password,setPassword]=useState("0tharun0")
    const[error,setError]=useState("")
    const navigator=useNavigate();
    const handleSubmit= async (e)=>{
      e.preventDefault();
      console.log(email,password);
      try{
       const response=await    authservice.signIn({email,password})
       console.log(response.data.jwt);
       localStorage.setItem("token",response.data.jwt);
       navigator("/home")
       window.location.reload();
      }catch(e){
        setError("Invalid credentials. Please try again.");
       console.log("Singn-In error",e);
      }
   }
  return (
    <div>
      <motion.div>
        <form onSubmit={handleSubmit}>
              <label >Email</label>
              <input type='email' value={email} onChange={(e)=>setEmail(e.target.value)}></input>
              <label>Password</label>
              <input type='password' value={password} onChange={(e)=> setPassword(e.target.value)}></input>
              <button>Submit</button>
        </form>
      </motion.div>
    </div>
  )
}

export default SignIn
