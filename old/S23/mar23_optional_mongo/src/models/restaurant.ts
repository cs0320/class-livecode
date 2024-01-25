// External dependencies
import { ObjectId } from "mongodb";

// Class Implementation

export interface Grade {
    date: string,
    grade: string, 
    score: number
}

export default class Restaurant {
    constructor(public address: string, borough: string, cuisine: string, restaurant_id: string, grades: Grade[], name: string, id?: ObjectId) {}
}

export function isRestaurant(obj: any): obj is Restaurant {
    if(!('address' in obj)) return false;
    if(!('borough' in obj)) return false;
    if(!('cuisine' in obj)) return false;
    if(!('restaurant_id' in obj)) return false;
    if(!('grades' in obj)) return false;
    if(!('name' in obj)) return false;
}