import React from 'react'
import { Link } from 'react-router-dom'

const NavBar = () => {

  
  return (
    <nav>
      <ul>
        <li>
          <Link to="/home"> Home</Link>
        </li>
        <li>
          <Link to="/trainlist"> TrainList</Link>
        </li>
        <li>
          <Link to="/booking"> Booking</Link>
        </li>
        <li>
          <Link to="/payment"> Payment</Link>
        </li>
      </ul>
    </nav>
  )
}

export default NavBar
