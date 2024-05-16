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
import Forum from "./Pages/Forum/Forum";
import Question from "./Pages/Forum/Question";
import Post from "./Pages/Forum/Post";

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
            <Route path="/forum" element={<Forum />} />
            <Route path="/forum/questions" element={<Question />} />
            <Route path="/forum/posts" element={<Post />} />
          </Routes>
        </MainContainer>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
// <Route path="*" element={<NotFound />} />;
//