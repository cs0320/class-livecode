import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { makeConstantThermometer, makeOscillatingThermometer } from './thermometers.ts'

/********* SWAP HERE TO CONTROL THERMOMETER INPUT **********/
const t = makeConstantThermometer(65)
// Swap when you have an oscillating thermometer implementation.
//const t = makeOscillatingThermometer(62, 72, 55)
/***********************************************************/

const rootElement = document.getElementById('root')
if (rootElement === null) throw new Error('missing #root element')

createRoot(rootElement).render(
  <StrictMode>
    <App thermometer={t} />
  </StrictMode>,
)
