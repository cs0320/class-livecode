import path from "path";
import {parseCSV} from './sprint2-parser2'
import {drainFiniteGenerator, readableFromFilename, readableFromString} from './sprint2-parser2'

const PEOPLE_CSV_PATH = path.join(__dirname, "./data/people.csv");

async function example() {
    const results = await parseCSV(readableFromFilename(PEOPLE_CSV_PATH))
    const rows = await drainFiniteGenerator(results)
    console.log(rows)

    const 
}
example()
