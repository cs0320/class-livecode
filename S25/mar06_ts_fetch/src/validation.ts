/**
 * Helpers to validate the API messages received. We'll use Zod, which is great 
 * for this (and other things). 
 * 
 * Why do we need to do this? Well, defining our own type predicates is a useful 
 * example, but I'd rather extract all we need from a single declaration. It's also
 * rather a lot of work to _fully_ specify a JSON scheme in a bunch of nested predicates. 
 * (Even my example in App.tsx is a partial check.)
 */

import { z } from 'zod'

// If we wanted to, we could give the inner object type its own schema, 
// and infer the interface. But really, we just want to validate the message.
export const zNWSGridResponse = z.object({
  properties: z.object({ 
    gridX: z.number(),
    gridY: z.number(),
    gridId: z.string(),
    // We want something richer here. It's not just a string! It's a URL!
    // This is an example of something that the type system couldn't express,
    // even if we forgot about the need to validate JSON at runtime. There's no
    // "URL" subtype of string in TypeScript. But we can precisely articulate 
    // that goal here, in validation. [[TODO: is this true? check the inferred interface.]]
    forecast: z.string().url({message: 'Forecast field was not a URL.'})
  })
});

export type NWSGridResponse = z.infer<typeof zNWSGridResponse>