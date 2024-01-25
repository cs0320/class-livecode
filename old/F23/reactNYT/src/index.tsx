import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

// Tim removed some boilerplate to keep things simple. This 
// file is the "glue" between the index.html file and our React app.
// Usually this doesn't need modifying.

// Finds the root element and starts rendering React there.
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
// Render starting with the App components.
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);