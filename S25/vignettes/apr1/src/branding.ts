export {}

/* Here is one thing we could do. In Java, we could just create a 
   new class for each, probably a record with only one argument. 
   Then its nominal type system would see the error. In TypeScript, 
   we need to force its structural type system to see the two kinds 
   of IDs as different somehow. 
   
   Enter: "branding".
   */

type StudentId = (string & {readonly _StudentId: unique symbol})
interface Student { 
  id: StudentId,
  studentName: string,
  concentration: string
}

type CourseId = (string & {readonly _CourseId: unique symbol})
interface Course { 
  id: CourseId, 
  name: string,
  enrollment: Set<Student>
}   

/** The registrar maps student IDs to the number of credits taken. */
type RegistrarCredits = {[id: StudentId]: number}
let registrar: RegistrarCredits = {}
/** ...and is told to give credit by invoking this function (which 
    in reality would return a promise...) */
function registrarGiveCredit(studentId: StudentId) {
  registrar[studentId] = registrar[studentId] + 1
}

/** The course is over, and, to keep the example simple all students 
 *  enrolled passed. Great! Let's give them credit. */
function courseFinished(c: Course) {
  c.enrollment.forEach((s: Student) => {
    console.log(`Congratulations ${s}.`)
    registrarGiveCredit(c.id)  // ERROR! Huzzah
  })}


  
/////////////////////////////////////////////////////////////////////



// Whenever we do some type gymnastics, we want to make sure we have 
// not made the new type impossible to instantiate!  
const nimTelson: Student = {
    id: '123',
    studentName: "Nim Telson",
    concentration: "undeclared"
}
// Uh oh. This shouldn't be surprising: there's no brand in '123'. 
// But we also don't want to spend actual memory storing the field...
function makeStudentId(id: string): StudentId {
    // Yes! A typecast! But invoking this function "blesses" the use
    // of this string as a StudentId
    return id as StudentId
}
const nimTelson2: Student = {
    id: makeStudentId('123'),   // not a "constructor" really. 
    studentName: "Nim Telson",
    concentration: "undeclared"
}