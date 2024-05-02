import React from "react";
import "./App.css";
import Navbar from "./Navbar";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainContainer from "./MainContainer";
import Login from "./Pages/Login/Login"; // Make sure to import your Login component
import Home from "./Pages/Home/Home";
import Signup from "./Pages/SignUp/SignUp";
import AuthProvider from "./Auth/AuthProvider";
import Users from "./Pages/Users/Users";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <MainContainer>
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/users" element={<Users />} />
          </Routes>
        </MainContainer>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
