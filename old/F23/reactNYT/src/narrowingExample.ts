/*
  There are many types of "narrowing" in TypeScript. 
  This is one of them. We'll cover more in the next weeks.
*/

interface Person {
    name: string
}

const nim = {
    name: 'Nim Telson',
    favoriteCourse: '0320'
}

function exampleNarrowingByField(person: Person) {
    console.log(person.name)
    if("favoriteCourse" in person) {
        // We've "narrowed" the type of person here
        // to include only Persons who have an additional field
        console.log(person.favoriteCourse)
    }
}
exampleNarrowingByField(nim)

// Another: TypeScript has union types...
function exampleNarrowingUnion(value: string | string[]): string {
    //return value // no! we don't know if it's a string yet
    if(typeof(value) == "string") return value
    // Notice that after this point, the type of value is an array
    const result = value[0] 
    // But the type of result is string, even though undefined is possible
    return result
}
console.log(exampleNarrowingUnion("hello"))
console.log(exampleNarrowingUnion(["hello", "world"]))
// The type system doesn't *completely* protect us...
// This is an ergonomic *CHOICE* for the default, to avoid 
// flooding programmers with errors. To turn on this check, 
// add this to tsconfig.json: 
//   "noUncheckedIndexedAccess": true
console.log(exampleNarrowingUnion([]))
