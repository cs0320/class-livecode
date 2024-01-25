// External dependencies
import { ObjectId } from "mongodb";

// Class Implementation

export default class Neighborhood {
    constructor(public geometry: object, name: string, id?: ObjectId) {}
}