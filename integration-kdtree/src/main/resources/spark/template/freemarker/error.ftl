<#assign content>
  <h1> Oh no. Windows has shut down. Yes. I know you have a Mac.</h1>

  <form id="goBackToStars" class="center" method="POST" action="/stars">
    <input type="submit" value="I'll be more careful">
  </form>

  <div id="errorDiv">
    <h2>
      ${error}
    </h2>
  </div>

  <iframe title="Windows Error Bill Gates" width="100%" height="600"
          src=${music_link}
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen></iframe>
</#assign>
<#include "main.ftl">