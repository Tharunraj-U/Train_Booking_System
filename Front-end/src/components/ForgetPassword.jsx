import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ForgetPassword = ({ setIsAuthenticated }) => {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [pass1, setPass1] = useState('');
  const [pass2, setPass2] = useState('');
  const [otpSent, setOtpSent] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);
  const redirect = useNavigate();

  const handleEmailSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/auth/otp', { email });
      console.log(response.data);
      setOtpSent(true);
      // Notify user that OTP was sent
    } catch (e) {
      console.error(e.response);
    }
  };

  const handleOtpVerify = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/auth/verify-otp', { email, otp });
      console.log(response.data);
      setOtpVerified(true);
      // Notify user that OTP was verified
    } catch (e) {
      console.error(e.response);
    }
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    if (pass1 !== pass2) {
      console.log('Passwords do not match');
      return;
    }
    try {
      const response = await axios.post('http://localhost:8080/auth/reset-password', {
        email,
        password: pass1,
      });
      console.log(response);
      localStorage.setItem('token', response.data.jwt);
      setIsAuthenticated(true);
      redirect('/home');
      window.location.reload();
    } catch (e) {
      console.error(e.response);
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-br from-purple-500 to-pink-500 p-4">
      <motion.div
        className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md"
        initial={{ scale: 0.9, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ duration: 0.5 }}
      >
        {!otpSent ? (
          <>
            <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">Enter Your Email</h1>
            <form onSubmit={handleEmailSubmit} className="space-y-4">
              <div>
                <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input
                  id="email"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  placeholder="Enter your email"
                />
              </div>
              <motion.button
                type="submit"
                className="w-full bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition duration-150 ease-in-out"
                // whileHover={{ scale: 1.05 }}
                // whileTap={{ scale: 0.95 }}
              >
                Send OTP
              </motion.button>
            </form>
          </>
        ) : !otpVerified ? (
          <>
            <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">Enter OTP</h1>
            <form onSubmit={handleOtpVerify} className="space-y-4">
              <div>
                <label htmlFor="otp" className="block text-sm font-medium text-gray-700 mb-1">OTP</label>
                <input
                  id="otp"
                  type="text"
                  value={otp}
                  onChange={(e) => setOtp(e.target.value)}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  placeholder="Enter the OTP sent to your email"
                />
              </div>
              <motion.button
                type="submit"
                className="w-full bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition duration-150 ease-in-out"
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
              >
                Verify OTP
              </motion.button>
            </form>
          </>
        ) : (
          <>
            <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">Reset Your Password</h1>
            <form onSubmit={handlePasswordSubmit} className="space-y-4">
              <div>
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">New Password</label>
                <input
                  id="password"
                  type="password"
                  value={pass1}
                  onChange={(e) => setPass1(e.target.value)}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  placeholder="Enter your new password"
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
                  className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  placeholder="Confirm your new password"
                />
              </div>
              {pass1 === pass2 ? (
                <motion.button
                  type="submit"
                  className="w-full bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 transition duration-150 ease-in-out"
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                >
                  Reset Password
                </motion.button>
              ) : (
                <p className="text-red-500 text-sm">Passwords do not match</p>
              )}
            </form>
          </>
        )}
        <div className="text-center mt-4">
          <Link to="/forget" className="text-sm text-purple-600 hover:text-purple-800">
            Forgot Password?
          </Link>
        </div>
      </motion.div>
    </div>
  );
};

export default ForgetPassword;
