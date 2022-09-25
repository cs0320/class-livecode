Development notes from Summer 2022 follow. Some of these will be useful when crafting handouts, and 
others may be useful for helping with setup problems.

  (1) Use the React testing library as starting point. Raw Jest + TypeScript + ESM is
      rough to do; mimicing create-react-app gives us a working starting point.
      We'll still use Jest without RTL help for the first NYT puzzle demo, and for sprint 1.

  (2) Use "react-scripts test"; this is pre-configured Jest with Babel to compile
      TypeScript, etc. 

  (3) VsCode "Live Server" extension to allow us to preview HTML via http://
      rather than file://, which avoids the same-origin policy violation
      introduced via type="module". Note this isn't "bad" of the browser;
      commonJS <script>s can ignore the same-origin policy for backwards
      compat., but this isn't really safe. Embrace simplicity.
    ** Right click the index.html in the Explorer + select open with Live Server **

  `package-lock.json` doesn't mean what a systems person might initially think. It's
  got nothing to do with file locks. Instead, it's a verbose manifest that
  makes sure that anyone running the package has all the same versions, etc. 
  Hence, it's meant to be included in git respositories. Standardization means
  fewer difficult-to-reproduce bugs.

  Another useful VSCode addon: ESLint
