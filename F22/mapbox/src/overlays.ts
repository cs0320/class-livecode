import { FeatureCollection } from "geojson";
import { FillLayer } from "react-map-gl";

// Import the raw JSON file
import rl_data from "./mockData/fullDownload.json"

// Type predicate for FeatureCollection
function isFeatureCollection(json: any): json is FeatureCollection {
    return json.type === "FeatureCollection"
}

export function overlayData(): GeoJSON.FeatureCollection | undefined {
  if(isFeatureCollection(rl_data))
    return rl_data
  return undefined
}


////////////////////////////////////

const propertyName = 'holc_grade';

export const geoLayer: FillLayer = {
    id: 'geo_data',
    type: 'fill',
    paint: {
        'fill-color': [
            'match',
            ['get', propertyName],
            'A',
            '#5bcc04',
            'B',
            '#04b8cc',
            'C',
            '#e9ed0e',
            'D',
            '#d11d1d',
            /* other */ '#ccc'
        ],
        'fill-opacity': 0.2
    }
};

