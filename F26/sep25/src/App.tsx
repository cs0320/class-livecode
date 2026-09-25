/**
 * The React application. You are NOT EXPECTED TO UNDERSTAND 
 * THIS YET (or indeed read it). It's a preview of the future. :-)
 */

import { useEffect, useState } from 'react'
import type { Thermometer } from './thermometers'

const TICK_MS = 1000

export default function App(props: {thermometer: Thermometer}) {
  const [heaterOn, setHeaterOn] = useState<boolean>(false)
  const [setpointF, setSetpointF] = useState<number>(68)
  const [lastTemp, setLastTemp] = useState<number|undefined>(undefined)

  useEffect(() => {
    const id = setInterval(() => {
      const reading = props.thermometer()
      const decision = reading < setpointF
      setLastTemp(reading)
      setHeaterOn(decision)
    }, TICK_MS)
    return () => clearInterval(id)
  }, [setpointF, heaterOn, props.thermometer])

  return (
    <main>
      <p className="reading">{lastTemp ? lastTemp.toFixed(1)+" F" : "Starting..."}</p>

      <p className={heaterOn ? 'heater on' : 'heater'}>
        Heater: {heaterOn ? 'ON' : 'OFF'}
      </p>

      <div className="controls">
        <button onClick={() => setSetpointF(s => s - 0.5)}>−</button>
        <span>Setpoint {setpointF.toFixed(1)} F</span>
        <button onClick={() => setSetpointF(s => s + 0.5)}>+</button>
      </div>
    </main>
  )
}
