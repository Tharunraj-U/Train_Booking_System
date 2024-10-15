import React, { useState } from 'react';
import { motion } from "framer-motion";
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Signup = ({ setIsAuthenticated }) => {
  const [email, setEmail] = useState("");
  const [pass1, setPass1] = useState("");
  const [pass2, setPass2] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (pass1 !== pass2) {
      console.log("Passwords do not match!");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/auth/signup", { email, password: pass1 });
      console.log(response);

      localStorage.setItem("token", response.data.jwt);
      setIsAuthenticated(true);

      // Navigate to home and refresh the page
      navigate("/home");
      window.location.reload();
    } catch (e) {
      console.log(e.response);
    }

    console.log("Signup submitted", { email, pass1, pass2 });
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-br from-purple-500 to-pink-500 p-4">
      <motion.div
        className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md"
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ duration: 0.5 }}
      >
        <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">Create a New Account</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
              placeholder="Enter your email"
            />
          </div>
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">Password</label>
            <input
              id="password"
              type="password"
              value={pass1}
              onChange={(e) => setPass1(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
              placeholder="Enter your password"
            />
          </div>
          <div>
            <label htmlFor="confirm-password" className="block text-sm font-medium text-gray-700 mb-1">Confirm Password</label>
            <input
              id="confirm-password"
              type="password"
              value={pass2}
              onChange={(e) => setPass2(e.target.value)}
              required
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
              placeholder="Confirm your password"
            />
          </div>
          {pass1 === pass2 ? (
            <motion.button
              type="submit"
              className="w-full bg-purple-600 text-white py-2 px-4 rounded-md"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              Create Account
            </motion.button>
          ) : (
            <p className="text-red-500 text-sm">Passwords do not match</p>
          )}
          <div className="text-center mt-4">
            <Link to="/forget" className="text-sm text-purple-600 hover:text-purple-800">
              Forgot Password?
            </Link>
          </div>
        </form>
      </motion.div>
    </div>
  );
};

export default Signup;
