import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

// Tim removed some boilerplate to keep things simple.

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);