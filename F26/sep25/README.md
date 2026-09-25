# Mocking Exercise

This is a React TypeScript application to be used as an exercise in class. You haven't been taught React yet, so your job isn't to understand the app. Instead, your mission is:

* Look at the behavior of the constant thermometer on-screen. Try manipulating the setpoint: can you get the heater to turn off? Then experiment with the value being requested in `main.tsx`: you should be able to make the heater stay on forever or stay off forever.
* Implement the body of `makeOscillatingThermometer` in `thermometers.ts`. 
* Swap thermometers in `main.tsx`. 
* Observe the new behavior. You should see the heater turn on and off in cycles.

You may find referencing the in-class code for `newNumberGenerator` useful. You can find it in the livecode's `F26/sep23/src/inclass.ts`. 

## AI Use Policy

AI Use: **None**
Purpose: TypeScript practice, practice with functions as values and mocking, conceptually. 

## Challenge Exercise

* **Challenge Exercise:** Make a thermometer that reacts to the changing temperature in the room. In other words, model (a very, VERY rough version of) physics in your mock. Notice how your thermometer mocks are increasing in sophistication, which would
enable the thermostat developer to test more complex functionality.

Remember that in 0320/1340, you're allowed to modify code unless we tell you otherwise. 