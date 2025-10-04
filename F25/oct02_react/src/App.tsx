import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [arr, setArr] = useState<string[]>([])


  return (
        <button onClick={
          () => {
            //setCount(count + 1) // easier to understand\
            setCount((count) => count + 1) // safer
            setCount((count) => count + 2) // safer
          }
        }>
          double count is {2*count}
        </button>
  )
}

export default App
