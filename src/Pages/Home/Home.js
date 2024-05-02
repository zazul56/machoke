import React from 'react';
import MainPage from '../../mainPage';
import AboutUs from '../../AboutUs';
import LatestPosts from '../../LatestPosts';

const Home = () => {
  return (
    <div>
      <MainPage />
      <AboutUs />
      <LatestPosts />
    </div>
  );
};

export default Home;