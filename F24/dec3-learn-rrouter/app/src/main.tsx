import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import ModifiedApp from './ModifiedApp.tsx'
import { BrowserRouter, Routes, Route  } from "react-router";
import { useParams } from 'react-router'

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
  Text inside BrowserRouter!
    <Routes>
      <Route element={<App />}> 
      {/* TODO: what dictates which route fires on 'hello'? */}
        <Route path=":word" element={<WordRenderer />} />
        <Route path="hello" element={'Hello'} />
        <Route path="goodbye" element={'Goodbye'} />
      </Route>
    </Routes>
  </BrowserRouter>,
)

// http://localhost:5173/iamtheverymodelofamodernmajorgeneral

function WordRenderer() {
  const params = useParams()
  return (
    <><b>{params['word']}</b></>
  )
}