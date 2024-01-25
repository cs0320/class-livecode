// External Dependencies
import express, { Request, Response } from "express";
import { Filter, FindOptions, ObjectId } from "mongodb";
import { collections } from "../services/database.service";
import Restaurant, {isRestaurant} from "../models/restaurant";
import Neighborhood from "../models/Neighborhood";

// Global Config
export const demoRouter = express.Router();
demoRouter.use(express.json());

// GET
demoRouter.get("/", async (_req: Request, res: Response) => {
    try {
       // This cast produces an error for me, and we should be able to
       // avoid typecasting regardless. Fortunately, there's some 2022
       // advice in this thread:
       // https://stackoverflow.com/questions/70029584/casting-mongodb-document-to-typescript-interface

       // Search only for restaurants on Staten Island, because getting anywhere
       // else takes approximately forever. Search only for ice cream.
       const filters: Filter<Document> = {
        borough: "Staten Island",
        cuisine: {$regex: ".*Ice Cream.*"},
       }
       const options: FindOptions = {}
       const restaurants = await collections.restaurants.find<Restaurant>(filters,options).toArray()       
       // The type parameter will make this typecheck, but providing the _wrong_ parameter
       // won't actually be caught (or even produce an error). This feels dangerous, so let's 
       // program defensively:
       if(!restaurants.every(isRestaurant)) {
         console.log("error: wrong model type")
         res.status(500).send("unexpected server error: wrong model type")
       }

        res.status(200).send(restaurants);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// POST
demoRouter.post("/", async (req: Request, res: Response) => {
    try {
        // TODO: better use of TypeScript
        const newGame = req.body as Restaurant;
        const result = await collections.restaurants.insertOne(newGame);

        result
            ? res.status(201).send(`Successfully created a new game with id ${result.insertedId}`)
            : res.status(500).send("Failed to create a new game.");
    } catch (error) {
        console.error(error);
        res.status(400).send(error.message);
    }
});

// PUT
demoRouter.put("/:id", async (req: Request, res: Response) => {
    const id = req?.params?.id;

    try {
        // TODO: better use of TypeScript
        const updatedGame: Restaurant = req.body as Restaurant;
        const query = { _id: new ObjectId(id) };
      
        const result = await collections.restaurants.updateOne(query, { $set: updatedGame });

        result
            ? res.status(200).send(`Successfully updated restaurant with id ${id}`)
            : res.status(304).send(`Restaurant with id: ${id} not updated`);
    } catch (error) {
        console.error(error.message);
        res.status(400).send(error.message);
    }
});

// DELETE
demoRouter.delete("/:id", async (req: Request, res: Response) => {
    const id = req?.params?.id;

    try {
        const query = { _id: new ObjectId(id) };
        const result = await collections.restaurants.deleteOne(query);

        if (result && result.deletedCount) {
            res.status(202).send(`Successfully removed restaurant with id ${id}`);
        } else if (!result) {
            res.status(400).send(`Failed to remove restaurant with id ${id}`);
        } else if (!result.deletedCount) {
            res.status(404).send(`Restaurant with id ${id} does not exist`);
        }
    } catch (error) {
        console.error(error.message);
        res.status(400).send(error.message);
    }
});
