
type State = "initial" | "carry_0" | "carry_1" | "error" | "done";

const startState: State = "initial";

const vars: { sum: string } = {
  sum: ""
};

function step(state: State, char: string | null): State {
  switch (state) {

    case "error": return "error"
    case "done" : return "done"

    case "initial":
      if(char === null) return "error";
      if(isNaN(parseInt(char))) return "error";
      if(parseInt(char) + 9 >= 10) {
        vars.sum = vars.sum + (parseInt(char) + 9 - 10)
        return "carry_1"
      } 
      vars.sum = vars.sum + (parseInt(char) + 9)
      return "carry_0"

    case "carry_0":
      if(char === null) {
        return "done";
      }
      if(isNaN(parseInt(char))) return "error";
      vars.sum = vars.sum + char
      return "carry_0"

    case "carry_1":
      if(char === null) {
        vars.sum = vars.sum + "1";
        return "done";
      }
      if(isNaN(parseInt(char))) return "error";
      if(parseInt(char) + 1 >= 10) {
        vars.sum = vars.sum + (parseInt(char) + 1 - 10)
        return "carry_1"
      } 
      vars.sum = vars.sum + (parseInt(char) + 1)
      return "carry_0"
  }
}