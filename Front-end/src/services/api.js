import axios from "axios"

 


 const API_URL="http://localhost:8080/auth"

 const signIn=( credentials)=>{
   return axios.post(`${API_URL}/signin`,credentials);
 }
 export default{
    signIn
 }