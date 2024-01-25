/*
  This file exists in a context *without* a tsconfig.json 
  or package.json. We only have globally-installed NPM 
  packages to work with (and to power IntelliSense in VSCode.) 
*/

// I've installed lodash globally via `npm install -g lodash`

// VSCode is now OK with this, because we added a jsconfig.json
// file to the context. (`jsconfig.json` is, I think about configuring
// the language server for an editor, *NOT* for actual configuration 
// like `tsconfig.json` would be. Still, this fixes the issue.)
import {partition} from 'lodash'

console.log(partition([0,1,2,3,4], (num: number) => num % 2))
