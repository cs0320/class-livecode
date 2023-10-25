/*
  This file exists in a context *without* a tsconfig.json 
  or package.json. We only have globally-installed NPM 
  packages to work with (and to power IntelliSense in VSCode.) 
*/

// I've installed lodash globally via `npm install -g lodash`

// VSCode isn't OK with this, because it can't find the lodash
// installation I have installed globally (via `npm install -g lodash).
// (Note that VSCode will even detect a `node_modules` folder
//    in some distant parent directory! But I have none.)
import {partition} from 'lodash'

console.log(partition([0,1,2,3,4], (num: number) => num % 2))
