<#assign content>
  <h1>Wow! Amaze! Success!</h1>

  <form id="goBackToStars" class="center" method="POST" action="/stars">
    <input type="submit" value="Again Again!">
  </form>

  <div class="center">
    ${successful_result}
  </div>
  <br>
  <br>

  <iframe title="In the Halls of the Usurper" width="100%" height="600"
          src=${music_link}
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen></iframe>

</#assign>
<#include "main.ftl">