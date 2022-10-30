//import './Puzzle.css';
import React, { useState } from 'react';
import Map, {    
   ViewState, ViewStateChangeEvent,
   MapLayerMouseEvent,
   Source, Layer } from 'react-map-gl'

// This won't be pushed to the repo; add your own!
// You'll need to have the file within the 'src' folder, though
// (Make sure it's a "default public" key. I'm just protecting my 
//  key from denial-of-service attack...)
import {myKey} from './private/key'

export default function Puzzle() {
  const [viewState, setViewState] = useState<ViewState>({
    longitude: 12.3,
    latitude: 45.6,
    zoom: 10,
    bearing: 0,
    pitch: 0,
    // This isn't required if we look at the docs...
    // https://visgl.github.io/react-map-gl/docs/api-reference/types
    // Unfortuntely, that seems to have changed. See:
    // https://docs.mapbox.com/mapbox-gl-js/api/properties/#paddingoptions
    padding: {top: 1, bottom: 20, left: 1, right: 1}
  });  
  
  return (
    <div className="map-demo">
      <div className="map-demo-map">   
        {/* We could use {...viewState} for the 6 viewState fields, 
            but "spread" syntax wasn't covered in class. */}
        <Map 
         mapboxAccessToken={myKey}
         latitude={viewState.latitude}
         longitude={viewState.longitude}
         zoom={viewState.zoom}
         pitch={viewState.pitch}
         bearing={viewState.bearing}
         padding={viewState.padding}
         onMove={(ev: ViewStateChangeEvent) => setViewState(ev.viewState)} 
         onClick={(ev: MapLayerMouseEvent) => console.log(ev)}
         // This is too big, and the 0.9 factor is pretty hacky
         style={{width:window.innerWidth, height:window.innerHeight*0.9}} 
         mapStyle={'mapbox://styles/mapbox/light-v10'}/>       
      </div>
      <div className='map-status'>
        {`lat=${viewState.latitude.toFixed(4)},
          long=${viewState.longitude.toFixed(4)},
          zoom=${viewState.zoom.toFixed(4)}`}
      </div>
    </div>
  );
}
