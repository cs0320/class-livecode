
/*
  In Java, we can have 2 record classes that are identical:
  record A(String name) {}
  record B(String name) {}

  Yet, if I were to do this:
  A a = A("Nim Telson")
  B b = a; // error!
*/



interface A { name: string }
interface B { name: string }

const a: A = {name: 'Nim Telson'}
const b: B = a 

/* Let's consider a bug that isn't caught by the type system. 
   This _specific_ bug wouldn't be caught in Java either, but more 
   elaborate versions would be (but TypeScript would remain silent). 
*/ 

interface Student { 
  id: string,
  studentName: string,
  concentration: string
}
  
interface Course { 
  id: string, 
  name: string,
  enrollment: Set<Student>
}   

/** The registrar maps student IDs to the number of credits taken. */
type RegistrarCredits = {[id: string]: number}
let registrar: RegistrarCredits = {}
/** ...and is told to give credit by invoking this function (which 
    in reality would return a promise...) */
function registrarGiveCredit(studentId: string) {
  registrar[studentId] = registrar[studentId] + 1
}

/** The course is over, and, to keep the example simple all students 
 *  enrolled passed. Great! Let's give them credit. */
function courseFinished(c: Course) {
  c.enrollment.forEach((s: Student) => {
    console.log(`Congratulations ${s.studentName}.`)
    registrarGiveCredit(c.id)
  })}

/* Can you spot the bug? 
   How can we fix this? Not the _bug_, that's easy enough. 
   We need to fix the fact that this doesn't produce a type error. 
      This is a huge defensive-programming problem, otherwise!
   
   Note that this isn't just about primitives; the problem exists 
   anywhere! Let's start by thinking about how we'd fix this in Java...
   
   */



// We need to add this (or something like it) so TypeScript realizes 
// this file is a module. If it doesn't, VSCode will give us duplicate
// identifier errors for things we don't export.
export {} 