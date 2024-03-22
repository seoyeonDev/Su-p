import React from 'react';
import '../styles/components/Header.css';

function Header() {
  return (
    <header>
      <h2>여긴 헤더부분</h2>
      <nav>
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/login">login</a></li>
          <li><a href="/addpost">addpost</a></li>
          
        </ul>
      </nav>
    </header>
  );
}

export default Header;