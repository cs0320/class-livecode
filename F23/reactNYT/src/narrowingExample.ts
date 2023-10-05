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